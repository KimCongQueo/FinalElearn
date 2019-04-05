package nauq.mal.com.formapp.activities;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import nauq.mal.com.formapp.R;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mViewBirthday;
    private TextView tvBirthday;
    private Calendar mCalendarBirthday;
    private Calendar mCalendar;
    @Override
    protected int initLayout() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void initComponents() {
        setTitle(getString(R.string.txt_edit_profile));
        mViewBirthday = (ImageView) findViewById(R.id.view_birthday);
        tvBirthday = findViewById(R.id.tv_birthday);
    }

    @Override
    protected void addListener() {
        mViewBirthday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_birthday:
                showDatePicker(tvBirthday);
                break;
        }
    }

    private void showDatePicker(final TextView tvBirthday)  {
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
}
