package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class LikeTask extends BaseTask<BaseOutput> {
    private String idPost;
    public LikeTask(Context context,String idPost, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.idPost = idPost;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.likePost(idPost);
    }
}
