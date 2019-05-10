package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.objects.PasswordInput;
import nauq.mal.com.formapp.models.User;

public class ChangePasswordTaskk extends BaseTask<BaseOutput> {
    private PasswordInput input;
    public ChangePasswordTaskk(Context context, PasswordInput input, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.input = input;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.changePassword(input);
    }
}
