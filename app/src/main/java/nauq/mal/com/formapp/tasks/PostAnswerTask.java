package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetBookmarkOutput;
import nauq.mal.com.formapp.api.models.GetPointOutput;

public class PostAnswerTask extends BaseTask<GetPointOutput> {
    private String matches;
    private ArrayList<String> ans;
    public PostAnswerTask(Context context, String matches,ArrayList<String> ans, @Nullable ApiListener<GetPointOutput> listener) {
        super(context, listener);
        this.matches = matches;
        this.ans = ans;
    }


    @Override
    protected GetPointOutput callApiMethod() throws  Exception {
        return mApi.getPoint(matches, ans);
    }
}
