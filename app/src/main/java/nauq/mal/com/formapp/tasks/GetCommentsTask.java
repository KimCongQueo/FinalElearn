package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCommentOutput;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;

public class GetCommentsTask extends BaseTask<GetCommentOutput> {
    private int mStart;
    private String idPost;
    public GetCommentsTask(Context context, String idPost, int mStart, @Nullable ApiListener<GetCommentOutput> listener) {
        super(context, listener);
        this.mStart = mStart;
        this.idPost = idPost;
    }


    @Override
    protected GetCommentOutput callApiMethod() throws  Exception {
        return mApi.getComments(idPost, mStart);
    }
}
