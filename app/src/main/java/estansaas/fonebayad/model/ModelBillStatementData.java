package estansaas.fonebayad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerald.tayag on 10/13/2015.
 */
public class ModelBillStatementData {

    @SerializedName("billstatements")
    @Expose
    private List<ModelBillStatement> modelBillStatementList = new ArrayList<ModelBillStatement>();

    public ModelBillStatementData() {
    }

    public ModelBillStatementData(List<ModelBillStatement> modelBillStatementList) {
        this.modelBillStatementList = modelBillStatementList;
    }

    public List<ModelBillStatement> getModelBillStatementList() {
        return modelBillStatementList;
    }

    public void setModelBillStatementList(List<ModelBillStatement> modelBillStatementList) {
        this.modelBillStatementList = modelBillStatementList;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("billstatements", getModelBillStatementList());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
