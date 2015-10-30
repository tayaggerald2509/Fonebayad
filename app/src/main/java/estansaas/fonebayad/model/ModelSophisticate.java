package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/29/2015.
 */
public class ModelSophisticate {

    @SerializedName("sop_id")
    private String sop_id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("ofwId")
    private String ofwId;

    @SerializedName("sop_mobilecon")
    private String sop_mobilecon;

    @SerializedName("sop_homecon")
    private String sop_homecon;

    @SerializedName("sop_officecon")
    private String sop_officecon;

    @SerializedName("sop_flatno")
    private String sop_flatno;

    @SerializedName("sop_streetname")
    private String sop_streetname;

    @SerializedName("sop_streettype")
    private String sop_streettype;

    @SerializedName("sop_streetno")
    private String sop_streetno;

    @SerializedName("sop_suburb")
    private String sop_suburb;

    @SerializedName("sop_state")
    private String sop_state;

    @SerializedName("sop_countryid")
    private String sop_countryid;

    @SerializedName("sop_postalcode")
    private String sop_postalcode;

    @SerializedName("sop_status")
    private String sop_status;

    @SerializedName("sop_statusdescription")
    private String sop_statusdescription;

    @SerializedName("sop_dateofbirth")
    private String sop_dateofbirth;

    @SerializedName("sop_datecreated")
    private String sop_datecreated;

    @SerializedName("sop_datemodified")
    private String sop_datemodified;

    @SerializedName("greenid_status")
    private String greenid_status;

    @SerializedName("greenid_ruleid")
    private String greenid_ruleid;

    @SerializedName("greenid_transid")
    private String greenid_transid;

    @SerializedName("greenid_userid")
    private String greenid_userid;

    @SerializedName("first_kyc_id")
    private String first_kyc_id;

    @SerializedName("status_first_kyc")
    private String status_first_kyc;

    @SerializedName("second_kyc_id")
    private String second_kyc_id;

    @SerializedName("status_second_kyc")
    private String status_second_kyc;

    @SerializedName("third_kyc_id")
    private String third_kyc_id;

    @SerializedName("status_third_kyc")
    private String status_third_kyc;

    public ModelSophisticate() {

    }

    public ModelSophisticate(String sop_id, String userId, String ofwId, String sop_mobilecon, String sop_homecon, String sop_officecon, String sop_flatno, String sop_streetname, String sop_streettype, String sop_streetno, String sop_suburb, String sop_state, String sop_countryid, String sop_postalcode, String sop_status, String sop_statusdescription, String sop_dateofbirth, String sop_datecreated, String sop_datemodified, String greenid_status, String greenid_ruleid, String greenid_transid, String greenid_userid, String first_kyc_id, String status_first_kyc, String second_kyc_id, String status_second_kyc, String third_kyc_id, String status_third_kyc) {
        this.sop_id = sop_id;
        this.userId = userId;
        this.ofwId = ofwId;
        this.sop_mobilecon = sop_mobilecon;
        this.sop_homecon = sop_homecon;
        this.sop_officecon = sop_officecon;
        this.sop_flatno = sop_flatno;
        this.sop_streetname = sop_streetname;
        this.sop_streettype = sop_streettype;
        this.sop_streetno = sop_streetno;
        this.sop_suburb = sop_suburb;
        this.sop_state = sop_state;
        this.sop_countryid = sop_countryid;
        this.sop_postalcode = sop_postalcode;
        this.sop_status = sop_status;
        this.sop_statusdescription = sop_statusdescription;
        this.sop_dateofbirth = sop_dateofbirth;
        this.sop_datecreated = sop_datecreated;
        this.sop_datemodified = sop_datemodified;
        this.greenid_status = greenid_status;
        this.greenid_ruleid = greenid_ruleid;
        this.greenid_transid = greenid_transid;
        this.greenid_userid = greenid_userid;
        this.first_kyc_id = first_kyc_id;
        this.status_first_kyc = status_first_kyc;
        this.second_kyc_id = second_kyc_id;
        this.status_second_kyc = status_second_kyc;
        this.third_kyc_id = third_kyc_id;
        this.status_third_kyc = status_third_kyc;
    }

    public String getSop_id() {
        return sop_id;
    }

