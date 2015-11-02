package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/30/2015.
 */
public class ModelOffer {

    @SerializedName("offer_id")
    public String offer_id;

    @SerializedName("offer_biller")
    public String offer_biller;

    @SerializedName("offer_description")
    public String offer_description;

    @SerializedName("offer_numpoints")
    public String offer_numpoints;

    @SerializedName("offer_status")
    public String offer_status;

    @SerializedName("offer_createdby")
    public String offer_createdby;

    @SerializedName("offer_datecreated")
    public String offer_datecreated;

    @SerializedName("offer_datemodified")
    public String offer_datemodified;

    @SerializedName("url")
    public String url;

    @SerializedName("offer_dateexpired")
    public String offer_dateexpired;

    @SerializedName("instruction")
    public String instruction;

    @SerializedName("biller_id")
    public String biller_id;

    @SerializedName("biller_name")
    public String biller_name;

    @SerializedName("biller_logo")
    public String biller_logo;

    @SerializedName("biller_category")
    public String biller_category;

    @SerializedName("biller_createdby")
    public String biller_createdby;

    @SerializedName("biller_datecreated")
    public String biller_datecreated;

    @SerializedName("biller_status")
    public String biller_status;

    @SerializedName("biller_account")
    public String biller_account;

    public ModelOffer() {
    }

    public ModelOffer(String offer_id, String offer_biller, String offer_description, String offer_numpoints, String offer_status, String offer_createdby, String offer_datecreated, String offer_datemodified, String url, String offer_dateexpired, String instruction, String biller_id, String biller_name, String biller_logo, String biller_category, String biller_createdby, String biller_datecreated, String biller_status, String biller_account) {
        this.offer_id = offer_id;
        this.offer_biller = offer_biller;
        this.offer_description = offer_description;
        this.offer_numpoints = offer_numpoints;
        this.offer_status = offer_status;
        this.offer_createdby = offer_createdby;
        this.offer_datecreated = offer_datecreated;
        this.offer_datemodified = offer_datemodified;
        this.url = url;
        this.offer_dateexpired = offer_dateexpired;
        this.instruction = instruction;
        this.biller_id = biller_id;
        this.biller_name = biller_name;
        this.biller_logo = biller_logo;
        this.biller_category = biller_category;
        this.biller_createdby = biller_createdby;
        this.biller_datecreated = biller_datecreated;
        this.biller_status = biller_status;
        this.biller_account = biller_account;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
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

    public String getOffer_biller() {
        return offer_biller;
    }

    public void setOffer_biller(String offer_biller) {
        this.offer_biller = offer_biller;
    }

    public String getOffer_description() {
        return offer_description;
    }

    public void setOffer_description(String offer_description) {
        this.offer_description = offer_description;
    }

    public String getOffer_numpoints() {
        return offer_numpoints;
    }

    public void setOffer_numpoints(String offer_numpoints) {
        this.offer_numpoints = offer_numpoints;
    }

    public String getOffer_status() {
        return offer_status;
    }

    public void setOffer_status(String offer_status) {
        this.offer_status = offer_status;
    }

    public String getOffer_createdby() {
        return offer_createdby;
    }

    public void setOffer_createdby(String offer_createdby) {
        this.offer_createdby = offer_createdby;
    }

    public String getOffer_datecreated() {
        return offer_datecreated;
    }

    public void setOffer_datecreated(String offer_datecreated) {
        this.offer_datecreated = offer_datecreated;
    }

    public String getOffer_datemodified() {
        return offer_datemodified;
    }

    public void setOffer_datemodified(String offer_datemodified) {
        this.offer_datemodified = offer_datemodified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOffer_dateexpired() {
        return offer_dateexpired;
    }

    public void setOffer_dateexpired(String offer_dateexpired) {
        this.offer_dateexpired = offer_dateexpired;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public void setBiller_id(String biller_id) {
        this.biller_id = biller_id;
    }
}
