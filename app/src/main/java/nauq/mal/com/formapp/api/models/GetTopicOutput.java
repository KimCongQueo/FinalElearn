package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import nauq.mal.com.formapp.models.TopicItem;

public class GetTopicOutput extends BaseOutput implements Serializable {
    @SerializedName("result")
    public ArrayList<TopicItem> result;
}
