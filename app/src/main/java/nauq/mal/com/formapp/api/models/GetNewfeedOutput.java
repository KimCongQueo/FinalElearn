package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.models.Words;

public class GetNewfeedOutput extends  BaseOutput {
    @SerializedName("posts")
    public ArrayList<PostItem> posts;
    @SerializedName("words")
    public ArrayList<Words> words;
    public boolean hasNextPage = true;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
