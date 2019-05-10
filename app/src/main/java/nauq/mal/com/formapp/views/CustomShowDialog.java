package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class CustomShowDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private PostItem item;
    private int pos;
    private ImageView imgShow;
    private ImageView imgLike;
    private ImageView imgBookmark;
    private TextView content;
    private TextView tvName;
    private ConstraintLayout layoutLike;
    private ConstraintLayout layoutComment;
    private ConstraintLayout layoutBookmark;
    private IOnItemClickedListener mIOnItemClickedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_show_img);
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

    public CustomShowDialog(@NonNull Context context, PostItem postItem, int pos) {
        super(context);
        this.mContext = context;
        this.item = postItem;
        this.pos = pos;
    }

    public void setmIOnItemClickedListener(IOnItemClickedListener mIOnItemClickedListener) {
        this.mIOnItemClickedListener = mIOnItemClickedListener;
    }

    private void init() {
        content = findViewById(R.id.tv_content);
        imgShow = findViewById(R.id.img_show);
        imgLike = findViewById(R.id.imgLike);
        imgBookmark = findViewById(R.id.imgBookmark);
        tvName = findViewById(R.id.tv_name);
        layoutComment = findViewById(R.id.layout_comment);
        layoutLike = findViewById(R.id.layout_like);
        layoutBookmark = findViewById(R.id.layout_bookmark);
        if(item != null){
            Picasso.with(mContext).load(Constants.LINK_IMG + item.getImgs().get(pos))
                    .error(R.drawable.ic_avatar_default).placeholder(R.drawable.ic_avatar_default).into(imgShow);
            content.setText(item.getContent());
            tvName.setText(item.getUserPost().getName());
        }
        if (item.getCheckBookmark()) {
            imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
        } else {
            imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_white));
        }
        if (item.getCheckLike()) {
            imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
        } else {
            imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_white));
        }
        if(!SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)){
            layoutLike.setVisibility(View.VISIBLE);
            layoutBookmark.setVisibility(View.VISIBLE);
        } else {
            layoutLike.setVisibility(View.INVISIBLE);
            layoutBookmark.setVisibility(View.INVISIBLE);
        }
    }

    private void addListener() {
        layoutComment.setOnClickListener(this);
        layoutLike.setOnClickListener(this);
        layoutBookmark.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_comment:
                if (mIOnItemClickedListener != null) {
                    mIOnItemClickedListener.onItemClickComment(true);
                }
                dismiss();
                break;
            case R.id.layout_like:
                if (item.getCheckLike()) {
                    item.setCheckLike(false);
                    imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_white));
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClickDislike(true);
                    }
                } else {
                    item.setCheckLike(true);
                    imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClickLike(true);
                    }
                }
                break;
            case R.id.layout_bookmark:
                if (item.getCheckBookmark()) {
                    item.setCheckBookmark(false);
                    imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_white));
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClickDeleteBookmark(true);
                    }
                } else {
                    item.setCheckBookmark(true);
                    imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClickBookmark(true);
                    }
                }
                break;
        }
    }

    public interface IOnItemClickedListener {
        void onItemClickComment(boolean comment);
        void onItemClickLike(boolean islike);
        void onItemClickDislike(boolean dislike );
        void onItemClickBookmark(boolean bookmark);
        void onItemClickDeleteBookmark(boolean delBookmark );
    }
}