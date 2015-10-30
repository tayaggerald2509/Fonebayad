package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/6/2015.
 */
public class Response {

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public String status;


    public boolean isValid;

    public Response() {
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
