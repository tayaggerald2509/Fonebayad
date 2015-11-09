package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerald.tayag on 10/14/2015.
 */
public class ResponseUser extends ResponseBase {

    @Expose
    @SerializedName("data")
    private UserModel data;

    @Expose
    @SerializedName("status")
    private String status;

    public ResponseUser() {
    }

    public ResponseUser(UserModel data, String status) {
        this.data = data;
        this.status = status;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class UserModel {

        @Expose
        @SerializedName("user")
        private List<User> user = new ArrayList<>();

        public UserModel(List<User> user) {
            this.user = user;
        }

        public List<User> getUser() {
            return user;
        }

        public void setUser(List<User> user) {
            this.user = user;
        }

        public class User {

            @Expose
            @SerializedName("id")
            private String id;

            @Expose
            @SerializedName("user_fname")
            private String user_fname;

            @Expose
            @SerializedName("user_mname")
            private String user_mname;

            @Expose
            @SerializedName("user_lname")
            private String user_lname;

            @Expose
            @SerializedName("user_email")
            private String user_email;

            @SerializedName("user_birthdate")
            private String user_birthdate;

            @Expose
            @SerializedName("user_salutation")
            private String user_salutation;

            @Expose
            @SerializedName("user_type")
            private String user_type;

            @Expose
            @SerializedName("user_status")
            private String user_status;

            @Expose
            @SerializedName("user_countryId")
            private String user_countryId;

            @Expose
            @SerializedName("user_stateId")
            private String user_stateId;

            @Expose
            @SerializedName("user_profilepicture")
            private String user_profilepicture;


            public User() {
            }

            public User(String id, String user_fname, String user_mname, String user_lname, String user_email, String user_birthdate, String user_salutation, String user_type, String user_status, String user_profilepicture, String user_countryId, String user_stateId) {
                this.id = id;
                this.user_fname = user_fname;
                this.user_mname = user_mname;
                this.user_lname = user_lname;
                this.user_email = user_email;
                this.user_birthdate = user_birthdate;
                this.user_salutation = user_salutation;
                this.user_type = user_type;
                this.user_status = user_status;
                this.user_profilepicture = user_profilepicture;
                this.user_countryId = user_countryId;
                this.user_stateId = user_stateId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getUser_email() {
                return user_email;
            }

            public void setUser_email(String user_email) {
                this.user_email = user_email;
            }

            public String getUser_birthdate() {
                return user_birthdate;
            }

            public void setUser_birthdate(String user_birthdate) {
                this.user_birthdate = user_birthdate;
            }

            public String getUser_salutation() {
                return user_salutation;
            }

            public void setUser_salutation(String user_salutation) {
                this.user_salutation = user_salutation;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

            public String getUser_profilepicture() {
                return user_profilepicture;
            }

            public void setUser_profilepicture(String user_profilepicture) {
                this.user_profilepicture = user_profilepicture;
            }

            public String getUser_countryId() {
                return user_countryId;
            }

            public void setUser_countryId(String user_countryId) {
                this.user_countryId = user_countryId;
            }

            public String getUser_stateId() {
                return user_stateId;
            }

            public void setUser_stateId(String user_stateId) {
                this.user_stateId = user_stateId;
            }
        }

        public class Session {

            @SerializedName("setting_id")
            private String setting_id;

            @SerializedName("setting_fieldcode")
            private String setting_fieldcode;

            @SerializedName("setting_fieldname")
            private String setting_fieldname;

            @SerializedName("setting_fieldvalue")
            private String setting_fieldvalue;

            @SerializedName("setting_status")
            private String setting_status;

            @SerializedName("setting_datecreated")
            private String setting_datecreated;

        }
    }

}
