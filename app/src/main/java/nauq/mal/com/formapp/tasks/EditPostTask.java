package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;

public class EditPostTask extends BaseTask<BaseOutput> {
    private String content;
    private ArrayList<String> mData;
    private String id;
    public EditPostTask(Context context, String id, String content, ArrayList<String> mData, @Nullable ApiListener<BaseOutput> listener) {
        super(context, listener);
        this.content = content;
        this.mData = mData;
        this.id = id;
    }

    @Override
    protected BaseOutput callApiMethod() throws Exception {
        return mApi.editPost(id, content, mData);
    }
}
