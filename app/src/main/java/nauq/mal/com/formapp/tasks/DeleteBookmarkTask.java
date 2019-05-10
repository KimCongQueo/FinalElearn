package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class DeleteBookmarkTask extends BaseTask<BaseOutput> {
    private String idPost;
    private String word;
    public DeleteBookmarkTask(Context context, String idPost, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.idPost = idPost;
    }
    public DeleteBookmarkTask(Context context, String idPost, String word, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.idPost = idPost;
        this.word = word;
    }
    @Override
    protected BaseOutput callApiMethod() throws Exception {
        if(word.equals("word")){
            return mApi.deleteBookmarkWord(idPost);
        }
        else return mApi.deleteBookmark(idPost);
    }
}
