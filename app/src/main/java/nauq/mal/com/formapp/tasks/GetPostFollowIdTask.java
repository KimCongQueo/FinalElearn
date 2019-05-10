package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCommentOutput;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.api.models.GetPostOutput;

public class GetPostFollowIdTask extends BaseTask<GetPostOutput> {
    private String idPost;
    public GetPostFollowIdTask(Context context, String idPost, @Nullable ApiListener<GetPostOutput> listener) {
        super(context, listener);
        this.idPost = idPost;
    }


    @Override
    protected GetPostOutput callApiMethod() throws  Exception {
        return mApi.getPostFollowId(idPost);
    }
}
