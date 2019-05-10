package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 4/13/2017.
 */

public class LoginOutput extends BaseOutput {
        @SerializedName("id")
        public String userId;
        @SerializedName("token")
        public String token;
    }

