package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import estansaas.fonebayad.model.ModelBillStatementData;

/**
 * Created by gerald.tayag on 10/12/2015.
 */

public class ResponseBillStatement {

    @SerializedName("data")
    @Expose
    private ModelBillStatementData modelBillStatementData;

    @SerializedName("status")
    @Expose
    private String status;


    public ResponseBillStatement() {
    }

    public ResponseBillStatement(ModelBillStatementData modelBillStatementData, String status) {
        this.modelBillStatementData = modelBillStatementData;
        this.status = status;
    }

    public ModelBillStatementData getModelBillStatementData() {
        return modelBillStatementData;
    }

    public void setModelBillStatementData(ModelBillStatementData modelBillStatementData) {
        this.modelBillStatementData = modelBillStatementData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("data", modelBillStatementData.toJSON());
            jsonObject.put("status", status);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}

