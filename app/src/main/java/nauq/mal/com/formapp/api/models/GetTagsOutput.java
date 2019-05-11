package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.models.Tags;

public class GetTagsOutput extends  BaseOutput {
    @SerializedName("tags")
    public ArrayList<Tags> tags;
    public boolean hasNextPage = true;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
