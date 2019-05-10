package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.CommentItem;

public class CustomSearchDialog extends Dialog implements View.OnClickListener {
    private EditText etSearch;
    private TextView btnDone;
    private TextView btnCancel;
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search);
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

    public CustomSearchDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public void setmIOnItemClickedListener(IOnItemClickedListener mIOnItemClickedListener) {
        this.mIOnItemClickedListener = mIOnItemClickedListener;
    }

    private void init() {
        btnCancel = findViewById(R.id.tv_cancel);
        btnDone = findViewById(R.id.tv_done);
        etSearch = findViewById(R.id.et_search);
    }

    private void addListener() {
        btnDone.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_done:
                if (mIOnItemClickedListener != null) {
                    mIOnItemClickedListener.onItemSearch(etSearch.getText().toString().trim());
                }
                dismiss();
                break;
        }
    }

    public interface IOnItemClickedListener {
        void onItemSearch(String edit);
    }
}