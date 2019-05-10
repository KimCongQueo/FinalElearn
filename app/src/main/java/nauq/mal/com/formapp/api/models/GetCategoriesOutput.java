package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.Categories;
import nauq.mal.com.formapp.models.CommentItem;

public class GetCategoriesOutput extends  BaseOutput {
    @SerializedName("categories")
    public ArrayList<Categories> categories;
    @SerializedName("children")
    public ArrayList<Categories> children;
    public boolean hasNextPage = true;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
