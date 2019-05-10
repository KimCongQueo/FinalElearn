package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class BookmarkTask extends BaseTask<BaseOutput> {
    private String idPost;
    private String type = "";
    public BookmarkTask(Context context, String idPost, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.idPost = idPost;
    }
    public BookmarkTask(Context context, String idPost, String type, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.idPost = idPost;
        this.type = type;
    }
    @Override
    protected BaseOutput callApiMethod() throws Exception {
        if(type.equals("word")){
            return mApi.bookmarkWord(idPost);
        }
        else return mApi.bookmark(idPost);
    }
}
