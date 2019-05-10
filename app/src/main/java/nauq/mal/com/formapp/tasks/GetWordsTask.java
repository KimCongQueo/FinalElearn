package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.api.models.GetWordsOutput;

public class GetWordsTask extends BaseTask<GetWordsOutput> {
    private String id;
    public GetWordsTask(Context context, String id, @Nullable ApiListener<GetWordsOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected GetWordsOutput callApiMethod() throws Exception {
        return mApi.getWords(id);
    }
}
