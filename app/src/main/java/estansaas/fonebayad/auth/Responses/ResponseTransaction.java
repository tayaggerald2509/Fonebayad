package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import estansaas.fonebayad.model.ModelTransaction;

/**
 * Created by gerald.tayag on 11/4/2015.
 */
public class ResponseTransaction {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ModelTransaction> modelTransaction;

    public ResponseTransaction() {
    }

    public ResponseTransaction(String status, String message, List<ModelTransaction> modelTransaction) {
        this.status = status;
        this.message = message;
        this.modelTransaction = modelTransaction;
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

    public List<ModelTransaction> getModelTransaction() {
        return modelTransaction;
    }

    public void setModelTransaction(List<ModelTransaction> modelTransaction) {
        this.modelTransaction = modelTransaction;
    }
}
