package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("answer")
    private String answer;
    private boolean selected = false;

    public Answer(int id, String answer, boolean selected) {
        this.id = id;
        this.answer = answer;
        this.selected = selected;
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
