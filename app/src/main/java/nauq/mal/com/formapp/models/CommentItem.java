package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentItem implements Serializable {
    @SerializedName("_id")
    private String idComment;
    @SerializedName("content")
    private String content;
    @SerializedName("created")
    private long created = -1;
    @SerializedName("profile")
    private User userCmt;
    private boolean checkFirst = false;
    @SerializedName("imgs")
    private ArrayList<String> imgs;
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
    public CommentItem() {
    }

    public CommentItem(String id, String content, long created, User userCmt, boolean checkFirst, ArrayList<String> imgs,
                       int comments, int likes, Boolean checkBookmark, Boolean checkLike) {
        this.idComment = id;
        this.content = content;
        this.created = created;
        this.userCmt = userCmt;
        this.checkFirst = checkFirst;
        this.imgs = imgs;
        this.comments = comments;
        this.likes = likes;
        this.checkBookmark = checkBookmark;
        this.checkLike = checkLike;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
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

    public CommentItem(String content, long created, User userCmt, boolean checkFirst) {
        this.content = content;
        this.created = created;
        this.userCmt = userCmt;
        this.checkFirst = checkFirst;
    }

    public CommentItem(String content, long created) {
        this.content = content;
        this.created = created;
    }
    public CommentItem(String content, long created, boolean checkFirst) {
        this.content = content;
        this.created = created;
        this.checkFirst = checkFirst;
    }
    public CommentItem(String idComment, String content, long created) {
        this.idComment = idComment;
        this.content = content;
        this.created = created;
    }

    public boolean isCheckFirst() {
        return checkFirst;
    }

    public void setCheckFirst(boolean checkFirst) {
        this.checkFirst = checkFirst;
    }

    public boolean isCheckLike() {
        return checkLike;
    }

    public void setCheckLike(boolean checkLike) {
        this.checkLike = checkLike;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
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

    public User getUserCmt() {
        return userCmt;
    }

    public void setUserCmt(User userCmt) {
        this.userCmt = userCmt;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
