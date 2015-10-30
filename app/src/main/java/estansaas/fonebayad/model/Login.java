package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/23/2015.
 */
public class Login {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("secret")
    private String secret;

    public Login() {
    }

    public Login(String id, String username, String password, String secret) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
