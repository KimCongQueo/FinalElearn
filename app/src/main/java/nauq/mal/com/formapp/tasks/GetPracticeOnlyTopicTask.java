package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOnlyTopicOutput;

public class GetPracticeOnlyTopicTask extends BaseTask<GetPracticeOnlyTopicOutput> {
    String id;
    public GetPracticeOnlyTopicTask(Context context,String id, @Nullable ApiListener<GetPracticeOnlyTopicOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected GetPracticeOnlyTopicOutput callApiMethod() throws Exception {
        return mApi.getPracticeOnly1(id);
    }
}
