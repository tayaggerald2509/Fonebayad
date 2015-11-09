package estansaas.fonebayad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gerald.tayag on 11/4/2015.
 */
public class ModelTransaction implements Serializable {

    @Expose
    @SerializedName("transfer_id")
    private String transfer_id;

    @Expose
    @SerializedName("transfer_userid")
    private String transfer_userid;

    @Expose
    @SerializedName("transfer_base_amount")
    private String transfer_base_amount;

    @Expose
    @SerializedName("transfer_base_currency")
    private String transfer_base_currency;

    @Expose
    @SerializedName("transfer_amount")
    private String transfer_amount;

    @Expose
    @SerializedName("transfer_fee")
    private String transfer_fee;

    @Expose
    @SerializedName("transfer_amount_currency")
    private String transfer_amount_currency;

    @Expose
    @SerializedName("transfer_rate")
    private String transfer_rate;

    @Expose
    @SerializedName("transfer_purpose")
    private String transfer_purpose;

    @Expose
    @SerializedName("transfer_email")
    private String transfer_email;

    @Expose
    @SerializedName("transfer_type")
    private String transfer_type;

    @Expose
    @SerializedName("transfer_status")
    private int transfer_status;

    @Expose
    @SerializedName("transfer_code")
    private String transfer_code;

    @Expose
    @SerializedName("transfer_date")
    private String transfer_date;

    @Expose
    @SerializedName("transfer_schedule")
    private String transfer_schedule;

    @Expose
    @SerializedName("transfer_auth")
    private String transfer_auth;

    @Expose
    @SerializedName("transfer_origin")
    private String transfer_origin;

    @Expose
    @SerializedName("transfer_bankname")
    private String transfer_bankname;

    @Expose
    @SerializedName("transfer_currency")
    private String transfer_currency;

    @Expose
    @SerializedName("transfer_receiverid")
    private String transfer_receiverid;

    @Expose
    @SerializedName("transfer_datecreated")
    private String transfer_datecreated;

    @Expose
    @SerializedName("bpo_id")
    private String bpo_id;

    @Expose
    @SerializedName("transfer_bank_from")
    private String transfer_bank_from;

    @Expose
    @SerializedName("transfer_step")
    private String transfer_step;

    @Expose
    @SerializedName("transfer_batch")
    private String transfer_batch;

    @SerializedName("transfer_verify")
    private String transfer_verify;

    @Expose
    @SerializedName("transfer_countryid")
    private String transfer_countryid;

    @Expose
    @SerializedName("transfer_stateid")
    private String transfer_stateid;

    @Expose
    @SerializedName("transfer_attachment")
    private String transfer_attachment;

    @Expose
    @SerializedName("transfer_reason")
    private String transfer_reason;

    @Expose
    @SerializedName("transfer_reference")
    private String transfer_reference;

    @Expose
    @SerializedName("recipient_id")
    private String recipient_id;

    @Expose
    @SerializedName("recipient_ofw_id")
    private String recipient_ofw_id;

    @Expose
    @SerializedName("recipient_user_id")
    private String recipient_user_id;

    @Expose
    @SerializedName("recipient_name")
    private String recipient_name;

    @Expose
    @SerializedName("recipient_bank")
    private String recipient_bank;

    @Expose
    @SerializedName("recipient_bankaccount_name")
    private String recipient_bankaccount_name;

    @Expose
    @SerializedName("recipient_bankaccount_number")
    private String recipient_bankaccount_number;

    @Expose
    @SerializedName("recipient_contact_no")
    private String recipient_contact_no;

    @Expose
    @SerializedName("recipient_email")
    private String recipient_email;

    @Expose
    @SerializedName("recipient_address")
    private String recipient_address;

    @Expose
    @SerializedName("recipient_image")
    private String recipient_image;

    @Expose
    @SerializedName("recipient_status")
    private String recipient_status;

    public ModelTransaction() {
    }