    public void setSop_id(String sop_id) {
        this.sop_id = sop_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfwId() {
        return ofwId;
    }

    public void setOfwId(String ofwId) {
        this.ofwId = ofwId;
    }

    public String getSop_mobilecon() {
        return sop_mobilecon;
    }

    public void setSop_mobilecon(String sop_mobilecon) {
        this.sop_mobilecon = sop_mobilecon;
    }

    public String getSop_homecon() {
        return sop_homecon;
    }

    public void setSop_homecon(String sop_homecon) {
        this.sop_homecon = sop_homecon;
    }

    public String getSop_officecon() {
        return sop_officecon;
    }

    public void setSop_officecon(String sop_officecon) {
        this.sop_officecon = sop_officecon;
    }

    public String getSop_flatno() {
        return sop_flatno;
    }

    public void setSop_flatno(String sop_flatno) {
        this.sop_flatno = sop_flatno;
    }

    public String getSop_streetname() {
        return sop_streetname;
    }

    public void setSop_streetname(String sop_streetname) {
        this.sop_streetname = sop_streetname;
    }

    public String getSop_streettype() {
        return sop_streettype;
    }

    public void setSop_streettype(String sop_streettype) {
        this.sop_streettype = sop_streettype;
    }

    public String getSop_streetno() {
        return sop_streetno;
    }

    public void setSop_streetno(String sop_streetno) {
        this.sop_streetno = sop_streetno;
    }

    public String getSop_suburb() {
        return sop_suburb;
    }

    public void setSop_suburb(String sop_suburb) {
        this.sop_suburb = sop_suburb;
    }

    public String getSop_state() {
        return sop_state;
    }

    public void setSop_state(String sop_state) {
        this.sop_state = sop_state;
    }

    public String getSop_countryid() {
        return sop_countryid;
    }

    public void setSop_countryid(String sop_countryid) {
        this.sop_countryid = sop_countryid;
    }

    public String getSop_postalcode() {
        return sop_postalcode;
    }

    public void setSop_postalcode(String sop_postalcode) {
        this.sop_postalcode = sop_postalcode;
    }

    public String getSop_status() {
        return sop_status;
    }

    public void setSop_status(String sop_status) {
        this.sop_status = sop_status;
    }

    public String getSop_statusdescription() {
        return sop_statusdescription;
    }

    public void setSop_statusdescription(String sop_statusdescription) {
        this.sop_statusdescription = sop_statusdescription;
    }

    public String getSop_dateofbirth() {
        return sop_dateofbirth;
    }

    public void setSop_dateofbirth(String sop_dateofbirth) {
        this.sop_dateofbirth = sop_dateofbirth;
    }

    public String getSop_datecreated() {
        return sop_datecreated;
    }

    public void setSop_datecreated(String sop_datecreated) {
        this.sop_datecreated = sop_datecreated;
    }

    public String getSop_datemodified() {
        return sop_datemodified;
    }

    public void setSop_datemodified(String sop_datemodified) {
        this.sop_datemodified = sop_datemodified;
    }

    public String getGreenid_status() {
        return greenid_status;
    }

    public void setGreenid_status(String greenid_status) {
        this.greenid_status = greenid_status;
    }

    public String getGreenid_ruleid() {
        return greenid_ruleid;
    }

    public void setGreenid_ruleid(String greenid_ruleid) {
        this.greenid_ruleid = greenid_ruleid;
    }

    public String getGreenid_transid() {
        return greenid_transid;
    }

    public void setGreenid_transid(String greenid_transid) {
        this.greenid_transid = greenid_transid;
    }

    public String getGreenid_userid() {
        return greenid_userid;
    }

    public void setGreenid_userid(String greenid_userid) {
        this.greenid_userid = greenid_userid;
    }

    public String getFirst_kyc_id() {
        return first_kyc_id;
    }

    public void setFirst_kyc_id(String first_kyc_id) {
        this.first_kyc_id = first_kyc_id;
    }

    public String getStatus_first_kyc() {
        return status_first_kyc;
    }

    public void setStatus_first_kyc(String status_first_kyc) {
        this.status_first_kyc = status_first_kyc;
    }

    public String getSecond_kyc_id() {
        return second_kyc_id;
    }

    public void setSecond_kyc_id(String second_kyc_id) {
        this.second_kyc_id = second_kyc_id;
    }

    public String getStatus_second_kyc() {
        return status_second_kyc;
    }

    public void setStatus_second_kyc(String status_second_kyc) {
        this.status_second_kyc = status_second_kyc;
    }

    public String getThird_kyc_id() {
        return third_kyc_id;
    }

    public void setThird_kyc_id(String third_kyc_id) {
        this.third_kyc_id = third_kyc_id;
    }

    public String getStatus_third_kyc() {
        return status_third_kyc;
    }

    public void setStatus_third_kyc(String status_third_kyc) {
        this.status_third_kyc = status_third_kyc;
    }
}
