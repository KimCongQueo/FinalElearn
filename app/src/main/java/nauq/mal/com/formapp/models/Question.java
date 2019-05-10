package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("imgs")
    private ArrayList<String> imgs;
    @SerializedName("content")
    private String content;
    @SerializedName("answers")
    private ArrayList<String> answers;
    private ArrayList<Answer> arrAns = new ArrayList<>();


    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<Answer> getArrAns() {
        return arrAns;
    }

    public void setArrAns(ArrayList<Answer> arrAns) {
        this.arrAns = arrAns;
    }
}
