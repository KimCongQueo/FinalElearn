package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

public class PasswordOutput  extends BaseOutput {
    @SerializedName("id")
    public String id;
    @SerializedName("msg")
    public String msg = "";
}
