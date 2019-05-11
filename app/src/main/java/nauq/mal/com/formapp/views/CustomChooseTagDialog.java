package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.RcTagAdapter;
import nauq.mal.com.formapp.models.Tags;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class CustomChooseTagDialog extends Dialog implements View.OnClickListener {
    private IOnItemClickedListener mIOnItemClickedListener;
    private TextView tvDone;
    private Context mContext;
    private ArrayList<Tags> mDataTag = new ArrayList<>();
    private RecyclerView rcTag;
    private RcTagAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_tag);
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

    public CustomChooseTagDialog(@NonNull Context context, ArrayList<Tags> mDataTag) {
        super(context);
        this.mContext = context;
        this.mDataTag = mDataTag;
    }

    public void setmIOnItemClickedListener(IOnItemClickedListener mIOnItemClickedListener) {
        this.mIOnItemClickedListener = mIOnItemClickedListener;
    }

    private void init() {
        tvDone = findViewById(R.id.tv_done);
        rcTag = findViewById(R.id.rcTag);
        rcTag.setLayoutManager( new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new RcTagAdapter(mContext, mDataTag);
        rcTag.setAdapter(mAdapter);
    }

    private void addListener() {
        tvDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_done:
                if(mIOnItemClickedListener != null){
                    mIOnItemClickedListener.onItemClickDone(true);
                }
                dismiss();
                break;
        }
    }

    public interface IOnItemClickedListener {
        void onItemClickDone(boolean done);
    }
}