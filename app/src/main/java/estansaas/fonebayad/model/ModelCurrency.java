package estansaas.fonebayad.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
@Table(name = "Currency")
public class ModelCurrency extends Model {

    @Column(name = "currency_id")
    @SerializedName("currency_id")
    private String currency_id;

    @Column(name = "currency_code")
    @SerializedName("currency_code")
    private String currency_code;

    @Column(name = "currency_default")
    @SerializedName("currency_default")
    private String currency_default;

    @Column(name = "currency_name")
    @SerializedName("currency_name")
    private String currency_name;

    @Column(name = "currency_logo")
    @SerializedName("currency_logo")
    private String currency_logo;

    @Column(name = "currency_countryid")
    @SerializedName("currency_countryid")
    private String currency_countryid;

    @Column(name = "currency_status")
    @SerializedName("currency_status")
    private String currency_status;

    public ModelCurrency() {
        super();
    }

    public ModelCurrency(String currency_id, String currency_code, String currency_default, String currency_name, String currency_logo, String currency_countryid, String currency_status) {
        super();
        this.currency_id = currency_id;
        this.currency_code = currency_code;
        this.currency_default = currency_default;
        this.currency_name = currency_name;
        this.currency_logo = currency_logo;
        this.currency_countryid = currency_countryid;
        this.currency_status = currency_status;
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

    public static String getCurrency(String currency_id){
        ModelCurrency modelCurrency = new Select().from(ModelCurrency.class).where("currency_id=?", currency_id).executeSingle();
        return modelCurrency.getCurrency_code();
    }

    public static int count(){
        return new Select().from(ModelCurrency.class).count();
    }
}
