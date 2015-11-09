package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import estansaas.fonebayad.model.ModelOffer;

/**
 * Created by gerald.tayag on 10/30/2015.
 */
public class ResponseOffer {

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("data")
    public List<ModelOffer> modelOfferList;

    public ResponseOffer() {
    }

    public ResponseOffer(String message, String status, List<ModelOffer> modelOfferList) {
        this.message = message;
        this.status = status;
        this.modelOfferList = modelOfferList;
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

    public List<ModelOffer> getModelOfferList() {
        return modelOfferList;
    }

    public void setModelOfferList(List<ModelOffer> modelOfferList) {
        this.modelOfferList = modelOfferList;
    }


}
