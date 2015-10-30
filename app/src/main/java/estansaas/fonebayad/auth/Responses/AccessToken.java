package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/14/2015.
 */
public class AccessToken extends ResponseBase {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if ( ! Character.isUpperCase(tokenType.charAt(0))) {
            tokenType = Character.toString(tokenType.charAt(0)).toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }

}
