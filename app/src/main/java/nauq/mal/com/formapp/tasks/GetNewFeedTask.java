package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.exception.ApiException;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;

public class GetNewFeedTask extends BaseTask<GetNewfeedOutput> {
    String json;
    public GetNewFeedTask(Context context, String json,@Nullable ApiListener<GetNewfeedOutput> listener) {
        super(context, listener);
        this.json = json;
    }

    @Override
    protected GetNewfeedOutput callApiMethod() throws  Exception {
        return mApi.getNewfeed(json);
    }
}
