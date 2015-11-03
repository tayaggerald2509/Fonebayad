package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 11/3/2015.
 */
public class ModelPaybill {

    @SerializedName("statementID")
    private String statementID;

    @SerializedName("userID")
    private String userID;

    @SerializedName("status")
    private String status;

    @SerializedName("paymentAmount")
    private String paymentAmount;

    @SerializedName("newBalance")
    private String newBalance;

    @SerializedName("bankId")
    private String bankId;

    @SerializedName("transactionAmount")
    private String transactionAmount;

    @SerializedName("transactionLine")
    private String transactionLine;

    public ModelPaybill() {

    }

    public ModelPaybill(String statementID, String userID, String status, String paymentAmount, String newBalance, String bankId, String transactionAmount, String transactionLine) {
        this.statementID = statementID;
        this.userID = userID;
        this.status = status;
        this.paymentAmount = paymentAmount;
        this.newBalance = newBalance;
        this.bankId = bankId;
        this.transactionAmount = transactionAmount;
        this.transactionLine = transactionLine;
    }

    public String getStatementID() {
        return statementID;
    }

    public void setStatementID(String statementID) {
        this.statementID = statementID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(String newBalance) {
        this.newBalance = newBalance;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionLine() {
        return transactionLine;
    }

    public void setTransactionLine(String transactionLine) {
        this.transactionLine = transactionLine;
    }
}
