package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.SerializedName;

import estansaas.fonebayad.model.ModelSyncData;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
public class ResponseSyncData {

    @SerializedName("data")
    private ModelSyncData modelSyncData;

    @SerializedName("status")
    private int status;

    public ResponseSyncData() {
    }

    public ResponseSyncData(ModelSyncData modelSyncData, int status) {
        this.modelSyncData = modelSyncData;
        this.status = status;
    }

    public ModelSyncData getModelSyncData() {
        return modelSyncData;
    }

    public void setModelSyncData(ModelSyncData modelSyncData) {
        this.modelSyncData = modelSyncData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