    public ModelTransaction(String transfer_id, String transfer_userid, String transfer_base_amount, String transfer_base_currency, String transfer_amount, String transfer_fee, String transfer_amount_currency, String transfer_rate, String transfer_purpose, String transfer_email, String transfer_type, int transfer_status, String transfer_code, String transfer_date, String transfer_schedule, String transfer_auth, String transfer_origin, String transfer_bankname, String transfer_currency, String transfer_receiverid, String transfer_datecreated, String bpo_id, String transfer_bank_from, String transfer_step, String transfer_batch, String transfer_verify, String transfer_countryid, String transfer_stateid, String transfer_attachment, String transfer_reason, String transfer_reference, String recipient_id, String recipient_ofw_id, String recipient_user_id, String recipient_name, String recipient_bank, String recipient_bankaccount_name, String recipient_bankaccount_number, String recipient_contact_no, String recipient_email, String recipient_address, String recipient_image, String recipient_status) {
        this.transfer_id = transfer_id;
        this.transfer_userid = transfer_userid;
        this.transfer_base_amount = transfer_base_amount;
        this.transfer_base_currency = transfer_base_currency;
        this.transfer_amount = transfer_amount;
        this.transfer_fee = transfer_fee;
        this.transfer_amount_currency = transfer_amount_currency;
        this.transfer_rate = transfer_rate;
        this.transfer_purpose = transfer_purpose;
        this.transfer_email = transfer_email;
        this.transfer_type = transfer_type;
        this.transfer_status = transfer_status;
        this.transfer_code = transfer_code;
        this.transfer_date = transfer_date;
        this.transfer_schedule = transfer_schedule;
        this.transfer_auth = transfer_auth;
        this.transfer_origin = transfer_origin;
        this.transfer_bankname = transfer_bankname;
        this.transfer_currency = transfer_currency;
        this.transfer_receiverid = transfer_receiverid;
        this.transfer_datecreated = transfer_datecreated;
        this.bpo_id = bpo_id;
        this.transfer_bank_from = transfer_bank_from;
        this.transfer_step = transfer_step;
        this.transfer_batch = transfer_batch;
        this.transfer_verify = transfer_verify;
        this.transfer_countryid = transfer_countryid;
        this.transfer_stateid = transfer_stateid;
        this.transfer_attachment = transfer_attachment;
        this.transfer_reason = transfer_reason;
        this.transfer_reference = transfer_reference;
        this.recipient_id = recipient_id;
        this.recipient_ofw_id = recipient_ofw_id;
        this.recipient_user_id = recipient_user_id;
        this.recipient_name = recipient_name;
        this.recipient_bank = recipient_bank;
        this.recipient_bankaccount_name = recipient_bankaccount_name;
        this.recipient_bankaccount_number = recipient_bankaccount_number;
        this.recipient_contact_no = recipient_contact_no;
        this.recipient_email = recipient_email;
        this.recipient_address = recipient_address;
        this.recipient_image = recipient_image;
        this.recipient_status = recipient_status;
    }

    public String getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(String transfer_id) {
        this.transfer_id = transfer_id;
    }

    public String getTransfer_userid() {
        return transfer_userid;
    }

    public void setTransfer_userid(String transfer_userid) {
        this.transfer_userid = transfer_userid;
    }

    public String getTransfer_base_amount() {
        return transfer_base_amount;
    }

    public void setTransfer_base_amount(String transfer_base_amount) {
        this.transfer_base_amount = transfer_base_amount;
    }

    public String getTransfer_base_currency() {
        return transfer_base_currency;
    }

    public void setTransfer_base_currency(String transfer_base_currency) {
        this.transfer_base_currency = transfer_base_currency;
    }

