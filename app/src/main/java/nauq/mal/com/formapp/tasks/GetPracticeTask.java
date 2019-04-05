package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.exception.ApiException;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOutput;

public class GetPracticeTask extends BaseTask<GetPracticeOutput> {
    String jsonString;
    public GetPracticeTask(Context context, String jsonString, @Nullable ApiListener<GetPracticeOutput> listener) {
        super(context, listener);
        this.jsonString = jsonString;
    }

    @Override
    protected GetPracticeOutput callApiMethod() throws  Exception {
        return mApi.getPractice(jsonString);
    }
}
