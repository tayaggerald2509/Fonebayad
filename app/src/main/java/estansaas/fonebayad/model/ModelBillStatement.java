package estansaas.fonebayad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gerald.tayag on 10/12/2015.
 */

public class ModelBillStatement {

    @SerializedName("bill_id")
    @Expose
    private String billId;

    @SerializedName("bill_biller")
    @Expose
    private String bill_biller;

    @SerializedName("bill_accountnumber")
    @Expose
    private String bill_accountnumber;

    @SerializedName("bill_transactionnumber")
    @Expose
    private String bill_transactionnumber;

    @SerializedName("bill_currency")
    @Expose
    private String bill_currency;

    @SerializedName("bill_amount")
    @Expose
    private String bill_amount;

    @SerializedName("bill_balance")
    @Expose
    private String bill_balance;

    @SerializedName("bill_status")
    @Expose
    private String bill_status;

    @SerializedName("bill_sync")
    @Expose
    private String bill_sync;

    @SerializedName("bill_daysleftonpay")
    @Expose
    private String bill_daysleftonpay;

    @SerializedName("bill_trash_status")
    @Expose
    private String bill_trash_status;

    @SerializedName("bill_attachment")
    @Expose
    private String bill_attachment;

    @SerializedName("bill_duedate")
    @Expose
    private String bill_duedate;

    @SerializedName("bill_duedatestamp")
    @Expose
    private String bill_duedatestamp;

    @SerializedName("bill_scheduleofpayment")
    @Expose
    private String bill_scheduleofpayment;

    @SerializedName("bill_userid")
    @Expose
    private String bill_userid;

    @SerializedName("bill_share")
    @Expose
    private String bill_share;

    @SerializedName("bill_paymentmethod")
    @Expose
    private String bill_paymentmethod;

    @SerializedName("bill_shareverification")
    @Expose
    private String bill_shareverification;

    @SerializedName("bill_sharedate")
    @Expose
    private String bill_sharedate;

    @SerializedName("bill_userentity")
    @Expose
    private String bill_userentity;

    @SerializedName("bill_type")
    @Expose
    private String bill_type;

    @SerializedName("bill_createdby")
    @Expose
    private String bill_createdby;

    @SerializedName("bill_datecreated")
    @Expose
    private String bill_datecreated;

    @SerializedName("bill_datemodified")
    @Expose
    private String bill_datemodified;

    @SerializedName("bill_sortorder")
    @Expose
    private String bill_sortorder;

    @SerializedName("bill_shareto")
    @Expose
    private String bill_shareto;

    @SerializedName("bill_periodicflag")
    @Expose
    private String bill_periodicflag;

    @SerializedName("bill_paidby")
    @Expose
    private String bill_paidby;

    @SerializedName("biller_id")
    @Expose
    private String biller_id;

    @SerializedName("biller_name")
    @Expose
    private String biller_name;

    @SerializedName("biller_logo")
    @Expose
    private String biller_logo;

    @SerializedName("biller_category")
    @Expose
    private String biller_category;

    @SerializedName("biller_createdby")
    @Expose
    private String biller_createdby;

    @SerializedName("biller_datecreated")
    @Expose
    private String biller_datecreated;

    @SerializedName("biller_status")
    @Expose
    private String biller_status;

    @SerializedName("biller_account")
    @Expose
    private String biller_account;

    public ModelBillStatement() {
    }

