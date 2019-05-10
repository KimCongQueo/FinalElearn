package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.AddQuesActivity;
import nauq.mal.com.formapp.adapters.PostAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.fragments.home.HomeFragment;
import nauq.mal.com.formapp.interfaces.ReturnTextFromDialog;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.DeletePostTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class CustomFeedbackAndRipDialog extends Dialog implements View.OnClickListener, ApiListener {
    private LinearLayout mBtnDone, mBtnCancel, layoutBelowText;
    private LinearLayout layoutEdit;
    private LinearLayout layoutDoneDel;
    private IOnItemClickedListener mIOnItemClickedListener;
    private LinearLayout layoutCancelDel;
    private LinearLayout layoutButton;
    private Boolean checkFeedback = true;
    private TextView tvReport, tvFeedback;
    private TextView tvEdit;
    private TextView tvDelete;
    private TextView tvWantDelete;
    private View viewDivider;
    private View viewDividerTopDel;
    private View viewDividerTopEdit;
    private View viewDividerWantDel;
    private PostItem postItem;
    private EditText etAdd;
    private Context mContext;
    private HomeFragment homeFragment;
    private ReturnTextFromDialog returnTextFromDialog;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }
    public CustomFeedbackAndRipDialog(@NonNull Context context) {
        super(context);
    }

    public CustomFeedbackAndRipDialog(@NonNull Context context, PostItem postItem) {
        super(context);
        this.mContext = context;
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
        layoutButton = findViewById(R.id.layout_button);
        tvWantDelete = findViewById(R.id.tv_want_delete);
        layoutCancelDel = findViewById(R.id.tab_cancel_delete);
        layoutDoneDel = findViewById(R.id.tab_done_delete);
        viewDividerTopEdit = findViewById(R.id.view_divider_top_edit);
        viewDividerTopDel = findViewById(R.id.view_divider_top_delete);
        viewDividerWantDel = findViewById(R.id.view_devider_below_delete);
        tvReport = findViewById(R.id.tv_report);
        viewDivider = findViewById(R.id.view_divider_top);
        etAdd = findViewById(R.id.et_add);
        tvDelete = findViewById(R.id.tv_delete);
        tvEdit = findViewById(R.id.tv_edit);
        layoutEdit = findViewById(R.id.layout_edit);
        layoutBelowText = findViewById(R.id.layout_below_report_text);
        if (postItem.getUserPost().getId().equals(SharedPreferenceHelper.getInstance(getContext()).get(Constants.PREF_USER_ID))) {
            layoutEdit.setVisibility(View.VISIBLE);
        } else {
            layoutEdit.setVisibility(View.GONE);
        }
    }

    private void addListener() {
        mBtnCancel.setOnClickListener(this);
        mBtnDone.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        layoutDoneDel.setOnClickListener(this);
        layoutCancelDel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                viewDividerTopDel.setVisibility(View.GONE);
                viewDividerTopEdit.setVisibility(View.GONE);
                tvEdit.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                break;
            case R.id.tv_report:
                checkFeedback = false;
                tvFeedback.setVisibility(View.GONE);
                viewDivider.setVisibility(View.GONE);
                tvReport.setVisibility(View.VISIBLE);
                layoutBelowText.setVisibility(View.VISIBLE);
                viewDividerTopDel.setVisibility(View.GONE);
                viewDividerTopEdit.setVisibility(View.GONE);
                tvEdit.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                break;
            case R.id.tv_delete:
                tvFeedback.setVisibility(View.GONE);
                viewDivider.setVisibility(View.GONE);
                tvReport.setVisibility(View.GONE);
                tvEdit.setVisibility(View.GONE);
                viewDividerTopDel.setVisibility(View.GONE);
                tvWantDelete.setVisibility(View.VISIBLE);
                viewDividerWantDel.setVisibility(View.VISIBLE);
                layoutButton.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_edit:
                if (mIOnItemClickedListener != null) {
                    mIOnItemClickedListener.onItemEdit(true);
                }
                dismiss();
                break;
            case R.id.tab_cancel_delete:
                dismiss();
                break;
            case R.id.tab_done_delete:
                new DeletePostTask(mContext, postItem.getIdPost(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if(task instanceof DeletePostTask){
            BaseOutput output = (BaseOutput) data;
            if(output.success){
                if (mIOnItemClickedListener != null) {
                    mIOnItemClickedListener.onItemDelete(true);
                }
                dismiss();
            } else {

            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
    public interface IOnItemClickedListener {
        void onItemDelete(Boolean checkDelete);
        void onItemEdit(Boolean checkEdit);
    }
}
