package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;

public class GetChildrenCategoriesTask extends BaseTask<GetCategoriesOutput> {
    private String id;
    public GetChildrenCategoriesTask(Context context, String id, @Nullable ApiListener<GetCategoriesOutput> listener) {
        super(context, listener);
        this.id = id;
    }

    @Override
    protected GetCategoriesOutput callApiMethod() throws Exception {
        return mApi.getCategoriesChildren(id);
    }
}
