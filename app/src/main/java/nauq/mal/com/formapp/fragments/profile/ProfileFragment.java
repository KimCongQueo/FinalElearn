package nauq.mal.com.formapp.fragments.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.ChangePasswordActivity;
import nauq.mal.com.formapp.activities.EditProfileActivity;
import nauq.mal.com.formapp.activities.MainActivity;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetProfileOutput;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.models.User;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetProfileTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;
import nauq.mal.com.formapp.utils.StringUtils;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, ApiListener {
    private Button btnEdit;
    private LinearLayout viewChangePass;
    private TextView tvEmail, tvFullname, tvPhone, tvAddress, tvGender, tvBirthday, tvUsername;
    private ImageView imgAvatar, imgChangeAva;
    private User user = new User();

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initComponents() {
        tvEmail = mView.findViewById(R.id.tv_email);
        tvFullname = mView.findViewById(R.id.tv_fullname);
        tvPhone = mView.findViewById(R.id.tv_phone_number);
        tvAddress = mView.findViewById(R.id.tv_address);
        tvGender = mView.findViewById(R.id.tv_gender);
        tvBirthday = mView.findViewById(R.id.tv_birthday);
        tvUsername = mView.findViewById(R.id.tv_username);
        imgAvatar = mView.findViewById(R.id.imv_avatar);
        imgChangeAva = mView.findViewById(R.id.imv_change_avatar);
        btnEdit = mView.findViewById(R.id.btn_edit);
        viewChangePass = mView.findViewById(R.id.view_change_password);
        imgAvatar = mView.findViewById(R.id.imv_avatar);
        imgChangeAva = mView.findViewById(R.id.imv_change_avatar);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData1();

    }

    private void loadData1() {
        new GetProfileTask(mContext, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadData() {
        String userJson = SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_USER_PROFILE);
        if (userJson != null && userJson.length() > 0) {
            user = new Gson().fromJson(userJson, User.class);
            if (user.getUsername() != null && user.getUsername().length() > 0) {
                tvUsername.setText(user.getUsername());
            } else {
                tvUsername.setText(R.string.txt_no_text);
            }
            if (user.getName() != null && user.getName().length() > 0) {
                tvFullname.setText(user.getName());
            } else {
                tvFullname.setText(R.string.txt_no_text);
            }
            if (user.getPhone() != null && user.getPhone().length() > 0) {
                tvPhone.setText(user.getPhone());
            } else {
                tvPhone.setText(R.string.txt_no_text);
            }
            if (user.getAddress() != null && user.getAddress().length() > 0) {
                tvAddress.setText(user.getAddress());
            } else {
                tvAddress.setText(R.string.txt_no_text);
            }
            if (user.getEmail() != null && user.getEmail().length() > 0) {
                tvEmail.setText(user.getEmail());
            } else {
                tvEmail.setText(R.string.txt_no_text);
            }
            if (user.getBirthday() != -1) {
                tvBirthday.setText(StringUtils.getDateStringFromTimestampFull(user.getBirthday()));
            } else {
                tvBirthday.setText(R.string.txt_no_text);
            }
            if (user.isGender() == true) {
                tvGender.setText("Male");
            } else if (user.isGender() == false) {
                tvGender.setText("Female");
            } else {
                tvGender.setText(R.string.txt_no_text);
            }
            if (user.getAvatar() != null && user.getAvatar().length() > 0) {
                Picasso.with(mContext).load(Constants.LINK_IMG
                        + user.getAvatar()).into(imgAvatar);
            }
        }
    }

    @Override
    protected void addListener() {
        btnEdit.setOnClickListener(this);
        viewChangePass.setOnClickListener(this);
        imgChangeAva.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_change_password:
                startActivity(new Intent(mContext, ChangePasswordActivity.class));
                break;
            case R.id.imv_change_avatar:
                break;
            case R.id.btn_edit:
                startActivity(new Intent(mContext, EditProfileActivity.class));
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetProfileTask) {
            showLoading(false);
            GetProfileOutput output = (GetProfileOutput) data;
            if (output.success) {
                user = output.user;
                SharedPreferenceHelper.getInstance(mContext).set(Constants.PREF_USER_PROFILE, new Gson().toJson(user));
                SharedPreferenceHelper.getInstance(mContext).set(Constants.PREF_PERSON_NAME, user.getName());
                loadData();
            } else {
                showAlert(getString(R.string.txt_warning_login_fail));
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
}
