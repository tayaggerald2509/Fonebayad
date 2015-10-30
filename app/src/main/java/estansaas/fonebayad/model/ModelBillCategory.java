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
@Table(name = "BillCategory")
public class ModelBillCategory extends Model implements Listable {

    @Column(name = "category_id")
    @SerializedName("category_id")
    private String category_id;

    @Column(name = "category_name")
    @SerializedName("category_name")
    private String category_name;

    @Column(name = "category_description")
    @SerializedName("category_description")
    private String category_description;

    @Column(name = "category_logo")
    @SerializedName("category_logo")
    private String category_logo;

    @SerializedName("category_createdby")
    private String category_createdby;

    @SerializedName("category_datecreated")
    private String category_datecreated;

    @Column(name = "category_status")
    @SerializedName("category_status")
    private String category_status;


    public ModelBillCategory() {
        super();
    }

    public ModelBillCategory(String category_id, String category_name, String category_description, String category_logo, String category_createdby, String category_datecreated, String category_status) {
        super();
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_description = category_description;
        this.category_logo = category_logo;
        this.category_createdby = category_createdby;
        this.category_datecreated = category_datecreated;
        this.category_status = category_status;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getCategory_logo() {
        return category_logo;
    }

    public void setCategory_logo(String category_logo) {
        this.category_logo = category_logo;
    }

    public String getCategory_createdby() {
        return category_createdby;
    }

    public void setCategory_createdby(String category_createdby) {
        this.category_createdby = category_createdby;
    }

    public String getCategory_datecreated() {
        return category_datecreated;
    }

    public void setCategory_datecreated(String category_datecreated) {
        this.category_datecreated = category_datecreated;
    }

    public String getCategory_status() {
        return category_status;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }

    public static List<ModelBillCategory> getCategory() {
        return new Select().from(ModelBillCategory.class).execute();
    }

    public static String getCategoryName(int category_id) {
        ModelBillCategory modelBillCategory = new Select().from(ModelBillCategory.class).where("category_id=?", String.valueOf(category_id)).executeSingle();
        return modelBillCategory == null ? "" : modelBillCategory.getCategory_name();
    }

    public static int count() {
        return new Select().from(ModelBillCategory.class).count();
    }

    @Override
    public String getLabel() {
        return getCategory_name();
    }
}
