package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.MainActivity;
import nauq.mal.com.formapp.fragments.home.HomeFragment;
import nauq.mal.com.formapp.interfaces.ReturnTextFromDialog;
import nauq.mal.com.formapp.models.PostItem;

public class CustomFeedbackAndRipDialog extends Dialog implements View.OnClickListener {
    private LinearLayout mBtnDone, mBtnCancel, layoutBelowText;
    private Boolean checkFeedback = true;
    private TextView tvReport, tvFeedback;
    private View viewDivider;
    private PostItem postItem;
    private EditText etAdd;
    private HomeFragment homeFragment;
    private ReturnTextFromDialog returnTextFromDialog;
    public CustomFeedbackAndRipDialog(@NonNull Context context) {
        super(context);
    }
    public CustomFeedbackAndRipDialog(@NonNull Context context, PostItem postItem) {
        super(context);
        this.postItem = postItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_feedback_dialog);
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setAttributes(wlp);
        init();
        addListener();
    }


    private void init() {
        mBtnCancel = findViewById(R.id.tab_cancel);
        mBtnDone = findViewById(R.id.tab_done);
        tvFeedback = findViewById(R.id.tv_feedback);
        tvReport = findViewById(R.id.tv_report);
        viewDivider = findViewById(R.id.view_divider_top);
        etAdd = findViewById(R.id.et_add);
        layoutBelowText = findViewById(R.id.layout_below_report_text);
    }
    private void addListener() {
        mBtnCancel.setOnClickListener(this);
        mBtnDone.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_done:
                tvFeedback.setVisibility(View.VISIBLE);
                viewDivider.setVisibility(View.VISIBLE);
                tvReport.setVisibility(View.VISIBLE);
                layoutBelowText.setVisibility(View.GONE);
                postItem.setFeedBack(etAdd.getText().toString());
                etAdd.setText("");
                CustomFeedbackAndRipDialog.this.dismiss();
                break;
            case R.id.tab_cancel:
                tvFeedback.setVisibility(View.VISIBLE);
                viewDivider.setVisibility(View.VISIBLE);
                tvReport.setVisibility(View.VISIBLE);
                layoutBelowText.setVisibility(View.GONE);
                etAdd.setText("");
                CustomFeedbackAndRipDialog.this.dismiss();
                break;
            case R.id.tv_feedback:
                checkFeedback = true;
                tvFeedback.setVisibility(View.VISIBLE);
                viewDivider.setVisibility(View.GONE);
                tvReport.setVisibility(View.GONE);
                layoutBelowText.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_report:
                checkFeedback = false;
                tvFeedback.setVisibility(View.GONE);
                viewDivider.setVisibility(View.GONE);
                tvReport.setVisibility(View.VISIBLE);
                layoutBelowText.setVisibility(View.VISIBLE);
                break;
        }
    }

//    @Override
//    public void setOnDismissListener(@Nullable OnDismissListener listener) {
//        super.setOnDismissListener(listener);
//        homeFragment.onResume();
//    }
}
