package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostItem implements Serializable {
    @SerializedName("id")
    private int idPost;
    @SerializedName("question")
    private String question ="";
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    @SerializedName("numLike")
    private int numLike = -1;
    @SerializedName("numComment")
    private int numComment = -1;
    private Boolean checkBookmark = false;
    private Boolean checkLike = false;
    private String feedBack = "";
    private String report = "";
    public PostItem() {
    }

    public PostItem(int idPost, String question) {
        this.idPost = idPost;
        this.question = question;
    }

    public PostItem(int idPost, String question, int numLike, int numComment) {
        this.idPost = idPost;
        this.question = question;
        this.numLike = numLike;
        this.numComment = numComment;
    }

    public PostItem(int idPost, String question, String answerA, String answerB, String answerC, String answerD) {
        this.idPost = idPost;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    public PostItem(String question, String answerA, String answerB, String answerC, String answerD) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public int getNumComment() {
        return numComment;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    public PostItem(int idPost) {
        this.idPost = idPost;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public Boolean getCheckBookmark() {
        return checkBookmark;
    }

    public void setCheckBookmark(Boolean checkBookmark) {
        this.checkBookmark = checkBookmark;
    }

    public Boolean getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(Boolean checkLike) {
        this.checkLike = checkLike;
    }
}
