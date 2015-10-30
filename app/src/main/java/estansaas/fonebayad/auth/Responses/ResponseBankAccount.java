package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import estansaas.fonebayad.model.ModelBankAccount;

/**
 * Created by gerald.tayag on 10/21/2015.
 */
public class ResponseBankAccount {

    @Expose
    @SerializedName("status")
    public String status;

    @Expose
    @SerializedName("bankaccount")
    public List<ModelBankAccount> modelBankAccount;

    public ResponseBankAccount() {
    }

    public ResponseBankAccount(String status, List<ModelBankAccount> modelBankAccount) {
        this.status = status;
        this.modelBankAccount = modelBankAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ModelBankAccount> getModelBankAccount() {
        return modelBankAccount;
    }

    public void setModelBankAccount(List<ModelBankAccount> modelBankAccount) {
        this.modelBankAccount = modelBankAccount;
    }
}