    public String getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(String transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(String transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public String getTransfer_amount_currency() {
        return transfer_amount_currency;
    }

    public void setTransfer_amount_currency(String transfer_amount_currency) {
        this.transfer_amount_currency = transfer_amount_currency;
    }

    public String getTransfer_rate() {
        return transfer_rate;
    }

    public void setTransfer_rate(String transfer_rate) {
        this.transfer_rate = transfer_rate;
    }

    public String getTransfer_purpose() {
        return transfer_purpose;
    }

    public void setTransfer_purpose(String transfer_purpose) {
        this.transfer_purpose = transfer_purpose;
    }

    public String getTransfer_email() {
        return transfer_email;
    }

    public void setTransfer_email(String transfer_email) {
        this.transfer_email = transfer_email;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public int getTransfer_status() {
        return transfer_status;
    }

    public void setTransfer_status(int transfer_status) {
        this.transfer_status = transfer_status;
    }

    public String getTransfer_code() {
        return transfer_code;
    }

    public void setTransfer_code(String transfer_code) {
        this.transfer_code = transfer_code;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public String getTransfer_schedule() {
        return transfer_schedule;
    }

    public void setTransfer_schedule(String transfer_schedule) {
        this.transfer_schedule = transfer_schedule;
    }

    public String getTransfer_auth() {
        return transfer_auth;
    }

    public void setTransfer_auth(String transfer_auth) {
        this.transfer_auth = transfer_auth;
    }

    public String getTransfer_origin() {
        return transfer_origin;
    }

    public void setTransfer_origin(String transfer_origin) {
        this.transfer_origin = transfer_origin;
    }

    public String getTransfer_bankname() {
        return transfer_bankname;
    }

    public void setTransfer_bankname(String transfer_bankname) {
        this.transfer_bankname = transfer_bankname;
    }

    public String getTransfer_currency() {
        return transfer_currency;
    }

    public void setTransfer_currency(String transfer_currency) {
        this.transfer_currency = transfer_currency;
    }

    public String getTransfer_receiverid() {
        return transfer_receiverid;
    }

    public void setTransfer_receiverid(String transfer_receiverid) {
        this.transfer_receiverid = transfer_receiverid;
    }

    public String getTransfer_datecreated() {
        return transfer_datecreated;
    }

    public void setTransfer_datecreated(String transfer_datecreated) {
        this.transfer_datecreated = transfer_datecreated;
    }

    public String getBpo_id() {
        return bpo_id;
    }

    public void setBpo_id(String bpo_id) {
        this.bpo_id = bpo_id;
    }

    public String getTransfer_bank_from() {
        return transfer_bank_from;
    }

    public void setTransfer_bank_from(String transfer_bank_from) {
        this.transfer_bank_from = transfer_bank_from;
    }

    public String getTransfer_step() {
        return transfer_step;
    }

    public void setTransfer_step(String transfer_step) {
        this.transfer_step = transfer_step;
    }

    public String getTransfer_batch() {
        return transfer_batch;
    }

    public void setTransfer_batch(String transfer_batch) {
        this.transfer_batch = transfer_batch;
    }

    public String getTransfer_verify() {
        return transfer_verify;
    }

    public void setTransfer_verify(String transfer_verify) {
        this.transfer_verify = transfer_verify;
    }

    public String getTransfer_countryid() {
        return transfer_countryid;
    }

    public void setTransfer_countryid(String transfer_countryid) {
        this.transfer_countryid = transfer_countryid;
    }

    public String getTransfer_stateid() {
        return transfer_stateid;
    }

    public void setTransfer_stateid(String transfer_stateid) {
        this.transfer_stateid = transfer_stateid;
    }

    public String getTransfer_attachment() {
        return transfer_attachment;
    }

    public void setTransfer_attachment(String transfer_attachment) {
        this.transfer_attachment = transfer_attachment;
    }

    public String getTransfer_reason() {
        return transfer_reason;
    }

    public void setTransfer_reason(String transfer_reason) {
        this.transfer_reason = transfer_reason;
    }

    public String getTransfer_reference() {
        return transfer_reference;
    }

    public void setTransfer_reference(String transfer_reference) {
        this.transfer_reference = transfer_reference;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getRecipient_ofw_id() {
        return recipient_ofw_id;
    }

    public void setRecipient_ofw_id(String recipient_ofw_id) {
        this.recipient_ofw_id = recipient_ofw_id;
    }

    public String getRecipient_user_id() {
        return recipient_user_id;
    }

    public void setRecipient_user_id(String recipient_user_id) {
        this.recipient_user_id = recipient_user_id;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_bank() {
        return recipient_bank;
    }

    public void setRecipient_bank(String recipient_bank) {
        this.recipient_bank = recipient_bank;
    }

    public String getRecipient_bankaccount_name() {
        return recipient_bankaccount_name;
    }

    public void setRecipient_bankaccount_name(String recipient_bankaccount_name) {
        this.recipient_bankaccount_name = recipient_bankaccount_name;
    }

    public String getRecipient_bankaccount_number() {
        return recipient_bankaccount_number;
    }

    public void setRecipient_bankaccount_number(String recipient_bankaccount_number) {
        this.recipient_bankaccount_number = recipient_bankaccount_number;
    }

    public String getRecipient_contact_no() {
        return recipient_contact_no;
    }

    public void setRecipient_contact_no(String recipient_contact_no) {
        this.recipient_contact_no = recipient_contact_no;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public String getRecipient_address() {
        return recipient_address;
    }

    public void setRecipient_address(String recipient_address) {
        this.recipient_address = recipient_address;
    }

    public String getRecipient_image() {
        return recipient_image;
    }

    public void setRecipient_image(String recipient_image) {
        this.recipient_image = recipient_image;
    }

    public String getRecipient_status() {
        return recipient_status;
    }

    public void setRecipient_status(String recipient_status) {
        this.recipient_status = recipient_status;
    }
}
