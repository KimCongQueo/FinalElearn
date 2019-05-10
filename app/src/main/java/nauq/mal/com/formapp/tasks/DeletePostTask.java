package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;

public class DeletePostTask extends BaseTask<BaseOutput> {
    private String id;
    public DeletePostTask(Context context, String id, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.deletePost(id);
    }
}
