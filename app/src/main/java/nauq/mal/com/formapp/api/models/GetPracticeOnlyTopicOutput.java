package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.Categories;
import nauq.mal.com.formapp.models.Question;

public class GetPracticeOnlyTopicOutput extends  BaseOutput {
    @SerializedName("matches")
    public String matches;
    @SerializedName("questions")
    public ArrayList<Question> questions;
    public boolean hasNextPage = true;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
