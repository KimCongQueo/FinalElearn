package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.PostItem;

public class GetNewfeedOutput extends  BaseOutput {
    @SerializedName("result")
    public ArrayList<PostItem> result;

}
