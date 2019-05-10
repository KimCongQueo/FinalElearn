package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOutput;
import nauq.mal.com.formapp.api.models.GetTopicOutput;

public class GetTopicTask extends BaseTask<GetCategoriesOutput> {
    String id;
    public GetTopicTask(Context context, String id, @Nullable ApiListener<GetCategoriesOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected GetCategoriesOutput callApiMethod() throws  Exception {
        return mApi.getTopic(id);
    }
}
