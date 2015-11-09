package estansaas.fonebayad.service;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

import estansaas.fonebayad.activity.ActivityTapAndPay;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/28/2015.
 */
public class NFCTapPayService extends HostApduService {
    private static final String TAG = "fonebayad";
    // AID for our fonebayad tap and pay service.

    private static final String VALIDATE_AID = "F0666f6e656261796164";
    private static final String VALIDATED_AID = "F2666f6e656261796164";

    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    private static final String SELECT_VALIDATE_APDU_HEADER = "00A40400";

    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");

    private static final byte[] UNKNOWN_CMD_INVALID_PIN = HexStringToByteArray("1111");

    private static final byte[] SELECT_VALIDATE_APDU = BuildSelectApdu(SELECT_VALIDATE_APDU_HEADER, VALIDATE_AID);
    private static final byte[] SELECT_VALIDATED_APDU = BuildSelectApdu(SELECT_VALIDATE_APDU_HEADER, VALIDATED_AID);

    private static Boolean isValidated = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle b = intent.getExtras();
        isValidated = b.getBoolean("authenticated", false);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Called if the connection to the NFC card is lost, in order to let the application know the
     * cause for the disconnection (either a lost link, or another AID being selected by the
     * reader).
     *
     * @param reason Either DEACTIVATION_LINK_LOSS or DEACTIVATION_DESELECTED
     */
    @Override
    public void onDeactivated(int reason) {

    }

    /**
     * This method will be called when a command APDU has been received from a remote device. A
     * response APDU can be provided directly by returning a byte-array in this method. In general
     * response APDUs must be sent as quickly as possible, given the fact that the user is likely
     * holding his device over an NFC reader when this method is called.
     * <p/>
     * <p class="note">If there are multiple services that have registered for the same AIDs in
     * their meta-data entry, you will only get called if the user has explicitly selected your
     * service, either as a default or just for the next tap.
     * <p/>
     * <p class="note">This method is running on the main thread of your application. If you
     * cannot return a response APDU immediately, return null and use the {@link
     * #sendResponseApdu(byte[])} method later.
     *
     * @param commandApdu The APDU that received from the remote device
     * @param extras      A bundle containing extra data. May be null.
     * @return a byte-array containing the response APDU, or null if no response APDU can be sent
     * at this point.
     */
    // BEGIN_INCLUDE(processCommandApdu)
    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.i(TAG, "Received APDU: " + ByteArrayToHexString(commandApdu));
        // If the APDU matches the SELECT AID command for this service,
        // send the device guid, followed by a SELECT_OK status trailer (0x9000).
        if (Arrays.equals(SELECT_VALIDATE_APDU, commandApdu)) {
            Intent intent = new Intent(getApplicationContext(), ActivityTapAndPay.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(NFCTapPayService.this, "Please remove your phone on NFC Device.", Toast.LENGTH_SHORT).show();
            return UNKNOWN_CMD_SW;
        } else if (Arrays.equals(SELECT_VALIDATED_APDU, commandApdu)) {

            String device_guid = Util.getGUID(getApplicationContext());
            byte[] device_guid_Bytes = device_guid.getBytes();

            if (isValidated) {
                this.sendBroadcast(new Intent("TapPayReceiver"));
                Toast.makeText(NFCTapPayService.this, "Transaction Success", Toast.LENGTH_SHORT).show();
                return ConcatArrays(device_guid_Bytes, SELECT_OK_SW);
            } else {
                this.sendBroadcast(new Intent("TapPayReceiver"));
                Toast.makeText(NFCTapPayService.this, "Sorry transaction canceled", Toast.LENGTH_SHORT).show();
                return UNKNOWN_CMD_INVALID_PIN;
            }

        } else {
            return UNKNOWN_CMD_SW;
        }
    }
    // END_INCLUDE(processCommandApdu)

    /**
     * Build APDU for SELECT AID command. This command indicates which service a reader is
     * interested in communicating with. See ISO 7816-4.
     *
     * @param aid Application ID (AID) to select
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildSelectApdu(String header, String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return HexStringToByteArray(header + String.format("%02X",
                aid.length() / 2) + aid);
    }

    /**
     * Utility method to convert a byte array to a hexadecimal string.
     *
     * @param bytes Bytes to convert
     * @return String, containing hexadecimal representation.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    /**
     * Utility method to convert a hexadecimal string to a byte string.
     * <p/>
     * <p>Behavior with input strings containing non-hexadecimal characters is undefined.
     *
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws java.lang.IllegalArgumentException if input length is incorrect
     */
    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Utility method to concatenate two byte arrays.
     *
     * @param first First array
     * @param rest  Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
