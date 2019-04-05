package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dcmen on 8/31/2016.
 */
public class BaseOutput {
    @SerializedName("success")
    public boolean success;
    @SerializedName("errorCode")
    public String ErrorCode;
    @SerializedName("errorMessage")
    public String errorMessage;
}
