package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("image")
    private String image;
    @SerializedName("question")
    private String question;
    @SerializedName("arrAns")
    private ArrayList<Answer> arrAns;

    public Question(int id, String image, ArrayList<Answer> arrAns) {
        this.id = id;
        this.image = image;
        this.arrAns = arrAns;
    }

    public Question(String question) {
        this.question = question;
    }

    public Question(String question, ArrayList<Answer> arrAns) {
        this.question = question;
        this.arrAns = arrAns;
    }

    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Answer> getArrAns() {
        return arrAns;
    }

    public void setArrAns(ArrayList<Answer> arrAns) {
        this.arrAns = arrAns;
    }


}
