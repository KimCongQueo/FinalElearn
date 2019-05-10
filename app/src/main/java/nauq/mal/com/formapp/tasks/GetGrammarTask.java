package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetGrammarOutput;
import nauq.mal.com.formapp.api.models.GetWordsOutput;

public class GetGrammarTask extends BaseTask<GetGrammarOutput> {
    private String id;
    public GetGrammarTask(Context context, String id, @Nullable ApiListener<GetGrammarOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected GetGrammarOutput callApiMethod() throws Exception {
        return mApi.getGrammar(id);
    }
}
