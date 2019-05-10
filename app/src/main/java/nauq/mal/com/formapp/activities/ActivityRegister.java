package nauq.mal.com.formapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetProfileOutput;
import nauq.mal.com.formapp.api.models.LoginOutput;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetProfileTask;
import nauq.mal.com.formapp.tasks.SignupTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class ActivityRegister extends BaseActivity implements View.OnClickListener, ApiListener {
    private EditText etUsername;
    private EditText etPass;
    private EditText etRepass;
    private EditText etFullname;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etAddress;
    private TextView tvBirthday;
    private Button btnSignUp;
    private ImageView imgBack;
    private ImageView imgChooseBirthday;
    private RadioGroup radioGroup;
    private RadioButton rdMale;
    private RadioButton rdFemale;
    private Calendar mCalendarBirthday;
    private Calendar mCalendar;
    private User mUser;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initComponents() {
        setTitle("Sign up");
        etUsername = findViewById(R.id.et_username);
        etPass = findViewById(R.id.et_pass);
        etRepass = findViewById(R.id.et_re_pass);
        etFullname = findViewById(R.id.et_fullname);
        etEmail = findViewById(R.id.et_email);
//        etPhone = findViewById(R.id.et_phone);
//        etAddress = findViewById(R.id.et_address);
//        tvBirthday = findViewById(R.id.tv_birthday);
        btnSignUp = findViewById(R.id.btn_sign_up);
//        imgChooseBirthday = findViewById(R.id.view_birthday);
//        radioGroup = findViewById(R.id.rd_group_gender);
//        rdMale = findViewById(R.id.rd_male);
//        rdFemale = findViewById(R.id.rd_female);
        imgBack = findViewById(R.id.imv_nav_left);
    }

    @Override
    protected void addListener() {
        btnSignUp.setOnClickListener(this);
        imgBack.setOnClickListener(this);
//        imgChooseBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.btn_sign_up:
                if (validate()) {
                    showLoading(true);
                    new SignupTask(this, mUser, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;
//            case R.id.view_birthday:
//                showDatePicker(tvBirthday);
//                break;
        }
    }

    private boolean validate() {
        if (etUsername.getText().toString().trim().length() == 0) {
            toast("Please enter user name");
            return false;
        }
//        if (!TextUtils.isEmpty(etUsername.getText().toString().trim())) {
//            toast("Please enter user name");
//            return false;
//        }
        else if (etPass.getText().toString().trim().length() == 0) {
            toast("Please enter password");
            return false;
        } else if (etRepass.getText().toString().trim().length() == 0) {
            toast("Please re enter password");
            return false;
        } else if (!etPass.getText().toString().trim().equals(etRepass.getText().toString().trim())) {
            toast("Password not equal with re-password");
            return false;
        } else if (etFullname.getText().toString().trim().length() == 0) {
            toast("Please enter fullname");
            return false;
        }
//        else if (tvBirthday.getText().toString().trim().length() == 0) {
//            toast("Please choose birhday");
//            return false;
//        }
//        else if (etPhone.getText().toString().trim().length() == 0) {
//            toast("Please enter phone number");
//            return false;
//        }
//        else if (etAddress.getText().toString().trim().length() == 0) {
//            toast("Please enter address");
//            return false;
//        }
        else if (etEmail.getText().toString().trim().length() == 0) {
            toast("Please enter email");
            return false;
        } else if (!isValidateEmail((etEmail.getText().toString().trim()))) {
            return false;
        }
        mUser = new User();
        mUser.setUsername(etUsername.getText().toString().trim());
        mUser.setPassword(etPass.getText().toString().trim());
        mUser.setName(etFullname.getText().toString().trim());
//        mUser.setPhone(etPhone.getText().toString().trim());
//        mUser.setAddress(etAddress.getText().toString().trim());
        mUser.setEmail(etEmail.getText().toString().trim());
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
        if (task instanceof SignupTask) {
            LoginOutput output = (LoginOutput) data;
            if (output.success) {
//                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USER_PROFILE, mUser.toString());
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_SESSION_ID, output.token);
                new GetProfileTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                showAlert(getString(R.string.err_unexpected_exception));
            }
        }
        if (task instanceof GetProfileTask) {
            showLoading(false);
            GetProfileOutput output = (GetProfileOutput) data;
            if (output.success) {
                mUser = output.user;
                SharedPreferenceHelper.getInstance(this).set(Constants.PREF_USER_ID, mUser.getId());
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
