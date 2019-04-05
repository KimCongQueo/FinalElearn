package nauq.mal.com.formapp.api.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dcmen on 9/30/2016.
 */
public class LoginInput {
    @SerializedName("userName")
    public String userName;
    @SerializedName("password")
    public String password;

    public LoginInput(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
