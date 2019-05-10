package nauq.mal.com.formapp.activities;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.PasswordOutput;
import nauq.mal.com.formapp.api.objects.PasswordInput;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.ChangePasswordTaskk;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    private Button btnCancel, btnAgree;
    private ImageView imgBack;
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
        imgBack.setOnClickListener(this);
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
                finish();
                break;
            case R.id.btn_agree:
                String password = mEdtPassword.getText().toString();
                String passwordConfirm = mEdtPasswordConfirm.getText().toString();
                String passwordOld = mEdtPasswordOld.getText().toString();
                if (passwordOld.length() == 0) {
                    toast(getString(R.string.txt_warning_plz_input_old_password));
                    return;
                }
                if (password.length() == 0) {
                    toast(getString(R.string.txt_warning_plz_input_new_password));
                    return;
                }
                if (passwordConfirm.length() == 0) {
                    toast(getString(R.string.txt_warning_plz_input_confirm_password));
                    return;
                }
                showLoading(true);
                PasswordInput passwordInput = new PasswordInput();
                passwordInput.setOldPass(passwordOld);
                passwordInput.setNewPass(password);
                passwordInput.setConfirmPass(passwordConfirm);
                new ChangePasswordTaskk(this, passwordInput, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if(task instanceof ChangePasswordTaskk){
            PasswordOutput output = (PasswordOutput) data;
            showLoading(false);
            if(output.success){
                finish();
            } else {
                if(output.msg != null && output.msg != ""){
                    showAlert(output.msg);
                }
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
}
