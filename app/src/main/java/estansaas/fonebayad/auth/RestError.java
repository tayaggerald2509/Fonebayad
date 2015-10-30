package estansaas.fonebayad.auth;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 10/5/2015.
 */


public class RestError {

    @SerializedName("type")
    public String type;

    @SerializedName("message")
    public String result;

    @SerializedName("line")
    public String line;

    public RestError() {
    }

    public RestError(String message) {
        this.result = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
