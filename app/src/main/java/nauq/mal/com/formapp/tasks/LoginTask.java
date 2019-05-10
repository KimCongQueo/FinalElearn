package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.exception.ApiException;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;

public class LoginTask extends BaseTask<LoginOutput> {
    private LoginInput loginInput;
    public LoginTask(Context context, LoginInput loginInput,@Nullable ApiListener<LoginOutput> listener) {
        super(context, listener);
        this.loginInput = loginInput;
    }

    @Override
    protected LoginOutput callApiMethod() throws Exception {
        return mApi.loginByEmail(loginInput);
    }
}
