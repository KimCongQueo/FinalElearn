package nauq.mal.com.formapp.activities;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btnCancel, btnAgree;
    private ImageView imgBack;
    public static int RQ_ENTER_CODE = 9898;
    private EditText mEdtPassword, mEdtPasswordConfirm, mEdtPasswordOld;
    @Override
    protected int initLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initComponents() {
        setTitle(getString(R.string.txt_change_password));
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnAgree = (Button) findViewById(R.id.btn_agree);
        imgBack = findViewById(R.id.imv_nav_left);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mEdtPasswordConfirm = (EditText) findViewById(R.id.edt_password_confirm);
        mEdtPasswordOld = (EditText) findViewById(R.id.edt_password_old);
    }

    @Override
    protected void addListener() {
        btnCancel.setOnClickListener(this);
        btnAgree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.btn_cancel:
//                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_agree:
                String password = mEdtPassword.getText().toString();
                String passwordConfirm = mEdtPasswordConfirm.getText().toString();
                String passwordOld = mEdtPasswordOld.getText().toString();
                String currentPass = SharedPreferenceHelper.getInstance(this).get(Constants.PREF_PASSWORD_NAME);
//                if (passwordOld.length() == 0) {
//                    toast(getString(R.string.txt_warning_plz_input_password_old));
//                    return;
//                }
//                if(!passwordOld.equals(currentPass)){
//                    toast(getString(R.string.txt_new_password_old_incorrect));
//                    return;
//                }
                if (password.length() == 0) {
                    toast(getString(R.string.txt_warning_plz_input_new_password));
                    return;
                }
                if (password.length() < 6) {
                    toast(getString(R.string.txt_pass_least_6));
                    return;
                }
                if(password.equals(passwordOld)){
                    toast(getString(R.string.txt_pass_invalid_2));
                    return;
                }
                if (!password.equals(passwordConfirm)) {
                    toast(getString(R.string.txt_confirm_password_incorrect));
                    return;
                }

//                showLoading(true);
//                new ChangePasswordTask(this, passwordOld, password, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                finish();
                break;
        }
    }
}
