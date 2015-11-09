package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import estansaas.fonebayad.model.Login;

/**
 * Created by gerald.tayag on 10/14/2015.
 */
public class ResponseLogin {

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("data")
    private Login loginModel;

    @Expose
    @SerializedName("status")
    private String status;

    public ResponseLogin(Login loginModel, String status) {
        this.loginModel = loginModel;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Login getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(Login loginModel) {
        this.loginModel = loginModel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
