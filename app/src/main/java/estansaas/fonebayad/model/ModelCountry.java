package estansaas.fonebayad.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
@Table(name = "Country")
public class ModelCountry extends Model {

    @Column(name = "country_id")
    @SerializedName("country_id")
    private String country_id;

    @Column(name = "country_name")
    @SerializedName("country_name")
    private String country_name;

    @Column(name = "country_code")
    @SerializedName("country_code")
    private String country_code;

    public ModelCountry() {
        super();
    }

    public ModelCountry(String country_id, String country_name, String country_code) {
        super();
        this.country_id = country_id;
        this.country_name = country_name;
        this.country_code = country_code;
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

    public static int count(){
        return new Select().from(ModelCountry.class).count();
    }
}
