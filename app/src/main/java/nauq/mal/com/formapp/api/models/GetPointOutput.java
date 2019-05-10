package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import nauq.mal.com.formapp.models.Bookmark;

public class GetPointOutput extends BaseOutput implements Serializable {
    @SerializedName("match")
    public int match = -1;

    public boolean hasNextPage = true;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
