package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetProfileOutput;
import nauq.mal.com.formapp.models.User;

public class EditProfileTask extends BaseTask<BaseOutput> {
    private User user;
    public EditProfileTask(Context context,User user, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.user = user;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.putProfile(user);
    }
}
