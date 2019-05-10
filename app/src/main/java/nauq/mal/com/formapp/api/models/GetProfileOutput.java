package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import nauq.mal.com.formapp.models.User;

public class GetProfileOutput extends BaseOutput {

    @SerializedName("profile")
    public User user;
}
