package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/6/2015.
 */

public class ModelRegistration {

  //  @Column(name = "fonebayad_user_email")
    @SerializedName("user_email")
    public String user_email;

    @SerializedName("user_pin")
    public String user_pin;

    @SerializedName("user_salutation")
    public String user_salutation;

   // @Column(name = "fonebayad_user_fname")
    @SerializedName("user_fname")
    public String user_fname;

    @SerializedName("user_mname")
    public String user_mname;

    //@Column(name = "fonebayad_user_lname")
    @SerializedName("user_lname")
    public String user_lname;

    @SerializedName("user_guid")
    public String user_guid;

    @SerializedName("device_type")
    public String device_type;

    @SerializedName("device_name")
    public String device_name;

    @SerializedName("user_username")
    public String user_username;

    @SerializedName("user_country")
    public int user_country = 1;

    @SerializedName("user_state")
    public int user_state = 1;

    public ModelRegistration(){

    }

    public ModelRegistration(String user_email, String user_pin, String user_salutation, String user_fname, String user_mname, String user_lname, String user_guid, String device_type, String device_name, String user_username, int user_country, int user_state) {
        this.user_email = user_email;
        this.user_pin = user_pin;
        this.user_salutation = user_salutation;
        this.user_fname = user_fname;
        this.user_mname = user_mname;
        this.user_lname = user_lname;
        this.user_guid = user_guid;
        this.device_type = device_type;
        this.device_name = device_name;
        this.user_username = user_username;
        this.user_country = user_country;
        this.user_state = user_state;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_mname() {
        return user_mname;
    }

    public void setUser_mname(String user_mname) {
        this.user_mname = user_mname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getUser_guid() {
        return user_guid;
    }

    public void setUser_guid(String user_guid) {
        this.user_guid = user_guid;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public int getUser_country() {
        return user_country;
    }

    public void setUser_country(int user_country) {
        this.user_country = user_country;
    }

    public int getUser_state() {
        return user_state;
    }

    public void setUser_state(int user_state) {
        this.user_state = user_state;
    }

    public String getUser_pin() {
        return user_pin;
    }

    public void setUser_pin(String user_pin) {
        this.user_pin = user_pin;
    }

    public String getUser_salutation() {
        return user_salutation;
    }

    public void setUser_salutation(String user_salutation) {
        this.user_salutation = user_salutation;
    }

    /*public static ModelRegistration getFonebayadUserInfo(){
        ModelRegistration registrationModel = new Select().from(ModelRegistration.class).where()
    }
    */

}
