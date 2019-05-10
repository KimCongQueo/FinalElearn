package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class AddCommentTask extends BaseTask<BaseOutput> {
    private String content;
    private String idPost;
    public AddCommentTask(Context context, String content, String idPost, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.content = content;
        this.idPost = idPost;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.addCmt(content, idPost);
    }
}
