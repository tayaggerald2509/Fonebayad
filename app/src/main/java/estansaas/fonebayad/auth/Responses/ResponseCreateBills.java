package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 11/11/2015.
 */
public class ResponseCreateBills {
    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("bill_id")
    public String bill_id;

    public ResponseCreateBills(String message, String bill_id) {
        this.message = message;
        this.bill_id = bill_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}
