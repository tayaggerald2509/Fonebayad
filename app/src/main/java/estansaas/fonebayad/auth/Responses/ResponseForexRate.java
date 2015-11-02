package estansaas.fonebayad.auth.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gerald.tayag on 11/2/2015.
 */
public class ResponseForexRate {

    @SerializedName("success")
    private String success;

    @SerializedName("source")
    private String source;

    @SerializedName("target")
    private String target;

    @SerializedName("rate")
    private String rate;


    public ResponseForexRate(String success, String source, String target, String rate) {
        this.success = success;
        this.source = source;
        this.target = target;
        this.rate = rate;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
