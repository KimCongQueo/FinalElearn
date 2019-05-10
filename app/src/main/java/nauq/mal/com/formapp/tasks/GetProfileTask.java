package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetProfileOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;

public class GetProfileTask extends BaseTask<GetProfileOutput> {
    public GetProfileTask(Context context,  @Nullable ApiListener<GetProfileOutput> listener) {
        super(context, listener);
    }

    @Override
    protected GetProfileOutput callApiMethod() throws Exception {
        return mApi.getProfile();
    }
}
