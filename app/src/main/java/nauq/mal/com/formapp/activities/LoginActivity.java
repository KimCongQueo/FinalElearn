package nauq.mal.com.formapp.activities;

import android.Manifest;
import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetProfileOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.api.objects.LoginInput;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetProfileTask;
import nauq.mal.com.formapp.tasks.LoginTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    private String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE};
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnLogin;
    private TextView mTvForgotPassword;
    private TextView tvSignup;
    private LinearLayout layoutRoot;
    private static final int RC_SIGIN_IN = 1;
    private static final String TAG = LoginActivity.class.getName();
    AlertDialog progressDialog;
    private User mUser;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponents() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions();
        }

        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        layoutRoot = findViewById(R.id.layout_root);
        tvSignup = findViewById(R.id.tv_sign_up);
        checkLogin();
    }


    public void checkLogin() {
        String userJson = SharedPreferenceHelper.getInstance(this).get(Constants.PREF_USER_PROFILE);
        if (userJson != null && userJson.length() > 0) {
            User user = new Gson().fromJson(userJson, User.class);
            System.out.println(user.toString());
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }


    @Override
    protected void addListener() {
        mBtnLogin.setOnClickListener(this);
        mTvForgotPassword.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }

    private void requestPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_password:
                SharedPreferenceHelper.getInstance(this).clearSharePrefs();
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_SESSION_ID, Constants.FAKE_TOKEN);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_login:
                if (valid()) {
                    showLoading(true);
                    new LoginTask(this, new LoginInput(mEdtUsername.getText().toString().trim(), mEdtPassword.getText().toString().trim()), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;
            case R.id.tv_sign_up:
                startActivity(new Intent(this, ActivityRegister.class));
                break;
        }
    }

    public boolean valid() {
        if (mEdtUsername.getText().toString().trim().length() == 0) {
            toast(R.string.txt_warning_plz_input_username);
            return false;
        }
        if (mEdtPassword.getText().toString().trim().length() == 0) {
            toast(R.string.txt_warning_plz_input_password);
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof LoginTask) {
            LoginOutput output = (LoginOutput) data;
            if (output.success) {
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USER_ID, output.userId);
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_SESSION_ID, output.token);
                new GetProfileTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                showLoading(false);
                showAlert(getString(R.string.txt_warning_login_fail));
            }
        }
        if (task instanceof GetProfileTask) {
            showLoading(false);
            GetProfileOutput output = (GetProfileOutput) data;
            if (output.success) {
                mUser = output.user;
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USERNAME,mUser.getUsername());
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USER_PROFILE, new Gson().toJson(mUser));
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_PERSON_NAME, mUser.getName());
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                showAlert(getString(R.string.txt_warning_login_fail));
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {
        showLoading(false);
        showAlert(exception);
    }
}

