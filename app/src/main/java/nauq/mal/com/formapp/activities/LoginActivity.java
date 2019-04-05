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
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class LoginActivity extends BaseActivity implements  View.OnClickListener{
    private String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE};
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private EditText mEdtUsername, mEdtPassword;
    private Button mBtnLogin, mBtnLoginGg;
    private TextView mTvForgotPassword;
    private FrameLayout layoutRoot;
    private static final int RC_SIGIN_IN = 1;
    private static final String TAG = LoginActivity.class.getName();
    AlertDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;
    private User user;
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
        mBtnLoginGg = findViewById(R.id.btn_login_google);
        mTvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        layoutRoot = findViewById(R.id.layout_root);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        checkLogin();
    }


    public void checkLogin() {
        String userJson = SharedPreferenceHelper.getInstance(this).get(Constants.PREF_USER_PROFILE);
        if (userJson != null && userJson.length() > 0) {
            User user = new Gson().fromJson(userJson, User.class);
            System.out.println(user.toString());
            if (user != null) {
                if(SharedPreferenceHelper.getInstance(this).get(Constants.LOGIN_GOOGLE) == "true"){
                mEdtUsername.setText(SharedPreferenceHelper.getInstance(this).get(Constants.PREF_USERNAME));
                mEdtPassword.setText(SharedPreferenceHelper.getInstance(this).get(Constants.PREF_PASSWORD_NAME));
                showLoading(true);
//                new LoginTask(this, new LoginInput(mEdtUsername.getText().toString().trim(), mEdtPassword.getText().toString().trim()), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            }
        }
    }


    @Override
    protected void addListener() {
        mBtnLogin.setOnClickListener(this);
        mTvForgotPassword.setOnClickListener(this);
        mBtnLoginGg.setOnClickListener(this);
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
//                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.btn_login:
                if (valid()) {
                    startActivity(new Intent(this, MainActivity.class));
//                    showLoading(true);
//                    new LoginTask(this, new LoginInput(mEdtUsername.getText().toString().trim(), mEdtPassword.getText().toString().trim()), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;
            case R.id.btn_login_google:
                showLoading(true);
                requestSignIn();
                break;
        }
    }

    private void requestSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGIN_IN);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGIN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
//            user.setAvatar(account.getPhotoUrl()+"");
//            user.setUserName(account.getDisplayName().toString());
//            user.setEmail(account.zab());
//            String personName = account.getDisplayName();
//            String personGivenName = account.getGivenName();
//            String personFamilyName = account.getFamilyName();
//            String personEmail = account.getEmail();
//            String personId = account.getId();
//            Uri personPhoto = account.getPhotoUrl();
            User user = new User();
            user.setEmail(account.getEmail());
            user.setAvatar(String.valueOf(account.getPhotoUrl()));
            user.setFullName(String.valueOf(account.getDisplayName()));
            SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USER_PROFILE,  new Gson().toJson(user));
            SharedPreferenceHelper.getInstance(this).set(Constants.EXTRAX_EMAIL, account.getEmail());
            SharedPreferenceHelper.getInstance(this).set(Constants.PREF_AVATAR, String.valueOf(account.getPhotoUrl()));
            SharedPreferenceHelper.getInstance(this).set(Constants.PREF_PERSON_NAME, String.valueOf(account.getDisplayName()));
            SharedPreferenceHelper.getInstance(this).set(Constants.LOGIN_GOOGLE, "true");
        }
        try {
            account = task.getResult(ApiException.class);
            continueWith(account);
        } catch (ApiException e) {
            e.printStackTrace();
            Snackbar.make(layoutRoot, R.string.login_gg_failed, Snackbar.LENGTH_LONG).show();

        }
    }
    private void continueWith(GoogleSignInAccount currentAccount) {
        showLoading(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}

