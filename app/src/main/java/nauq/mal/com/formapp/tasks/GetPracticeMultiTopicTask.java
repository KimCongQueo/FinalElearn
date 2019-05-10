package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetPracticeOnlyTopicOutput;

public class GetPracticeMultiTopicTask extends BaseTask<GetPracticeOnlyTopicOutput> {
    ArrayList<String> id;
    public GetPracticeMultiTopicTask(Context context,  ArrayList<String> id, @Nullable ApiListener<GetPracticeOnlyTopicOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected GetPracticeOnlyTopicOutput callApiMethod() throws Exception {
        return mApi.getPracticeMulti(id);
    }
}
