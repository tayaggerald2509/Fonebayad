package estansaas.fonebayad.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import estansaas.fonebayad.R;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public class Util {

    public static Typeface tf;
    private static Snackbar snackbar;
    public static String MYRIAD = "MyriadPro-Regular.otf";
    public static String ROBOTO = "Roboto-Regular.ttf";
    public static String ROBOTO_LIGHT = "Roboto-Light.ttf";
    public static String MYRIAD_BOLD = "MyriadPro-Cond.otf";
    public static String MYRIAD_BOLD_ITALIC = "MyriadPro-BoldIt.otf";

    private static Calendar calendar;
    public static String EmailRegEx = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    private static String manufacturer, model;

    private static final String IMAGE_DIRECTORY_NAME = "fonebayad";


    public static Integer[] pins = {R.id.pin1, R.id.pin2, R.id.pin3, R.id.pin4};
    public static Integer[] Registration = {R.id.salutation, R.id.txtFname, R.id.txtLname, R.id.txtFoneEmail, R.id.txtPersonalEmail, R.id.txtConfirmEmail, R.id.txtPin, R.id.txtConfirmPin};

    // Main
    public static String[] Tabs = {"Dashboard", "Notifications", "Offers", "Messages"};
    public static Integer[] Tabs_Icon = {R.drawable.ic_av_timer_white_24dp_active, R.drawable.ic_action_ic_notifications_none_white_24dp_active, R.drawable.ic_action_ic_card_giftcard_black_48dp_active, R.drawable.ic_action_ic_email_white_48dp_active};

    public static Typeface setTypeface(Context context, String font) {
        tf = Typeface.createFromAsset(context.getAssets(), font);
        return tf;
    }

    public static String getGUID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceType() {
        return "ANDROID";
    }

    public static String getDeviceName() {
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return Capitalize(manufacturer);
        }
        return Capitalize(manufacturer) + " " + model;
    }

    private static String Capitalize(String paramString) {
        if (paramString == null || paramString.length() == 0) {
            return "";
        }
        char first = paramString.charAt(0);
        if (Character.isUpperCase(first)) {
            return paramString;
        } else {
            return Character.toUpperCase(first) + paramString.substring(1);
        }
    }

    public static EditText setCompoundDrawables(EditText editText, Drawable icon) {
        editText.setCompoundDrawables(null, null, icon, null);
        return editText;
    }

    public static Drawable resizeDrawable(Context context, int drawable_id) {
        Drawable icon = context.getResources().getDrawable(drawable_id);
        icon.setBounds(new Rect(0, 0, icon.getIntrinsicWidth() / 2, icon
                .getIntrinsicHeight() / 2));

        return icon;
    }

    public static Boolean EmailValidator(String paramString) {
        Pattern pattern = Pattern.compile(EmailRegEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(paramString);
        return matcher.matches();
    }

    public static void ShowMessage(CoordinatorLayout coordinatorLayout, String msg) {
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void ShowMessageWithAction(final CoordinatorLayout coordinatorLayout, String msg, String action, final int code) {
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (code) {
                            case 1:
                                Button btn = (Button) coordinatorLayout.findViewById(R.id.btnProceed);
                                btn.performClick();
                                break;
                        }
                    }
                });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public static void startNextActivity(Activity activity, Class<?> next) {
        Intent intent = new Intent(activity, next);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public static Date getCurrentDate() throws ParseException {
        return new Date();
    }

    public static Date getWeekDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static Date convertDate(String paramString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        return format.parse(paramString);
    }

    public static Date convert(String paramString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(paramString);
    }

    public static String StringconvertDate(String paramString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date newFormat = format.parse(paramString);
        format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(newFormat);
    }

    public static String getDay() {
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getMonth() {
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDateDay() {
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static <T> boolean checkIfNull(T objectToCheck) {
        return objectToCheck == null ? true : false;
    }

    public static boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        Boolean isServiceFound = false;
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).topActivity.toString().equalsIgnoreCase("ComponentInfo{com.lyo.AutoMessage/com.lyo.AutoMessage.TextLogList}")) {
                isServiceFound = true;
            }
        }
        return isServiceFound;
    }

    public static Uri getMediaFile() {
        return Uri.fromFile(getOutputMediaFile());
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    public static void ShowNeutralDialog(Context context, String title, String content, String neutral, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .contentGravity(GravityEnum.CENTER)
                .titleGravity(GravityEnum.CENTER)
                .titleColor(context.getResources().getColor(R.color.app_color))
                .theme(Theme.LIGHT)
                .neutralText(neutral)
                .onNeutral(callback)
                .buttonsGravity(GravityEnum.CENTER)
                .cancelable(false)
                .neutralColor(context.getResources().getColor(R.color.app_color))
                .show();
    }

    public static void ShowDialog(Context context, String title, String content, String postive, String negative, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .contentGravity(GravityEnum.CENTER)
                .titleGravity(GravityEnum.CENTER)

                .theme(Theme.LIGHT)
                .positiveText(postive)
                .negativeText(negative)
                .onAny(callback)
                .buttonsGravity(GravityEnum.CENTER)
                .cancelable(false)
                .positiveColor(context.getResources().getColor(R.color.app_color))
                .negativeColor(context.getResources().getColor(R.color.app_color))
                .show();
    }

}
