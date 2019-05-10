package nauq.mal.com.formapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.AddImageAdapter;
import nauq.mal.com.formapp.adapters.EditImageAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.ImgOutput;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.tasks.AddPostTask;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.EditPostTask;
import nauq.mal.com.formapp.tasks.UploadImageTask;

public class EditQuesActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    public static final int SELECT_PICTURE = 101;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private EditText etQuestion;
    private Button btnSEnd, btnAttachImg;
    private ImageView imgBack;
    private RecyclerView rcImg;
    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<String> mDataIdImg = new ArrayList<>();
    private Uri mImageCaptureUri;
    private String myImagePath;
    private PostItem item = new PostItem();
    private String myImagePathCrop;
//    private EditImageAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_add_ques;
    }

    @Override
    protected void initComponents() {
        setTitle(getString(R.string.txt_edit_post));
        etQuestion = findViewById(R.id.et_ques);
        btnAttachImg = findViewById(R.id.btn_attach_img);
        btnAttachImg.setVisibility(View.INVISIBLE);
        btnSEnd = findViewById(R.id.btn_send_ques);
        btnSEnd.setText("Edit post");
        imgBack = findViewById(R.id.imv_nav_left);
        rcImg = findViewById(R.id.rc_img);
        rcImg.setLayoutManager(new GridLayoutManager(this, 3));
//        mAdapter = new EditImageAdapter(this, mData);
//        rcImg.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        Intent i = getIntent();
        item = (PostItem) i.getSerializableExtra("POST");
        if (item != null) {
            etQuestion.setText(item.getContent());
            if (item.getImgs() != null && item.getImgs().size() > 0) {
                mData.addAll(item.getImgs());
//                mDataIdImg.addAll(item.getImgs());
//                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void addListener() {
        btnAttachImg.setOnClickListener(this);
        btnSEnd.setOnClickListener(this);
        imgBack.setOnClickListener(this);
//        mAdapter.setOnItemClickListener(new EditImageAdapter.IOnItemClickedListener() {
//            @Override
//            public void onItemDelete(int position) {
//                if (mData.size() > 0) {
//                    mData.remove(position);
////                    mDataIdImg.remove(position);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_attach_img:
                showDialogChoose();
                break;
            case R.id.btn_send_ques:
                showLoading(true);
                if (etQuestion.getText().toString().length() >= 10) {
                    new EditPostTask(this, item.getIdPost(),etQuestion.getText().toString(), mDataIdImg, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    Toast.makeText(this, "Question is shorter than the minimum allowed length (10)", Toast.LENGTH_LONG).show();
                    showLoading(false);
                    return;
                }
                break;

            case R.id.imv_nav_left:
                finish();
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof EditPostTask) {
            showLoading(false);
            BaseOutput output = (BaseOutput) data;
            if (output.success) {
                finish();
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if (task instanceof UploadImageTask) {
            ImgOutput output = (ImgOutput) data;
            if (output.success) {
                showLoading(false);
                mDataIdImg.add(output.imgId);
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }

    public void showDialogChoose() {
        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.app_name))
                .setItems(R.array.arr_capture, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            onClickTakeAPhoto();
                        } else if (which == 1) {
                            onClickChooseFromGallery();
                        }
                    }
                }).show();
    }

    public void onClickTakeAPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TASKSAPP");
        if (!file.exists()) {
            file.mkdirs();
        }
        File f = new File(file.getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        mImageCaptureUri = Uri.fromFile(f);
        myImagePath = f.getPath();
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        takePictureIntent.putExtra("return-data", true);
        takePictureIntent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void onClickChooseFromGallery() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TASKSAPP");
        if (!file.exists()) {
            file.mkdirs();
        }
        File f = new File(file.getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
        mImageCaptureUri = Uri.fromFile(f);
        myImagePath = f.getPath();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        List<Intent> targets = new ArrayList<Intent>();
        List<ResolveInfo> candidates = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo candidate : candidates) {
            String packageName = candidate.activityInfo.packageName;
            if (!packageName.equals("com.google.android.apps.photos") && !packageName.equals("com.google.android.apps.plus") && !packageName.equals("com.android.documentsui")) {
                Intent iWantThis = new Intent();
                iWantThis.setType("image/*");
                iWantThis.setAction(Intent.ACTION_PICK);
                iWantThis.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                iWantThis.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                iWantThis.setPackage(packageName);
                targets.add(iWantThis);
            }
        }
        if (targets.size() > 0) {
            Intent chooser = Intent.createChooser(targets.remove(0), "Select Picture");
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[targets.size()]));
            startActivityForResult(chooser, SELECT_PICTURE);
        } else {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                    try {
                        Bundle extras = null;
                        if (data != null) {
                            extras = data.getExtras();
                        }
                        if (data != null && extras != null && extras.get("data") != null) {
                            File f = new File(myImagePath);
                            Bitmap bitMap = (Bitmap) extras.get("data");
                            try {
                                f.createNewFile();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitMap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                            byte[] bitmapdata = bos.toByteArray();
                            FileOutputStream fos;
                            try {
                                fos = new FileOutputStream(f);
                                try {
                                    fos.write(bitmapdata);
                                    mImageCaptureUri = Uri.fromFile(f);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Take with Uri
                        }
                    } catch (NullPointerException ex) {

                    }
                    doCrop();
                    break;
                case SELECT_PICTURE:
                    Uri uriPhoto = data.getData();
                    String path = getRealPathFromURI(uriPhoto);
                    if (path != null) {
                        File finalFile1 = new File(path);
                        try {
                            copy(finalFile1, new File(myImagePath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        doCrop();
                    } else {
                        toast(getString(R.string.txt_cannot_access_this_image));
                    }
                    break;
                case Crop.REQUEST_CROP:
                    Uri tempUri = Crop.getOutput(data);
                    File finalFile = new File(tempUri.getPath());
                    myImagePathCrop = finalFile.getPath();
                    mData.add("file://" + myImagePathCrop);
//                    mAdapter.notifyDataSetChanged();
//                    Picasso.with(this).load("file://" + myImagePathCrop).into(imgAvatar);

                    if (myImagePathCrop != null) {
                        File f = new File(myImagePathCrop);
                        if (f.exists()) {
                            showLoading(true);
                            ArrayList<File> files = new ArrayList<>();
                            files.add(f);
                            new UploadImageTask(this, files, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    }
                    break;
            }

        }

    }

    private void doCrop() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String timeStamp = dateFormat.format(new Date());
        String imageFileName = "picture_crop" + timeStamp + ".jpg";
        Uri destination = Uri.fromFile(new File(getCacheDir(), imageFileName));
        Crop.of(mImageCaptureUri, destination).asSquare().withMaxSize(400, 400).start(this);

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    void copy(File source, File target) throws IOException {

        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }

}