    public ModelBillStatement(String billId, String bill_biller, String bill_accountnumber, String bill_transactionnumber, String bill_currency, String bill_amount, String bill_balance, String bill_status, String bill_sync, String bill_daysleftonpay, String bill_trash_status, String bill_attachment, String bill_duedate, String bill_duedatestamp, String bill_scheduleofpayment, String bill_userid, String bill_share, String bill_paymentmethod, String bill_shareverification, String bill_sharedate, String bill_userentity, String bill_type, String bill_createdby, String bill_datecreated, String bill_datemodified, String bill_sortorder, String bill_shareto, String bill_periodicflag, String bill_paidby, String biller_id, String biller_name, String biller_logo, String biller_category, String biller_createdby, String biller_datecreated, String biller_status, String biller_account) {
        this.billId = billId;
        this.bill_biller = bill_biller;
        this.bill_accountnumber = bill_accountnumber;
        this.bill_transactionnumber = bill_transactionnumber;
        this.bill_currency = bill_currency;
        this.bill_amount = bill_amount;
        this.bill_balance = bill_balance;
        this.bill_status = bill_status;
        this.bill_sync = bill_sync;
        this.bill_daysleftonpay = bill_daysleftonpay;
        this.bill_trash_status = bill_trash_status;
        this.bill_attachment = bill_attachment;
        this.bill_duedate = bill_duedate;
        this.bill_duedatestamp = bill_duedatestamp;
        this.bill_scheduleofpayment = bill_scheduleofpayment;
        this.bill_userid = bill_userid;
        this.bill_share = bill_share;
        this.bill_paymentmethod = bill_paymentmethod;
        this.bill_shareverification = bill_shareverification;
        this.bill_sharedate = bill_sharedate;
        this.bill_userentity = bill_userentity;
        this.bill_type = bill_type;
        this.bill_createdby = bill_createdby;
        this.bill_datecreated = bill_datecreated;
        this.bill_datemodified = bill_datemodified;
        this.bill_sortorder = bill_sortorder;
        this.bill_shareto = bill_shareto;
        this.bill_periodicflag = bill_periodicflag;
        this.bill_paidby = bill_paidby;
        this.biller_id = biller_id;
        this.biller_name = biller_name;
        this.biller_logo = biller_logo;
        this.biller_category = biller_category;
        this.biller_createdby = biller_createdby;
        this.biller_datecreated = biller_datecreated;
        this.biller_status = biller_status;
        this.biller_account = biller_account;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBill_biller() {
        return bill_biller;
    }

    public void setBill_biller(String bill_biller) {
        this.bill_biller = bill_biller;
    }

    public String getBill_accountnumber() {
        return bill_accountnumber;
    }

    public void setBill_accountnumber(String bill_accountnumber) {
        this.bill_accountnumber = bill_accountnumber;
    }

    public String getBill_transactionnumber() {
        return bill_transactionnumber;
    }

    public void setBill_transactionnumber(String bill_transactionnumber) {
        this.bill_transactionnumber = bill_transactionnumber;
    }

    public String getBill_currency() {
        return bill_currency;
    }

    public void setBill_currency(String bill_currency) {
        this.bill_currency = bill_currency;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_balance() {
        return bill_balance;
    }

    public void setBill_balance(String bill_balance) {
        this.bill_balance = bill_balance;
    }

    public String getBill_status() {
        return bill_status;
    }

    public void setBill_status(String bill_status) {
        this.bill_status = bill_status;
    }

    public String getBill_sync() {
        return bill_sync;
    }

    public void setBill_sync(String bill_sync) {
        this.bill_sync = bill_sync;
    }

    public String getBill_daysleftonpay() {
        return bill_daysleftonpay;
    }

    public void setBill_daysleftonpay(String bill_daysleftonpay) {
        this.bill_daysleftonpay = bill_daysleftonpay;
    }

    public String getBill_trash_status() {
        return bill_trash_status;
    }

    public void setBill_trash_status(String bill_trash_status) {
        this.bill_trash_status = bill_trash_status;
    }

    public String getBill_attachment() {
        return bill_attachment;
    }

    public void setBill_attachment(String bill_attachment) {
        this.bill_attachment = bill_attachment;
    }

    public String getBill_duedate() {
        return bill_duedate;
    }

    public void setBill_duedate(String bill_duedate) {
        this.bill_duedate = bill_duedate;
    }

    public String getBill_duedatestamp() {
        return bill_duedatestamp;
    }

    public void setBill_duedatestamp(String bill_duedatestamp) {
        this.bill_duedatestamp = bill_duedatestamp;
    }

    public String getBill_scheduleofpayment() {
        return bill_scheduleofpayment;
    }

    public void setBill_scheduleofpayment(String bill_scheduleofpayment) {
        this.bill_scheduleofpayment = bill_scheduleofpayment;
    }

    public String getBill_userid() {
        return bill_userid;
    }

    public void setBill_userid(String bill_userid) {
        this.bill_userid = bill_userid;
    }

    public String getBill_share() {
        return bill_share;
    }

    public void setBill_share(String bill_share) {
        this.bill_share = bill_share;
    }

    public String getBill_paymentmethod() {
        return bill_paymentmethod;
    }

    public void setBill_paymentmethod(String bill_paymentmethod) {
        this.bill_paymentmethod = bill_paymentmethod;
    }

    public String getBill_shareverification() {
        return bill_shareverification;
    }

    public void setBill_shareverification(String bill_shareverification) {
        this.bill_shareverification = bill_shareverification;
    }

    public String getBill_sharedate() {
        return bill_sharedate;
    }

    public void setBill_sharedate(String bill_sharedate) {
        this.bill_sharedate = bill_sharedate;
    }

    public String getBill_userentity() {
        return bill_userentity;
    }

    public void setBill_userentity(String bill_userentity) {
        this.bill_userentity = bill_userentity;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getBill_createdby() {
        return bill_createdby;
    }

    public void setBill_createdby(String bill_createdby) {
        this.bill_createdby = bill_createdby;
    }

    public String getBill_datecreated() {
        return bill_datecreated;
    }

    public void setBill_datecreated(String bill_datecreated) {
        this.bill_datecreated = bill_datecreated;
    }

    public String getBill_datemodified() {
        return bill_datemodified;
    }

    public void setBill_datemodified(String bill_datemodified) {
        this.bill_datemodified = bill_datemodified;
    }

    public String getBill_sortorder() {
        return bill_sortorder;
    }

    public void setBill_sortorder(String bill_sortorder) {
        this.bill_sortorder = bill_sortorder;
    }

    public String getBill_shareto() {
        return bill_shareto;
    }

    public void setBill_shareto(String bill_shareto) {
        this.bill_shareto = bill_shareto;
    }

    public String getBill_periodicflag() {
        return bill_periodicflag;
    }

    public void setBill_periodicflag(String bill_periodicflag) {
        this.bill_periodicflag = bill_periodicflag;
    }

    public String getBill_paidby() {
        return bill_paidby;
    }

    public void setBill_paidby(String bill_paidby) {
        this.bill_paidby = bill_paidby;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
    }

    public String getBiller_name() {
        return biller_name;
    }

    public void setBiller_name(String biller_name) {
        this.biller_name = biller_name;
    }

    public String getBiller_logo() {
        return biller_logo;
    }

    public void setBiller_logo(String biller_logo) {
        this.biller_logo = biller_logo;
    }

    public String getBiller_category() {
        return biller_category;
    }

    public void setBiller_category(String biller_category) {
        this.biller_category = biller_category;
    }

    public String getBiller_createdby() {
        return biller_createdby;
    }

    public void setBiller_createdby(String biller_createdby) {
        this.biller_createdby = biller_createdby;
    }

    public String getBiller_datecreated() {
        return biller_datecreated;
    }

    public void setBiller_datecreated(String biller_datecreated) {
        this.biller_datecreated = biller_datecreated;
    }

    public String getBiller_status() {
        return biller_status;
    }

    public void setBiller_status(String biller_status) {
        this.biller_status = biller_status;
    }

    public String getBiller_account() {
        return biller_account;
    }

    public void setBiller_account(String biller_account) {
        this.biller_account = biller_account;
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bill_id", biller_id);
            jsonObject.put("bill_biller", bill_biller);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }


}
