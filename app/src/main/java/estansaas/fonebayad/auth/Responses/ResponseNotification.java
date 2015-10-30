package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.SerializedName;

import estansaas.fonebayad.model.ModelUserBillStatemenetData;

/**
 * Created by gerald.tayag on 10/29/2015.
 */
public class ResponseNotification {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ModelUserBillStatemenetData modelUserBillStatemenetData;

    public ResponseNotification() {
    }

    public ResponseNotification(String message, ModelUserBillStatemenetData modelUserBillStatemenetData) {
        this.message = message;
        this.modelUserBillStatemenetData = modelUserBillStatemenetData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelUserBillStatemenetData getModelUserBillStatemenetData() {
        return modelUserBillStatemenetData;
    }

    public void setModelUserBillStatemenetData(ModelUserBillStatemenetData modelUserBillStatemenetData) {
        this.modelUserBillStatemenetData = modelUserBillStatemenetData;
    }
}
