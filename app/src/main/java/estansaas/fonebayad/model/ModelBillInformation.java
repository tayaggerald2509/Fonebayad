package estansaas.fonebayad.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by gerald.tayag on 10/22/2015.
 */
@Table(name = "billstatement")
public class ModelBillInformation extends Model implements Serializable {

    @Column(name = "bill_Id")
    public String bill_Id;

    @Expose
    @SerializedName("bill_biller")
    @Column(name = "bill_biller")
    private String bill_biller;

    @Expose
    @SerializedName("bill_account_number")
    @Column(name = "bill_account_number")
    private String bill_account_number;

    @Expose
    @SerializedName("bill_transaction_number")
    @Column(name = "bill_transaction_number")
    private String bill_transaction_number = "";

    @Expose
    @SerializedName("bill_currency")
    @Column(name = "bill_currency")
    private String bill_currency;

    @Column(name = "bill_balance")
    public String balance;

    @Expose
    @SerializedName("bill_amount")
    @Column(name = "bill_amount")
    private String bill_amount;

    @Expose
    @SerializedName("bill_status")
    @Column(name = "bill_status")
    private String bill_status;

    @Expose
    @SerializedName("bill_attachment")
    @Column(name = "bill_attachment")
    private String bill_attachment = "";

    @Column(name = "bill_due_date")
    private Date due_date;

    @Expose
    @SerializedName("bill_due_date")
    private String bill_due_date;

    @Expose
    @SerializedName("bill_schedule_of_payment")
    @Column(name = "bill_schedule_of_payment")
    private String bill_schedule_of_payment = "";

    @Column(name = "bill_share")
    public int bill_share;

    @Expose
    @SerializedName("bill_user_id")
    private String bill_user_id;

    @Expose
    @SerializedName("bill_payment_method")
    @Column(name = "bill_payment_method")
    private String bill_payment_method;

    @Column(name = "biller_name")
    public String biller_name;

    @Expose
    @SerializedName("bill_type")
    private String bill_type = "Now";

    @Expose
    @SerializedName("bill_user_entity")
    private String bill_user_entity = "Personal";

    @Column(name = "biller_category")
    public int biller_category;

    @Column(name = "bill_month")
    public String month;

    @Column(name = "bill_year")
    public String year;

    private String file_path;

    @Column(name = "isSelected")
    public Boolean isSelected = false;

    public String NoOfDays;
    public String type;

    public ModelBillInformation() {
        super();
    }

    public ModelBillInformation(String bill_Id, String bill_biller, String bill_account_number, String bill_transaction_number, String bill_currency, String balance, String bill_amount, String bill_status, String bill_attachment, Date due_date, String bill_due_date, String bill_schedule_of_payment, int bill_share, String bill_user_id, String bill_payment_method, String biller_name, String bill_type, String bill_user_entity, int biller_category, String month, String year, String file_path, Boolean isSelected, String noOfDays, String type) {
        this.bill_Id = bill_Id;
        this.bill_biller = bill_biller;
        this.bill_account_number = bill_account_number;
        this.bill_transaction_number = bill_transaction_number;
        this.bill_currency = bill_currency;
        this.balance = balance;
        this.bill_amount = bill_amount;
        this.bill_status = bill_status;
        this.bill_attachment = bill_attachment;
        this.due_date = due_date;
        this.bill_due_date = bill_due_date;
        this.bill_schedule_of_payment = bill_schedule_of_payment;
        this.bill_share = bill_share;
        this.bill_user_id = bill_user_id;
        this.bill_payment_method = bill_payment_method;
        this.biller_name = biller_name;
        this.bill_type = bill_type;
        this.bill_user_entity = bill_user_entity;
        this.biller_category = biller_category;
        this.month = month;
        this.year = year;
        this.file_path = file_path;
        this.isSelected = isSelected;
        NoOfDays = noOfDays;
        this.type = type;
    }

    public String getBill_Id() {
        return bill_Id;
    }

    public void setBill_Id(String bill_Id) {
        this.bill_Id = bill_Id;
    }

    public String getBill_biller() {
        return bill_biller;
    }

    public void setBill_biller(String bill_biller) {
        this.bill_biller = bill_biller;
    }

    public String getBill_account_number() {
        return bill_account_number;
    }

    public void setBill_account_number(String bill_account_number) {
        this.bill_account_number = bill_account_number;
    }

    public String getBill_transaction_number() {
        return bill_transaction_number;
    }

    public void setBill_transaction_number(String bill_transaction_number) {
        this.bill_transaction_number = bill_transaction_number;
    }

    public String getBill_currency() {
        return bill_currency;
    }

    public void setBill_currency(String bill_currency) {
        this.bill_currency = bill_currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_status() {
        return bill_status;
    }

    public void setBill_status(String bill_status) {
        this.bill_status = bill_status;
    }

    public String getBill_attachment() {
        return bill_attachment;
    }

    public void setBill_attachment(String bill_attachment) {
        this.bill_attachment = bill_attachment;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getBill_due_date() {
        return bill_due_date;
    }

    public void setBill_due_date(String bill_due_date) {
        this.bill_due_date = bill_due_date;
    }

    public String getBill_schedule_of_payment() {
        return bill_schedule_of_payment;
    }

    public void setBill_schedule_of_payment(String bill_schedule_of_payment) {
        this.bill_schedule_of_payment = bill_schedule_of_payment;
    }

    public int getBill_share() {
        return bill_share;
    }

    public void setBill_share(int bill_share) {
        this.bill_share = bill_share;
    }

    public String getBill_user_id() {
        return bill_user_id;
    }

    public void setBill_user_id(String bill_user_id) {
        this.bill_user_id = bill_user_id;
    }

    public String getBill_payment_method() {
        return bill_payment_method;
    }

    public void setBill_payment_method(String bill_payment_method) {
        this.bill_payment_method = bill_payment_method;
    }

    public String getBiller_name() {
        return biller_name;
    }

    public void setBiller_name(String biller_name) {
        this.biller_name = biller_name;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getBill_user_entity() {
        return bill_user_entity;
    }

    public void setBill_user_entity(String bill_user_entity) {
        this.bill_user_entity = bill_user_entity;
    }

    public int getBiller_category() {
        return biller_category;
    }

    public void setBiller_category(int biller_category) {
        this.biller_category = biller_category;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        NoOfDays = noOfDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ModelBillInformation viewBillStatement(String bill_Id) {
        return new Select().from(ModelBillInformation.class).where("bill_Id=?", bill_Id).executeSingle();
    }

    public static List<ModelBillInformation> getBillStatement() {
        return new Select().from(ModelBillInformation.class).groupBy("bill_Id").orderBy("bill_due_date ASC").execute();
    }

    public static List<ModelBillInformation> getBillStatementByMonth(String month, String year) {
        return new Select().from(ModelBillInformation.class).where("bill_month=" + month + " AND bill_year=" + year).execute();
    }

    public static List<ModelBillInformation> getBillStatementByCategory(String biller_category) {
        return new Select().from(ModelBillInformation.class).where("biller_category=?", biller_category).execute();
    }

    public static boolean getSelected(String bill_Id) {
        ModelBillInformation billStatement = new Select().from(ModelBillInformation.class).where("bill_Id=?", bill_Id).executeSingle();
        return billStatement.getIsSelected();
    }

    public static void DeleteBillStatement() {
        new Delete().from(ModelBillInformation.class).execute();
    }

    public static int CountBillStatement() {
        return new Select().from(ModelBillInformation.class).count();
    }
}
