package estansaas.fonebayad.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import estansaas.fonebayad.utils.Listable;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
@Table(name = "billers")
public class ModelBillers extends Model implements Listable {

    @Column(name = "biller_id")
    @SerializedName("biller_id")
    private String biller_id;

    @Column(name = "biller_name")
    @SerializedName("biller_name")
    private String biller_name;

    @Column(name = "biller_logo")
    @SerializedName("biller_logo")
    private String biller_logo;

    @Column(name = "biller_category")
    @SerializedName("biller_category")
    private String biller_category;

    @SerializedName("biller_createdby")
    private String biller_createdby;

    @SerializedName("biller_datecreated")
    private String biller_datecreated;

    @Column(name = "biller_status")
    @SerializedName("biller_status")
    private String biller_status;

    @Column(name = "biller_account")
    @SerializedName("biller_account")
    private String biller_account;

    public ModelBillers() {
        super();
    }

    public ModelBillers(String biller_id, String biller_name, String biller_logo, String biller_category, String biller_createdby, String biller_datecreated, String biller_status, String biller_account) {
        super();
        this.biller_id = biller_id;
        this.biller_name = biller_name;
        this.biller_logo = biller_logo;
        this.biller_category = biller_category;
        this.biller_createdby = biller_createdby;
        this.biller_datecreated = biller_datecreated;
        this.biller_status = biller_status;
        this.biller_account = biller_account;
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


    public static ModelBillers getBillers(String biller_id) {
        return new Select().from(ModelBillers.class).where("biller_id=?", biller_id).executeSingle();
    }

    public static List<ModelBillers> getBillersByCategory(String category_id) {
        return new Select().from(ModelBillers.class).where("biller_category=?", category_id).execute();
    }

    public static int count() {
        return new Select().from(ModelBillers.class).count();
    }

    @Override
    public String getLabel() {
        return getBiller_name();
    }
}
