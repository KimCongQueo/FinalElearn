package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;
import nauq.mal.com.formapp.models.User;

public class SignupTask extends BaseTask<LoginOutput> {
    private User user;
    public SignupTask(Context context, User user, @Nullable ApiListener<LoginOutput> listener) {
        super(context, listener);
        this.user = user;
    }

    @Override
    protected LoginOutput callApiMethod() throws Exception {
        return mApi.signup(user);
    }
}
