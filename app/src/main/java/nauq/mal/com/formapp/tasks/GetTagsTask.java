package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCommentOutput;
import nauq.mal.com.formapp.api.models.GetTagsOutput;

public class GetTagsTask extends BaseTask<GetTagsOutput> {
    public GetTagsTask(Context context,  @Nullable ApiListener<GetTagsOutput> listener) {
        super(context, listener);
    }


    @Override
    protected GetTagsOutput callApiMethod() throws  Exception {
        return mApi.getTags();
    }
}
