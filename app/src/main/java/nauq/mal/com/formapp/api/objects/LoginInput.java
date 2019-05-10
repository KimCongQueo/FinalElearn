package nauq.mal.com.formapp.api.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dcmen on 9/30/2016.
 */
public class LoginInput {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;

    public LoginInput(String username, String password){
        this.username = username;
        this.password = password;
    }
}
