package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 11/6/2015.
 */
public class ModelCountryDetail {

    @SerializedName("transperabank_id")
    private String transperabank_id;

    @SerializedName("transperabank_currency")
    private String transperabank_currency;

    @SerializedName("transperabank_name")
    private String transperabank_name;

    @SerializedName("transperabank_accountnumber")
    private String transperabank_accountnumberv;

    @SerializedName("transperabank_accountname")
    private String transperabank_accountname;

    @SerializedName("transperabank_bsbnumber")
    private String transperabank_bsbnumber;

    @SerializedName("transperabank_datecreated")
    private String transperabank_datecreated;

    @SerializedName("bankstatus")
    private String bankstatus;

    @SerializedName("transperabankname")
    private String transperabankname;

    @SerializedName("transaperabanklogo")
    private String transaperabanklogo;

    @SerializedName("currency_id")
    private String currency_id;

    @SerializedName("currency_code")
    private String currency_code;

    @SerializedName("currency_default")
    private String currency_default;

    @SerializedName("currency_name")
    private String currency_name;

    @SerializedName("currency_logo")
    private String currency_logo;

    @SerializedName("currency_countryid")
    private String currency_countryid;

    @SerializedName("currency_status")
    private String currency_status;

    @SerializedName("currency_datecreated")
    private String currency_datecreated;

    @SerializedName("country_id")
    private String country_id;

    @SerializedName("country_name")
    private String country_name;

    @SerializedName("country_code")
    private String country_code;

    @SerializedName("country_datecreated")
    private String country_datecreated;

    public ModelCountryDetail() {
    }

    public ModelCountryDetail(String transperabank_id, String transperabank_currency, String transperabank_name, String transperabank_accountnumberv, String transperabank_accountname, String transperabank_bsbnumber, String transperabank_datecreated, String bankstatus, String transperabankname, String transaperabanklogo, String currency_id, String currency_code, String currency_default, String currency_name, String currency_logo, String currency_countryid, String currency_status, String currency_datecreated, String country_id, String country_name, String country_code, String country_datecreated) {
        this.transperabank_id = transperabank_id;
        this.transperabank_currency = transperabank_currency;
        this.transperabank_name = transperabank_name;
        this.transperabank_accountnumberv = transperabank_accountnumberv;
        this.transperabank_accountname = transperabank_accountname;
        this.transperabank_bsbnumber = transperabank_bsbnumber;
        this.transperabank_datecreated = transperabank_datecreated;
        this.bankstatus = bankstatus;
        this.transperabankname = transperabankname;
        this.transaperabanklogo = transaperabanklogo;
        this.currency_id = currency_id;
        this.currency_code = currency_code;
        this.currency_default = currency_default;
        this.currency_name = currency_name;
        this.currency_logo = currency_logo;
        this.currency_countryid = currency_countryid;
        this.currency_status = currency_status;
        this.currency_datecreated = currency_datecreated;
        this.country_id = country_id;
        this.country_name = country_name;
        this.country_code = country_code;
        this.country_datecreated = country_datecreated;
    }

    public String getTransperabank_id() {
        return transperabank_id;
    }

    public void setTransperabank_id(String transperabank_id) {
        this.transperabank_id = transperabank_id;
    }

    public String getTransperabank_currency() {
        return transperabank_currency;
    }

    public void setTransperabank_currency(String transperabank_currency) {
        this.transperabank_currency = transperabank_currency;
    }

    public String getTransperabank_name() {
        return transperabank_name;
    }

    public void setTransperabank_name(String transperabank_name) {
        this.transperabank_name = transperabank_name;
    }

    public String getTransperabank_accountnumberv() {
        return transperabank_accountnumberv;
    }

    public void setTransperabank_accountnumberv(String transperabank_accountnumberv) {
        this.transperabank_accountnumberv = transperabank_accountnumberv;
    }

    public String getTransperabank_accountname() {
        return transperabank_accountname;
    }

    public void setTransperabank_accountname(String transperabank_accountname) {
        this.transperabank_accountname = transperabank_accountname;
    }

    public String getTransperabank_bsbnumber() {
        return transperabank_bsbnumber;
    }

    public void setTransperabank_bsbnumber(String transperabank_bsbnumber) {
        this.transperabank_bsbnumber = transperabank_bsbnumber;
    }

    public String getTransperabank_datecreated() {
        return transperabank_datecreated;
    }

    public void setTransperabank_datecreated(String transperabank_datecreated) {
        this.transperabank_datecreated = transperabank_datecreated;
    }

    public String getBankstatus() {
        return bankstatus;
    }

    public void setBankstatus(String bankstatus) {
        this.bankstatus = bankstatus;
    }

    public String getTransperabankname() {
        return transperabankname;
    }

    public void setTransperabankname(String transperabankname) {
        this.transperabankname = transperabankname;
    }

    public String getTransaperabanklogo() {
        return transaperabanklogo;
    }

    public void setTransaperabanklogo(String transaperabanklogo) {
        this.transaperabanklogo = transaperabanklogo;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_default() {
        return currency_default;
    }

    public void setCurrency_default(String currency_default) {
        this.currency_default = currency_default;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_logo() {
        return currency_logo;
    }

    public void setCurrency_logo(String currency_logo) {
        this.currency_logo = currency_logo;
    }

    public String getCurrency_countryid() {
        return currency_countryid;
    }

    public void setCurrency_countryid(String currency_countryid) {
        this.currency_countryid = currency_countryid;
    }

    public String getCurrency_status() {
        return currency_status;
    }

    public void setCurrency_status(String currency_status) {
        this.currency_status = currency_status;
    }

    public String getCurrency_datecreated() {
        return currency_datecreated;
    }

    public void setCurrency_datecreated(String currency_datecreated) {
        this.currency_datecreated = currency_datecreated;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_datecreated() {
        return country_datecreated;
    }

    public void setCountry_datecreated(String country_datecreated) {
        this.country_datecreated = country_datecreated;
    }
}
