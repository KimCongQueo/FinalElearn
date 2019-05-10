package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class EditCommentTask extends BaseTask<BaseOutput> {
    private String content;
    private String id;
    public EditCommentTask(Context context, String id, String content, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.content = content;
        this.id = id;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.editCmt(id, content);
    }
}
