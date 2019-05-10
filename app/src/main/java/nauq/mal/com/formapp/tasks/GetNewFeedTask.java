package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.exception.ApiException;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.fragments.home.HomeFragment;

public class GetNewFeedTask extends BaseTask<GetNewfeedOutput> {
    private int mStart;
    private String search;
    public GetNewFeedTask(Context context, int mStart, String search, @Nullable ApiListener<GetNewfeedOutput> listener) {
        super(context, listener);
        this.mStart = mStart;
        this.search = search;
    }


    @Override
    protected GetNewfeedOutput callApiMethod() throws  Exception {
        if(search.equals("")){
            return mApi.getNewfeed(mStart);
        } else {
            return mApi.getNewfeedSearch(mStart, search);
        }
    }
}
