package estansaas.fonebayad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerald.tayag on 10/29/2015.
 */
public class ModelUserBillStatemenetData {

    @SerializedName("userBillStatements")
    @Expose
    private List<ModelBillStatement> modelBillStatementList = new ArrayList<ModelBillStatement>();

    public ModelUserBillStatemenetData() {
    }

    public ModelUserBillStatemenetData(List<ModelBillStatement> modelBillStatementList) {
        this.modelBillStatementList = modelBillStatementList;
    }

    public List<ModelBillStatement> getModelBillStatementList() {
        return modelBillStatementList;
    }

    public void setModelBillStatementList(List<ModelBillStatement> modelBillStatementList) {
        this.modelBillStatementList = modelBillStatementList;
    }
}
