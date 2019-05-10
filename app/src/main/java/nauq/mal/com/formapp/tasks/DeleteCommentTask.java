package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class DeleteCommentTask extends BaseTask<BaseOutput> {
    private String id;
    public DeleteCommentTask(Context context, String id, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.deleteComment(id);
    }
}
