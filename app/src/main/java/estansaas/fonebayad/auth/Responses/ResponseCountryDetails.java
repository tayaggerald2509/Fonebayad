package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import estansaas.fonebayad.model.ModelCountryDetail;

/**
 * Created by gerald.tayag on 11/6/2015.
 */
public class ResponseCountryDetails {

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<ModelCountryDetail> modelCountryDetails;

    public ResponseCountryDetails() {
    }

    public ResponseCountryDetails(String status, String message, List<ModelCountryDetail> modelCountryDetails) {
        this.status = status;
        this.message = message;
        this.modelCountryDetails = modelCountryDetails;
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

    public List<ModelCountryDetail> getModelCountryDetails() {
        return modelCountryDetails;
    }

    public void setModelCountryDetails(List<ModelCountryDetail> modelCountryDetails) {
        this.modelCountryDetails = modelCountryDetails;
    }
}
