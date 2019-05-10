package nauq.mal.com.formapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.ImgOutput;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.EditProfileTask;
import nauq.mal.com.formapp.tasks.UploadImageTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;
import nauq.mal.com.formapp.utils.StringUtils;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    public static final int SELECT_PICTURE = 101;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private ImageView mViewBirthday;
    private TextView tvBirthday;
    private TextView tvUsername;
    private EditText etFullname;
    private EditText etAdress;
    private EditText etPhone;
    private EditText etEmail;
    private Calendar mCalendarBirthday;
    private Calendar mCalendar;
    private ImageView imgBack;
    private ImageView imgAvatar;
    private RadioButton rdMale;
    private RadioButton rdFemale;
    private User user = new User();
    private ImageView imgChangeAva;
    private Button btnSubmit;
    private Uri mImageCaptureUri;
    private String myImagePath;
    private String myImagePathCrop;
    private String myImagePathCropCurrent;
    private String imgId = "";

    @Override
    protected int initLayout() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initComponents() {
        setTitle(getString(R.string.txt_edit_profile));
        mViewBirthday = (ImageView) findViewById(R.id.view_birthday);
        tvBirthday = findViewById(R.id.tv_birthday);
        imgBack = findViewById(R.id.imv_nav_left);
        etAdress = findViewById(R.id.et_address);
        etEmail = findViewById(R.id.et_email);
        etFullname = findViewById(R.id.et_fullname);
        etPhone = findViewById(R.id.et_phone);
        tvUsername = findViewById(R.id.tv_username);
        imgAvatar = findViewById(R.id.imv_avatar);
        rdMale = findViewById(R.id.rd_male);
        rdFemale = findViewById(R.id.rd_female);
        btnSubmit = findViewById(R.id.btn_edit);
        imgChangeAva = findViewById(R.id.imv_change_avatar);
        loadData();
    }

    private void loadData() {
        String userJson = SharedPreferenceHelper.getInstance(this).get(Constants.PREF_USER_PROFILE);
        if (userJson != null && userJson.length() > 0) {
            user = new Gson().fromJson(userJson, User.class);
            if (user.getUsername() != null && user.getUsername().length() > 0) {
                tvUsername.setText(user.getUsername());
            } else {
                tvUsername.setText(R.string.txt_no_text);
            }
            if (user.getName() != null && user.getName().length() > 0) {
                etFullname.setText(user.getName());
            } else {
                etFullname.setText(R.string.txt_no_text);
            }
            if (user.getPhone() != null && user.getPhone().length() > 0) {
                etPhone.setText(user.getPhone());
            } else {
                etPhone.setText(R.string.txt_no_text);
            }
            if (user.getAddress() != null && user.getAddress().length() > 0) {
                etAdress.setText(user.getAddress());
            } else {
                etAdress.setText(R.string.txt_no_text);
            }
            if (user.getEmail() != null && user.getEmail().length() > 0) {
                etEmail.setText(user.getEmail());
            } else {
                etEmail.setText(R.string.txt_no_text);
            }
            if (user.getBirthday() != -1) {
                tvBirthday.setText(StringUtils.getDateStringFromTimestampFull(user.getBirthday()));
            } else {
                tvBirthday.setText(R.string.txt_no_text);
            }
            if (user.isGender() == true) {
                rdMale.setChecked(true);
                rdFemale.setChecked(false);
            } else if (user.isGender() == false) {
                rdFemale.setChecked(true);
                rdMale.setChecked(false);
            }
//            else {
//                tvGender.setText(R.string.txt_no_text);
//            }
            if (user.getAvatar() != null && user.getAvatar().length() > 0) {
                Picasso.with(this).load(Constants.LINK_IMG + user.getAvatar()).into(imgAvatar);
            }
        }
    }

    @Override
    protected void addListener() {
        mViewBirthday.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        imgChangeAva.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_birthday:
                showDatePicker(tvBirthday);
                break;
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.btn_edit:
                showLoading(true);
                if (isValidate()) {
                    new EditProfileTask(this, user, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;
            case R.id.imv_change_avatar:
                showDialogChoose();
                break;
        }
    }

    private boolean isValidate() {
        if (etFullname.getText().toString().trim().length() == 0) {
            toast("Please enter fullname");
            return false;
        } else if (tvBirthday.getText().toString().trim().length() == 0) {
            toast("Please choose birhday");
            return false;
        } else if (etPhone.getText().toString().trim().length() == 0) {
            toast("Please enter phone number");
            return false;
        } else if (etAdress.getText().toString().trim().length() == 0) {
            toast("Please enter address");
            return false;
        } else if (etEmail.getText().toString().trim().length() == 0) {
            toast("Please enter email");
            return false;
        } else if (!isValidateEmail((etEmail.getText().toString().trim()))) {
            return false;
        } else if(rdMale.isChecked() && !rdFemale.isChecked()) {
            user.setGender(true);
        } else if(!rdMale.isChecked() && rdFemale.isChecked()){
            user.setGender(false);
        }
        user.setName(etFullname.getText().toString().trim());
        user.setPhone(etPhone.getText().toString().trim());
        user.setAddress(etAdress.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        if (imgId != "" && imgId != user.getAvatar()){
            user.setAvatar(imgId);
        }
//        mUser.setGender(true);
//        mUser.setAge(22);
        return true;
    }

    private boolean isValidateEmail(String target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void showDatePicker(final TextView tvBirthday) {
        if (mCalendarBirthday != null) {
            mCalendar = (Calendar) mCalendarBirthday.clone();
        } else {
            mCalendar = Calendar.getInstance();
        }
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, int monthOfYear,
                                          int dayOfMonth) {
                        Calendar dateChoose = Calendar.getInstance();
                        dateChoose.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateChoose.set(Calendar.MONTH, monthOfYear);
                        dateChoose.set(Calendar.YEAR, year);
                        if (dateChoose.getTimeInMillis() <= Calendar.getInstance().getTimeInMillis()) {
                            tvBirthday.setText(getDD(dayOfMonth) + "/" + getDD((monthOfYear + 1)) + "/" + year);
                            mCalendarBirthday = Calendar.getInstance();
                            mCalendarBirthday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            mCalendarBirthday.set(Calendar.MONTH, monthOfYear);
                            mCalendarBirthday.set(Calendar.YEAR, year);
                            user.setBirthday(mCalendarBirthday.getTimeInMillis());
                        } else {
                            toast(R.string.txt_compare_birthday);
                        }

                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        dialog.show();
    }

    public String getDD(int num) {
        return num > 9 ? "" + num : "0" + num;
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof EditProfileTask) {
            BaseOutput output = (BaseOutput) data;
            if (output.success) {
                toast("Success");
                showLoading(false);
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USER_PROFILE, new Gson().toJson(user));
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
                imgId = output.imgId;
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
    }


    @Override
    public void onConnectionError(BaseTask task, Exception exception) {
        showLoading(false);
        showAlert(exception);
    }
    //take image

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
//        mImageCaptureUri = Uri.fromFile(f);
        final Uri data = FileProvider.getUriForFile(this, "nauq.mal.com.fileprovider", new File(file.getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg"));
        this.grantUriPermission(this.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mImageCaptureUri = data;
        myImagePath = f.getPath();
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
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
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        List<Intent> targets = new ArrayList<Intent>();
        List<ResolveInfo> candidates = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo candidate : candidates) {
            String packageName = candidate.activityInfo.packageName;
            if (!packageName.equals("com.google.android.apps.photos") && !packageName.equals("com.google.android.apps.plus") && !packageName.equals("com.android.documentsui")) {
                Intent iWantThis = new Intent();
                iWantThis.setType("image/*");
                iWantThis.setAction(Intent.ACTION_PICK);
                iWantThis.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
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
                    Picasso.with(this).load("file://" + myImagePathCrop).into(imgAvatar);

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
