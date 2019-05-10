package nauq.mal.com.formapp.tasks;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.ImgOutput;

public class UploadImageTask extends BaseTask<ImgOutput> {
    private ArrayList<File> mFiles;

    public UploadImageTask(Context context, ArrayList<File> files, @Nullable ApiListener<ImgOutput> listener) {
        super(context, listener);
        this.mFiles = files;
    }

    @Override
    protected ImgOutput callApiMethod() throws Exception {
        return mApi.uploadAvatar(this.mFiles);
    }
}
