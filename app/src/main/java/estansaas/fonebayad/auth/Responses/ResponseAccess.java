package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 11/9/2015.
 */
public class ResponseAccess {

    @Expose
    @SerializedName("access_token")
    private String access_token;

    @Expose
    @SerializedName("token_type")
    private String token_type;

    @Expose
    @SerializedName("expires_in")
    private String expires_in;

    public ResponseAccess(String access_token, String token_type, String expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
