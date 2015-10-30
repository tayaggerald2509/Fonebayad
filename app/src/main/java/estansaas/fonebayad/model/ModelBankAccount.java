package estansaas.fonebayad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/21/2015.
 */

public class ModelBankAccount {

    @SerializedName("bankaccount_id")
    @Expose
    public String bankaccount_id;

    @SerializedName("bankaccount_accountname")
    @Expose
    public String bankaccount_accountname;

    @SerializedName("bankaccount_type")
    @Expose
    public String bankaccount_type;

    @SerializedName("bankaccount_userentity")
    @Expose
    public String bankaccount_userentity;

    @SerializedName("bankaccount_branch")
    @Expose
    public String bankaccount_branch;

    @SerializedName("bankaccount_bsbnumber")
    @Expose
    public String bankaccount_bsbnumber;

    @SerializedName("bankaccount_accountnumber")
    @Expose
    public String bankaccount_accountnumber;

    @SerializedName("bankaccount_directdebitorder")
    @Expose
    public String bankaccount_directdebitorder;

    @SerializedName("bankaccount_currency")
    @Expose
    public String bankaccount_currency;

    @SerializedName("bankaccount_amount")
    @Expose
    public String bankaccount_amount;

    @SerializedName("bankaccount_createdby")
    @Expose
    public String bankaccount_createdby;

    @SerializedName("bankaccount_datecreated")
    @Expose
    public String bankaccount_datecreated;

    @SerializedName("bankaccount_status")
    @Expose
    public String bankaccount_status;

    @SerializedName("bankaccount_sortorder")
    @Expose
    public String bankaccount_sortorder;

    @SerializedName("branch_id")
    @Expose
    public String branch_id;

    @SerializedName("branch_name")
    @Expose
    public String branch_name;

    @SerializedName("branch_address")
    @Expose
    public String branch_address;

    @SerializedName("branch_phonenumber")
    @Expose
    public String branch_phonenumber;

    @SerializedName("branch_bankid")
    @Expose
    public String branch_bankid;

    @SerializedName("branch_createdby")
    @Expose
    public String branch_createdby;

    @SerializedName("branch_datecreated")
    @Expose
    public String branch_datecreated;

    @SerializedName("branch_datemodified")
    @Expose
    public String branch_datemodified;

    @SerializedName("branch_status")
    @Expose
    public String branch_status;

    @SerializedName("bank_id")
    @Expose
    public String bank_id;

    @SerializedName("bank_name")
    @Expose
    public String bank_name;

    @SerializedName("bank_code")
    @Expose
    public String bank_code;

    @SerializedName("bank_account_type")
    @Expose
    public String bank_account_type;

    @SerializedName("bank_isproduct")
    @Expose
    public String bank_isproduct;

    @SerializedName("bank_order")
    @Expose
    public String bank_order;

    @SerializedName("bank_createdby")
    @Expose
    public String bank_createdby;

    @SerializedName("bank_datecreated")
    @Expose
    public String bank_datecreated;

    @SerializedName("bank_datemodified")
    @Expose
    public String bank_datemodified;

    @SerializedName("bank_logo")
    @Expose
    public String bank_logo;

    @SerializedName("bank_status")
    @Expose
    public String bank_status;

    public ModelBankAccount() {
    }

    public ModelBankAccount(String bankaccount_id, String bankaccount_accountname, String bankaccount_type, String bankaccount_userentity, String bankaccount_branch, String bankaccount_bsbnumber, String bankaccount_accountnumber, String bankaccount_directdebitorder, String bankaccount_currency, String bankaccount_amount, String bankaccount_createdby, String bankaccount_datecreated, String bankaccount_status, String bankaccount_sortorder, String branch_id, String branch_name, String branch_address, String branch_phonenumber, String branch_bankid, String branch_createdby, String branch_datecreated, String branch_datemodified, String branch_status, String bank_id, String bank_name, String bank_code, String bank_account_type, String bank_isproduct, String bank_order, String bank_createdby, String bank_datecreated, String bank_datemodified, String bank_logo, String bank_status) {
        this.bankaccount_id = bankaccount_id;
        this.bankaccount_accountname = bankaccount_accountname;
        this.bankaccount_type = bankaccount_type;
        this.bankaccount_userentity = bankaccount_userentity;
        this.bankaccount_branch = bankaccount_branch;
        this.bankaccount_bsbnumber = bankaccount_bsbnumber;
        this.bankaccount_accountnumber = bankaccount_accountnumber;
        this.bankaccount_directdebitorder = bankaccount_directdebitorder;
        this.bankaccount_currency = bankaccount_currency;
        this.bankaccount_amount = bankaccount_amount;
        this.bankaccount_createdby = bankaccount_createdby;
        this.bankaccount_datecreated = bankaccount_datecreated;
        this.bankaccount_status = bankaccount_status;
        this.bankaccount_sortorder = bankaccount_sortorder;
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.branch_address = branch_address;
        this.branch_phonenumber = branch_phonenumber;
        this.branch_bankid = branch_bankid;
        this.branch_createdby = branch_createdby;
        this.branch_datecreated = branch_datecreated;
        this.branch_datemodified = branch_datemodified;
        this.branch_status = branch_status;
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.bank_code = bank_code;
        this.bank_account_type = bank_account_type;
        this.bank_isproduct = bank_isproduct;
        this.bank_order = bank_order;
        this.bank_createdby = bank_createdby;
        this.bank_datecreated = bank_datecreated;
        this.bank_datemodified = bank_datemodified;
        this.bank_logo = bank_logo;
        this.bank_status = bank_status;
    }

