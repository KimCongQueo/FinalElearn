package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import nauq.mal.com.formapp.models.Bookmark;
import nauq.mal.com.formapp.models.TopicItem;
import nauq.mal.com.formapp.models.Words;

public class GetBookmarkOutput extends BaseOutput implements Serializable {
    @SerializedName("posts")
    public ArrayList<Bookmark> posts;
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
