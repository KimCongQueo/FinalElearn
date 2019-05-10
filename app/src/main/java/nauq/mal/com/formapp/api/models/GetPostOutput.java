package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.PostItem;

public class GetPostOutput extends  BaseOutput {
    @SerializedName("post")
    public PostItem post;

    public PostItem getPost() {
        return post;
    }

    public void setPost(PostItem post) {
        this.post = post;
    }
}
