package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.api.models.GetProfileOutput;

public class GetCategoriesTask extends BaseTask<GetCategoriesOutput> {
    public GetCategoriesTask(Context context, @Nullable ApiListener<GetCategoriesOutput> listener) {
        super(context, listener);
    }

    @Override
    protected GetCategoriesOutput callApiMethod() throws Exception {
        return mApi.getCategories();
    }
}
