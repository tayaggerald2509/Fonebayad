package estansaas.fonebayad.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/14/2015.
 */
@Table(name = "Users")
public class ModelLogin extends Model {

    @Column(name = "userId")
    private String userId;

    @Column(name = "app_id")
    @SerializedName("id")
    private String app_id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("secret")
    private String secret;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    public ModelLogin() {
        super();
    }

    public ModelLogin(String userId, String app_id, String username, String password, String secret, String firstname, String lastname) {
        super();
        this.userId = userId;
        this.app_id = app_id;
        this.username = username;
        this.password = password;
        this.secret = secret;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public static ModelLogin getUserInfo() {
        return new Select().from(ModelLogin.class).limit(1).executeSingle();
    }

    public static int countUser() {
        return new Select().from(ModelLogin.class).count();
    }
}

