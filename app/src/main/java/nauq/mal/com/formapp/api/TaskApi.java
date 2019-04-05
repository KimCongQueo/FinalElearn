package nauq.mal.com.formapp.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import nauq.mal.com.formapp.api.exception.ApiException;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.models.StringOutput;
import nauq.mal.com.formapp.api.models.ValidateCodeOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;
import nauq.mal.com.formapp.http.HttpApiWithSessionAuth;

/**
 * Created by dcmen on 08/31/16.
 */
public class TaskApi {
    // URL
//    public static final String TASK_WS = "http://115.79.35.119:9004/api/hbc/";//DEV api
    public static final String TASK_WS = "http://5d1c40fd.ngrok.io/elearn/";//DEV api
    public static final String LOGIN_API = "login";
    public static final String UPLOAD_AVATAR_API = "user/me/updateavatar";
    public static final String LOGOUT_API = "logout";
    public static final String CHANGE_PASSWORD_API = "user/password/change";
    public static final String FORGOT_PASSWORD_API = "password/forgot";
    public static final String FORGOT_ENTER_CODE_API = "password/validateactivecode";
    public static final String RESET_PASSWORD_API = "password/reset";
    public static final String GET_PRACTICE = "do_practice.php";
    public static final String GET_NEWFEED = "getNewfeed.php";


    private static final Logger LOG = Logger
            .getLogger(TaskApi.class.getCanonicalName());
    private HttpApiWithSessionAuth mHttpApi;
    private String mDomain;
    private Context mContext;
    private Gson mGson;

    public TaskApi(Context context) {
        mContext = context;
        mHttpApi = new HttpApiWithSessionAuth(context);
        mGson = new Gson();
        mDomain = TASK_WS;
    }


    public TaskApi setCredentials(String token) {
        if (token == null || token.length() == 0)
            mHttpApi.clearCredentials();
        else
            mHttpApi.setCredentials(token);
        return this;
    }


    public String getFullUrl(String subUrl) {
        return mDomain + subUrl;
    }

    public LoginOutput loginByEmail(LoginInput input) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(LOGIN_API), new Gson().toJson(input));
        LoginOutput output = mGson.fromJson(data.toString(), LoginOutput.class);
        return output;
    }

    public BaseOutput logout() throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(LOGOUT_API), "");
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput changePassword(String currentPassword, String newPassword) throws ApiException, JSONException, IOException {
        JSONObject input = new JSONObject();
        input.put("currentPassword", currentPassword);
        input.put("newPassword", newPassword);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(CHANGE_PASSWORD_API), input.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }
    public BaseOutput createNewPassword(String email, String token, String password) throws ApiException, JSONException, IOException {
        JSONObject input = new JSONObject();
        input.put("email", email);
        input.put("token", token);
        input.put("password", password);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(RESET_PASSWORD_API), input.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }
    public ValidateCodeOutput enterCodeForgotPassword(String email, String code) throws ApiException, JSONException, IOException {
        JSONObject input = new JSONObject();
        input.put("email", email);
        input.put("activeCode", code);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(FORGOT_ENTER_CODE_API), input.toString());
        ValidateCodeOutput output = mGson.fromJson(data.toString(), ValidateCodeOutput.class);
        return output;
    }
    public BaseOutput getCodeByEmailInForgotPassword(String email) throws ApiException, JSONException, IOException {
        JSONObject input = new JSONObject();
        input.put("email", email);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(FORGOT_PASSWORD_API), input.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }
    public StringOutput updateAvatar(ArrayList<File> files) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpMultipartImages(getFullUrl(UPLOAD_AVATAR_API), new HashMap<String, String>(), files);
        StringOutput output = mGson.fromJson(data.toString(), StringOutput.class);
        return output;
    }

    public GetPracticeOutput getPractice(String data)  throws ApiException, JSONException, IOException{
//        String tmp = mHttpApi.doHttpGetString(getFullUrl(GET_PRACTICE));
//        JSONObject data = new JSONObject();
        GetPracticeOutput output = mGson.fromJson(data, GetPracticeOutput.class);
        return output;
    }

    public GetNewfeedOutput getNewfeed(String json) {
        GetNewfeedOutput output = mGson.fromJson(json, GetNewfeedOutput.class);
        return output;
    }
}
