package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.models.User;

public class AddPostTask extends BaseTask<BaseOutput> {
    private String content;
    private ArrayList<String> mData;
    public AddPostTask(Context context, String content,ArrayList<String> mData, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.content = content;
        this.mData = mData;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.addPost(content, mData);
    }
}
