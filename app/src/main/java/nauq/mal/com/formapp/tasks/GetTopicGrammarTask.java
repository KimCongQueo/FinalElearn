package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetTopicOutput;

public class GetTopicGrammarTask extends BaseTask<GetTopicOutput> {
    String jsonString;
    public GetTopicGrammarTask(Context context, String jsonString, @Nullable ApiListener<GetTopicOutput> listener) {
        super(context, listener);
        this.jsonString = jsonString;
    }

    @Override
    protected GetTopicOutput callApiMethod() throws  Exception {
        return mApi.getTopicGrammar(jsonString);
    }
}
