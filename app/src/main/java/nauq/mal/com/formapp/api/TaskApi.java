package nauq.mal.com.formapp.api;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
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
import nauq.mal.com.formapp.api.models.GetBookmarkOutput;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.api.models.GetCommentOutput;
import nauq.mal.com.formapp.api.models.GetGrammarOutput;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.api.models.GetPointOutput;
import nauq.mal.com.formapp.api.models.GetPostOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOnlyTopicOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOutput;
import nauq.mal.com.formapp.api.models.GetProfileOutput;
import nauq.mal.com.formapp.api.models.GetTagsOutput;
import nauq.mal.com.formapp.api.models.GetTopicOutput;
import nauq.mal.com.formapp.api.models.GetWordsOutput;
import nauq.mal.com.formapp.api.models.ImgOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.models.PasswordOutput;
import nauq.mal.com.formapp.api.models.StringOutput;
import nauq.mal.com.formapp.api.models.ValidateCodeOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;
import nauq.mal.com.formapp.api.objects.PasswordInput;
import nauq.mal.com.formapp.http.HttpApiWithSessionAuth;
import nauq.mal.com.formapp.models.Tags;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.StringUtils;

/**
 * Created by dcmen on 08/31/16.
 */
public class TaskApi {
    // URL
//    public static final String TASK_WS = "http://115.79.35.119:9004/api/hbc/";//DEV api
    public static final String TASK_WS = "https://elearn-anhhong.c9users.io/api/";//DEV api
    public static final String LOGIN_API = "login";
    public static final String PROFILE = "profile";
    public static final String UPLOAD_AVATAR_API = "upload";
    public static final String LOGOUT_API = "logout";
    public static final String CHANGE_PASSWORD_API = "reset";
    public static final String FORGOT_PASSWORD_API = "password/forgot";
    public static final String FORGOT_ENTER_CODE_API = "password/validateactivecode";
    public static final String RESET_PASSWORD_API = "password/reset";
    public static final String GET_PRACTICE = "do_practice.php";
    public static final String POST = "post";
    public static final String COMMENTS = "comment";
    public static final String LIKE = "like";
    public static final String CATEGORY = "category";
    public static final String BOOKMARK = "bookmark";
    public static final String QUIZ = "quiz";
    public static final String TAGS = "tag";
    public static final String QUIZ_ARR = "quiz?size=20";


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
        JSONObject inputt = new JSONObject();
        inputt.put("username", input.username);
        inputt.put("password", input.password);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(LOGIN_API), inputt.toString());
        LoginOutput output = mGson.fromJson(data.toString(), LoginOutput.class);
        return output;
    }

    public BaseOutput logout() throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(LOGOUT_API), "");
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public PasswordOutput changePassword(PasswordInput input) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(CHANGE_PASSWORD_API), new Gson().toJson(input));
        PasswordOutput output = mGson.fromJson(data.toString(), PasswordOutput.class);
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

    public GetPracticeOutput getPractice(String data) throws ApiException, JSONException, IOException {
//        String tmp = mHttpApi.doHttpGetString(getFullUrl(GET_PRACTICE));
//        JSONObject data = new JSONObject();
        GetPracticeOutput output = mGson.fromJson(data, GetPracticeOutput.class);
        return output;
    }


    public GetCategoriesOutput getTopic(String id) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/category/"
                + id + "/children");
        GetCategoriesOutput output = mGson.fromJson(data.toString(), GetCategoriesOutput.class);
        return output;
    }

    public GetWordsOutput getWords(String id) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/category/"
                + id + "/words");
        GetWordsOutput output = mGson.fromJson(data.toString(), GetWordsOutput.class);
        return output;
    }

    public GetTopicOutput getTopicGrammar(String jsonString) {
        GetTopicOutput output = mGson.fromJson(jsonString, GetTopicOutput.class);
        return output;
    }

    public GetProfileOutput getProfile() throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader(getFullUrl(PROFILE));
        GetProfileOutput output = mGson.fromJson(data.toString(), GetProfileOutput.class);
        return output;
    }

    public LoginOutput signup(User user) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(PROFILE), new Gson().toJson(user));
        LoginOutput output = mGson.fromJson(data.toString(), LoginOutput.class);
        return output;
    }

    public BaseOutput putProfile(User user) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpPut(getFullUrl(PROFILE), new Gson().toJson(user));
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public ImgOutput uploadAvatar(ArrayList<File> files) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpMultipartImages(getFullUrl(UPLOAD_AVATAR_API), new HashMap<String, String>(), files);
        ImgOutput output = mGson.fromJson(data.toString(), ImgOutput.class);
        return output;
    }

    public GetNewfeedOutput getNewfeed(int mStart) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGet("https://elearn-anhhong.c9users.io/api/post?page=" + mStart + "&limit=" + Constants.LIMIT_ITEMS);
        GetNewfeedOutput output = mGson.fromJson(data.toString(), GetNewfeedOutput.class);
        if (output.posts.size() < Constants.LIMIT_ITEMS) {
            output.setHasNextPage(false);
        }
        return output;
    }

    public BaseOutput addPost(String content, ArrayList<String> mData, ArrayList<Tags> mDataTag) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        JSONArray array = new JSONArray(mData);
        ArrayList<String> mTag = new ArrayList<>();
        for (int i = 0; i < mDataTag.size(); i++) {
            if(mDataTag.get(i).isChecked()){
                mTag.add(mDataTag.get(i).getId());
            }
        }
        contentTmp.put("content", content);
        contentTmp.put("imgs", array);
        contentTmp.put("tags", new JSONArray(mTag));
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(POST), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }


    public BaseOutput deletePost(String id) throws ApiException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        JSONObject data = mHttpApi.doHttpDelete(getFullUrl(POST), jsonObject.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput editPost(String id, String content, ArrayList<String> mData) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        JSONArray array = new JSONArray(mData);
        contentTmp.put("content", content);
        contentTmp.put("id", id);
        contentTmp.put("imgs", array);
        JSONObject data = mHttpApi.doHttpPut(getFullUrl(POST), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public GetCommentOutput getComments(String idPost, int mStart) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/post/"
                + idPost + "/comments" + "?page=" + mStart + "&limit=" + Constants.LIMIT_ITEMS);
        GetCommentOutput output = mGson.fromJson(data.toString(), GetCommentOutput.class);
        if (output.comments.size() < Constants.LIMIT_ITEMS) {
            output.setHasNextPage(false);
        }
        return output;
    }

    public BaseOutput addCmt(String content, String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("content", content);
        contentTmp.put("post", idPost);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(COMMENTS), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput deleteComment(String id) throws ApiException, JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        JSONObject data = mHttpApi.doHttpDelete(getFullUrl(COMMENTS), jsonObject.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput editCmt(String id, String content) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("content", content);
        contentTmp.put("id", id);
        JSONObject data = mHttpApi.doHttpPut(getFullUrl(COMMENTS), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput likePost(String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("post", idPost);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(LIKE), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput dislikePost(String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("post", idPost);
        JSONObject data = mHttpApi.doHttpDelete(getFullUrl(LIKE), contentTmp.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public GetCategoriesOutput getCategories() throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGet(getFullUrl(CATEGORY));
        GetCategoriesOutput output = mGson.fromJson(data.toString(), GetCategoriesOutput.class);
        return output;
    }

    public BaseOutput bookmark(String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("post", idPost);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(BOOKMARK), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput deleteBookmark(String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("post", idPost);
        JSONObject data = mHttpApi.doHttpDelete(getFullUrl(BOOKMARK), contentTmp.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput bookmarkWord(String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("word", idPost);
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(BOOKMARK), contentTmp);
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public BaseOutput deleteBookmarkWord(String idPost) throws ApiException, JSONException, IOException {
        JSONObject contentTmp = new JSONObject();
        contentTmp.put("word", idPost);
        JSONObject data = mHttpApi.doHttpDelete(getFullUrl(BOOKMARK), contentTmp.toString());
        BaseOutput output = mGson.fromJson(data.toString(), BaseOutput.class);
        return output;
    }

    public GetBookmarkOutput getBookmarks(int mStart) throws ApiException, JSONException, IOException {
//        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/bookmark"
//                 + "?page=" + mStart + "&limit=" + Constants.LIMIT_ITEMS);
        JSONObject data = mHttpApi.doHttpGet(getFullUrl(BOOKMARK));
        GetBookmarkOutput output = mGson.fromJson(data.toString(), GetBookmarkOutput.class);
        if (output.posts.size() < Constants.LIMIT_ITEMS) {
            output.setHasNextPage(false);
        }
        return output;
    }

    public GetPostOutput getPostFollowId(String idPost) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/post/"
                + "?id=" + idPost);
        GetPostOutput output = mGson.fromJson(data.toString(), GetPostOutput.class);
        return output;
    }

    public GetNewfeedOutput getNewfeedSearch(int mStart, String search) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/search/"
                + "?q=" + search);
        GetNewfeedOutput output = mGson.fromJson(data.toString(), GetNewfeedOutput.class);
        return output;
    }

    public GetCategoriesOutput getCategoriesChildren(String id) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/category/"
                + id + "/children");
        GetCategoriesOutput output = mGson.fromJson(data.toString(), GetCategoriesOutput.class);
        return output;
    }

    public GetPracticeOnlyTopicOutput getPracticeOnly1(String id) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/quiz?size=20&categories[0]="
                + id);
        GetPracticeOnlyTopicOutput output = mGson.fromJson(data.toString(), GetPracticeOnlyTopicOutput.class);
        return output;
    }

    public GetGrammarOutput getGrammar(String id) throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader("https://elearn-anhhong.c9users.io/api/category/"
                + id + "/grammars");
        GetGrammarOutput output = mGson.fromJson(data.toString(), GetGrammarOutput.class);
        return output;
    }

    public GetPointOutput getPoint(String matches, ArrayList<String> ans) throws ApiException, JSONException, IOException {
        JSONObject input = new JSONObject();
        input.put("matches", matches);
        input.put("answers", new JSONArray(ans));
        JSONObject data = mHttpApi.doHttpPost(getFullUrl(QUIZ), input);
        GetPointOutput output = mGson.fromJson(data.toString(), GetPointOutput.class);
        return output;
    }

    public GetPracticeOnlyTopicOutput getPracticeMulti(ArrayList<String> id) throws ApiException, JSONException, IOException {
        String[] temp = new String[id.size()];
        String api = getFullUrl(QUIZ_ARR);
        for (int i = 0; i < id.size(); i++) {
            temp[i] = id.get(i);
            api += "&categories=" + id.get(i);
        }
        JSONObject data = mHttpApi.doHttpGetWithHeader(api);
//        JSONObject data = mHttpApi.doHttpGet(String.format(getFullUrl(QUIZ_ARR), 20, temp));
        GetPracticeOnlyTopicOutput output = mGson.fromJson(data.toString(), GetPracticeOnlyTopicOutput.class);
        return output;
    }

    public GetTagsOutput getTags() throws ApiException, JSONException, IOException {
        JSONObject data = mHttpApi.doHttpGetWithHeader(getFullUrl(TAGS));
        GetTagsOutput output = mGson.fromJson(data.toString(), GetTagsOutput.class);
        return output;
    }
}
