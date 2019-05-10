package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class PostItem implements Serializable {
    @SerializedName("_id")
    private String idPost;
    @SerializedName("imgs")
    private ArrayList<String> imgs;
    @SerializedName("profile")
    private User userPost;
    @SerializedName("content")
    private String content ="";
    @SerializedName("created")
    private long created = -1;
//    @SerializedName("numLike")
//    private int numLike = -1;
//    @SerializedName("numComment")
//    private int numComment = -1;
    @SerializedName("comments")
    private int comments = -1;
    @SerializedName("likes")
    private int likes = -1;
    @SerializedName("isBookmark")
    private Boolean checkBookmark = false;
    @SerializedName("isLike")
    private Boolean checkLike = false;
    private String feedBack = "";
    private String report = "";
    private ArrayList<File> mDataFile;
    public PostItem() {
    }

    public ArrayList<File> getmDataFile() {
        return mDataFile;
    }

    public void setmDataFile(ArrayList<File> mDataFile) {
        this.mDataFile = mDataFile;
    }

    public PostItem(int i, String s) {
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public User getUserPost() {
        return userPost;
    }

    public void setUserPost(User userPost) {
        this.userPost = userPost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
//
//    public int getNumLike() {
//        return numLike;
//    }
//
//    public void setNumLike(int numLike) {
//        this.numLike = numLike;
//    }
//
//    public int getNumComment() {
//        return numComment;
//    }
//
//    public void setNumComment(int numComment) {
//        this.numComment = numComment;
//    }


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

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
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
