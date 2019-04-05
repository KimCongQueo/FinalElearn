package nauq.mal.com.formapp.models;

import java.io.Serializable;

public class CommentItem implements Serializable {
    private int idComment;
    private String content;
    private long created = -1;
    private boolean checkLike = false;
    private boolean checkFirst = false;
    public CommentItem() {
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
    public CommentItem(int idComment, String content, long created) {
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

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
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

    public void setCreated(long created) {
        this.created = created;
    }
}
