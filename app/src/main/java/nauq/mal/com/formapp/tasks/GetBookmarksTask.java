package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetBookmarkOutput;
import nauq.mal.com.formapp.api.models.GetCommentOutput;

public class GetBookmarksTask extends BaseTask<GetBookmarkOutput> {
    private int mStart;
    public GetBookmarksTask(Context context, int mStart, @Nullable ApiListener<GetBookmarkOutput> listener) {
        super(context, listener);
        this.mStart = mStart;
    }


    @Override
    protected GetBookmarkOutput callApiMethod() throws  Exception {
        return mApi.getBookmarks(mStart);
    }
}