    public String getBankaccount_id() {
        return bankaccount_id;
    }

    public void setBankaccount_id(String bankaccount_id) {
        this.bankaccount_id = bankaccount_id;
    }

    public String getBankaccount_accountname() {
        return bankaccount_accountname;
    }

    public void setBankaccount_accountname(String bankaccount_accountname) {
        this.bankaccount_accountname = bankaccount_accountname;
    }

    public String getBankaccount_type() {
        return bankaccount_type;
    }

    public void setBankaccount_type(String bankaccount_type) {
        this.bankaccount_type = bankaccount_type;
    }

    public String getBankaccount_userentity() {
        return bankaccount_userentity;
    }

    public void setBankaccount_userentity(String bankaccount_userentity) {
        this.bankaccount_userentity = bankaccount_userentity;
    }

    public String getBankaccount_branch() {
        return bankaccount_branch;
    }

    public void setBankaccount_branch(String bankaccount_branch) {
        this.bankaccount_branch = bankaccount_branch;
    }

    public String getBankaccount_bsbnumber() {
        return bankaccount_bsbnumber;
    }

    public void setBankaccount_bsbnumber(String bankaccount_bsbnumber) {
        this.bankaccount_bsbnumber = bankaccount_bsbnumber;
    }

    public String getBankaccount_accountnumber() {
        return bankaccount_accountnumber;
    }

    public void setBankaccount_accountnumber(String bankaccount_accountnumber) {
        this.bankaccount_accountnumber = bankaccount_accountnumber;
    }

    public String getBankaccount_directdebitorder() {
        return bankaccount_directdebitorder;
    }

    public void setBankaccount_directdebitorder(String bankaccount_directdebitorder) {
        this.bankaccount_directdebitorder = bankaccount_directdebitorder;
    }

    public String getBankaccount_currency() {
        return bankaccount_currency;
    }

    public void setBankaccount_currency(String bankaccount_currency) {
        this.bankaccount_currency = bankaccount_currency;
    }

    public String getBankaccount_amount() {
        return bankaccount_amount;
    }

    public void setBankaccount_amount(String bankaccount_amount) {
        this.bankaccount_amount = bankaccount_amount;
    }

    public String getBankaccount_createdby() {
        return bankaccount_createdby;
    }

    public void setBankaccount_createdby(String bankaccount_createdby) {
        this.bankaccount_createdby = bankaccount_createdby;
    }

    public String getBankaccount_datecreated() {
        return bankaccount_datecreated;
    }

    public void setBankaccount_datecreated(String bankaccount_datecreated) {
        this.bankaccount_datecreated = bankaccount_datecreated;
    }

    public String getBankaccount_status() {
        return bankaccount_status;
    }

    public void setBankaccount_status(String bankaccount_status) {
        this.bankaccount_status = bankaccount_status;
    }

    public String getBankaccount_sortorder() {
        return bankaccount_sortorder;
    }

    public void setBankaccount_sortorder(String bankaccount_sortorder) {
        this.bankaccount_sortorder = bankaccount_sortorder;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_address() {
        return branch_address;
    }

    public void setBranch_address(String branch_address) {
        this.branch_address = branch_address;
    }

    public String getBranch_phonenumber() {
        return branch_phonenumber;
    }

    public void setBranch_phonenumber(String branch_phonenumber) {
        this.branch_phonenumber = branch_phonenumber;
    }

    public String getBranch_bankid() {
        return branch_bankid;
    }

    public void setBranch_bankid(String branch_bankid) {
        this.branch_bankid = branch_bankid;
    }

    public String getBranch_createdby() {
        return branch_createdby;
    }

    public void setBranch_createdby(String branch_createdby) {
        this.branch_createdby = branch_createdby;
    }

    public String getBranch_datecreated() {
        return branch_datecreated;
    }

    public void setBranch_datecreated(String branch_datecreated) {
        this.branch_datecreated = branch_datecreated;
    }

    public String getBranch_datemodified() {
        return branch_datemodified;
    }

    public void setBranch_datemodified(String branch_datemodified) {
        this.branch_datemodified = branch_datemodified;
    }

    public String getBranch_status() {
        return branch_status;
    }

    public void setBranch_status(String branch_status) {
        this.branch_status = branch_status;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_account_type() {
        return bank_account_type;
    }

    public void setBank_account_type(String bank_account_type) {
        this.bank_account_type = bank_account_type;
    }

    public String getBank_isproduct() {
        return bank_isproduct;
    }

    public void setBank_isproduct(String bank_isproduct) {
        this.bank_isproduct = bank_isproduct;
    }

    public String getBank_order() {
        return bank_order;
    }

    public void setBank_order(String bank_order) {
        this.bank_order = bank_order;
    }

    public String getBank_createdby() {
        return bank_createdby;
    }

    public void setBank_createdby(String bank_createdby) {
        this.bank_createdby = bank_createdby;
    }

    public String getBank_datecreated() {
        return bank_datecreated;
    }

    public void setBank_datecreated(String bank_datecreated) {
        this.bank_datecreated = bank_datecreated;
    }

    public String getBank_datemodified() {
        return bank_datemodified;
    }

    public void setBank_datemodified(String bank_datemodified) {
        this.bank_datemodified = bank_datemodified;
    }

    public String getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(String bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getBank_status() {
        return bank_status;
    }

    public void setBank_status(String bank_status) {
        this.bank_status = bank_status;
    }

}
