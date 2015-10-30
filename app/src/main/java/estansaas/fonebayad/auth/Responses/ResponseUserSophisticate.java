package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import estansaas.fonebayad.model.ModelSophisticate;

/**
 * Created by gerald.tayag on 10/29/2015.
 */
public class ResponseUserSophisticate {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    private ModelSophisticate modelSophisticate;

    public ResponseUserSophisticate() {

    }

    public ResponseUserSophisticate(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseUserSophisticate(String message, String status, ModelSophisticate modelSophisticate) {
        this.message = message;
        this.status = status;
        this.modelSophisticate = modelSophisticate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelSophisticate getModelSophisticate() {
        return modelSophisticate;
    }

    public void setModelSophisticate(ModelSophisticate modelSophisticate) {
        this.modelSophisticate = modelSophisticate;
    }
}
