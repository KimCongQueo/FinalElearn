package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.DeleteCommentTask;

public class CustomChooseEditCmtDialog extends Dialog implements View.OnClickListener {
    private TextView tvEdit;
    private TextView tvDelete;
    private View viewDevider;
    private ConstraintLayout layoutEdit;
    private EditText etEdit;
    private TextView btnDone;
    private TextView btnCancel;
    private CommentItem item;
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_option_cmt);
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

    public CustomChooseEditCmtDialog(@NonNull Context context, CommentItem item) {
        super(context);
        this.item = item;
        this.mContext = context;
    }

    public void setmIOnItemClickedListener(IOnItemClickedListener mIOnItemClickedListener) {
        this.mIOnItemClickedListener = mIOnItemClickedListener;
    }

    private void init() {
        tvEdit = findViewById(R.id.tv_edit);
        tvDelete = findViewById(R.id.tv_delete);
        btnCancel = findViewById(R.id.tv_cancel);
        btnDone = findViewById(R.id.tv_done);
        viewDevider = findViewById(R.id.view_divider_top);
        layoutEdit = findViewById(R.id.layout_edit);
        etEdit = findViewById(R.id.et_edit);
    }

    private void addListener() {
        tvEdit.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                layoutEdit.setVisibility(View.VISIBLE);
                viewDevider.setVisibility(View.GONE);
                tvDelete.setVisibility(View.GONE);
                etEdit.setText(item.getContent());
                break;
            case R.id.tv_delete:
                if (mIOnItemClickedListener != null) {
                    mIOnItemClickedListener.onItemClickDelete(true);
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_done:
                if (mIOnItemClickedListener != null) {
                    mIOnItemClickedListener.onItemEdit(etEdit.getText().toString().trim());
                }
                dismiss();
                break;
        }
    }

    public interface IOnItemClickedListener {
        void onItemClickDelete(boolean isDelete);

        void onItemEdit(String edit);
    }
}