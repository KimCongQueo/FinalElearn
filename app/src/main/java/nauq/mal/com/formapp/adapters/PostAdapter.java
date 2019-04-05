package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.fragments.home.HomeFragment;
import nauq.mal.com.formapp.interfaces.ReturnTextFromDialog;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.views.CustomFeedbackAndRipDialog;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
//        implements ReturnTextFromDialog
{
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<PostItem> mData;
    private Boolean checkFeedback = false;
    private String textFromDialog;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public PostAdapter(Context context, List<PostItem> PostItem) {
        this.mContext = context;
        this.mData = PostItem;
    }

    public Boolean getCheckFeedback() {
        return checkFeedback;
    }

    public String getTextFromDialog() {
        return textFromDialog;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostItem item = mData.get(position);
        if(item.getQuestion() != ""){
            holder.tvQuestion.setText(item.getQuestion());
        }
        if(item.getNumComment() != -1){
            holder.tvNumCmt.setText(item.getNumComment()+"");
        }
        if(item.getNumLike() != -1){
            holder.tvNumLike.setText(item.getNumLike()+"");
        }
        if(item.getCheckBookmark()){
            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_bookmark_selected));
        } else {
            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_bookmark_default));
        }
        if(item.getCheckLike()){
            holder.imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_select));
        } else {
            holder.imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_def));
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

//    @Override
//    public void return_value(String value, boolean checkFeedback) {
//        this.checkFeedback = checkFeedback;
//        textFromDialog = value;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, imgBookmark, imgLike;
        ImageView imgReport;
        LinearLayout layoutLike, layoutComment, layoutBookmark;
        CardView cvPost;
        TextView tvFullName;
        TextView tvQuestion;
        TextView tvLike;
        TextView tvBookmark;
        TextView tvNumLike;
        TextView tvNumCmt;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imv_ava_post);
            cvPost = itemView.findViewById(R.id.cv_post);
            layoutLike = itemView.findViewById(R.id.layout_like);
            layoutComment = itemView.findViewById(R.id.layout_comment);
            layoutBookmark = itemView.findViewById(R.id.layout_bookmark);
            tvFullName = itemView.findViewById(R.id.tv_fullname);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvNumLike = itemView.findViewById(R.id.tv_num_like);
            tvNumCmt = itemView.findViewById(R.id.tv_num_comment);
            imgBookmark = itemView.findViewById(R.id.img_bookmark);
            imgLike = itemView.findViewById(R.id.img_like);
            tvLike = itemView.findViewById(R.id.tv_like);
            tvBookmark = itemView.findViewById(R.id.tv_bookmark);
            imgReport = itemView.findViewById(R.id.img_report_fb);
            imgReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomFeedbackAndRipDialog dialog = new CustomFeedbackAndRipDialog(mContext, mData.get(getLayoutPosition()));
                    dialog.show();
                }
            });
            cvPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getIdPost());
                    }
                }
            });
            layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClickComment(mData.get(getLayoutPosition()).getIdPost());
                    }
                }
            });
            layoutBookmark.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    //set event onclick bookmark
                    if(mData.get(getLayoutPosition()).getCheckBookmark()){
                        mData.get(getLayoutPosition()).setCheckBookmark(false);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_bookmark_default));
                    } else {
                        mData.get(getLayoutPosition()).setCheckBookmark(true);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_bookmark_selected));
                    }
                }
            });
            layoutLike.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    //set event onclick like
                    if(mData.get(getLayoutPosition()).getCheckLike()){
                        mData.get(getLayoutPosition()).setCheckLike(false);
                        mData.get(getLayoutPosition()).setNumLike( mData.get(getLayoutPosition()).getNumLike()-1);
                        tvNumLike.setText(mData.get(getLayoutPosition()).getNumLike()+"");
                        imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_def));
                    } else {
                        mData.get(getLayoutPosition()).setCheckLike(true);
                        mData.get(getLayoutPosition()).setNumLike( mData.get(getLayoutPosition()).getNumLike()+1);
                        tvNumLike.setText(mData.get(getLayoutPosition()).getNumLike()+"");
                        imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_select));
                    }
                }
            });
        }
    }

    public interface IOnItemClickedListener {
        void onItemClick(int id);
        void onItemClickComment(int id);
    }

}
