package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.fragments.home.HomeFragment;
import nauq.mal.com.formapp.interfaces.ReturnTextFromDialog;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;
import nauq.mal.com.formapp.utils.StringUtils;
import nauq.mal.com.formapp.views.CustomFeedbackAndRipDialog;
import nauq.mal.com.formapp.views.CustomShowDialog;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
//        implements ReturnTextFromDialog
{
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<PostItem> mData;
    private ArrayList<String> mListImg;
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
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final PostItem item = mData.get(position);
        if (item.getContent() != "") {
            holder.tvQuestion.setText(item.getContent());
        }
        if (item.getUserPost() != null) {
            if (item.getUserPost().getName() != null && !item.getUserPost().getName().equals("")) {
                holder.tvFullName.setText(item.getUserPost().getName());
            }
            if (item.getUserPost().getAvatar() != null && item.getUserPost().getAvatar() != "") {
                Picasso.with(mContext).load(Constants.LINK_IMG + item.getUserPost().getAvatar())
                        .placeholder(R.drawable.ic_user_def).error(R.drawable.ic_user_def).into(holder.imgAvatar);
            } else {
                Picasso.with(mContext).load(R.drawable.ic_user_def).placeholder(R.drawable.ic_user_def)
                        .error(R.drawable.ic_user_def).into(holder.imgAvatar);
            }
        }

        if (item.getComments() != -1) {
            holder.tvNumCmt.setText(item.getComments() + "");
        }
        if (item.getLikes() != -1) {
            holder.tvNumLike.setText(item.getLikes() + "");
        }
        if (item.getCheckBookmark()) {
            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
        } else {
            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
        }
        if (item.getCheckLike()) {
            holder.imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
        } else {
            holder.imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_def));
        }

        if (item.getCreated() != -1) {
            holder.tvCreated.setText(StringUtils.getFullDate2StringFromNotTimeZone(item.getCreated()));
        }
        mListImg = new ArrayList<>();
        for (String file : mData.get(position).getImgs()) {
            mListImg.add(Constants.LINK_IMG + file);
        }
        if(!SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)){
            holder.layoutLike.setVisibility(View.VISIBLE);
            holder.layoutBookmark.setVisibility(View.VISIBLE);
        } else {
            holder.layoutLike.setVisibility(View.GONE);
            holder.layoutBookmark.setVisibility(View.GONE);
        }
        holder.mAdapter = new LoadImgPostAdapter(mContext, mListImg);
        holder.mAdapter.setOnItemClickListener(new LoadImgPostAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(int pos) {
                CustomShowDialog dialog = new CustomShowDialog(mContext,  mData.get(position), pos);
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();
                dialog.setmIOnItemClickedListener(new CustomShowDialog.IOnItemClickedListener() {
                    @Override
                    public void onItemClickComment(boolean comment) {
                       if(comment){
                           if (mIOnItemClickedListener != null) {
                               mIOnItemClickedListener.onItemClickComment(mData.get(position));
                           }
                       }
                    }

                    @Override
                    public void onItemClickLike(boolean islike) {
                        if(islike){
                            if (mIOnItemClickedListener != null) {
                                mIOnItemClickedListener.onItemLike(item.getIdPost());
                            }
                            holder.imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
                            holder.tvNumLike.setText(Integer.toString(item.getLikes() + 1));
                        }
                    }

                    @Override
                    public void onItemClickDislike(boolean dislike) {
                        if(dislike){
                            if (mIOnItemClickedListener != null) {
                                mIOnItemClickedListener.onItemDisLike(item.getIdPost());
                            }
                            holder.imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_def));
                            holder.tvNumLike.setText(Integer.toString(item.getLikes() - 1));
                        }
                    }

                    @Override
                    public void onItemClickBookmark(boolean bookmark) {
                        if(bookmark){
                            if (mIOnItemClickedListener != null) {
                                mIOnItemClickedListener.onItemBookmark(item.getIdPost());
                            }
                            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
                        }
                    }

                    @Override
                    public void onItemClickDeleteBookmark(boolean delBookmark) {
                        if(delBookmark){
                            if (mIOnItemClickedListener != null) {
                                mIOnItemClickedListener.onItemDeleteBookmark(item.getIdPost());
                            }
                            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_default));
                        }
                    }
                });
            }
        });
        holder.rcImg.setAdapter(holder.mAdapter);
        if (item.getUserPost() != null &&
                item.getUserPost().getId().equals(SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_USER_ID))) {
            holder.imgReport.setVisibility(View.VISIBLE);
        } else {
            holder.imgReport.setVisibility(View.GONE);
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
        TextView tvCreated;
        RecyclerView rcImg;
        LoadImgPostAdapter mAdapter;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imv_ava_post);
            cvPost = itemView.findViewById(R.id.cv_post);
            layoutLike = itemView.findViewById(R.id.layout_like);
            layoutComment = itemView.findViewById(R.id.layout_comment);
            layoutBookmark = itemView.findViewById(R.id.layout_bookmark);
            tvFullName = itemView.findViewById(R.id.tv_fullname_post);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvNumLike = itemView.findViewById(R.id.tv_num_like);
            tvNumCmt = itemView.findViewById(R.id.tv_num_comment);
            tvCreated = itemView.findViewById(R.id.tv_date_post);
            imgBookmark = itemView.findViewById(R.id.img_bookmark);
            imgLike = itemView.findViewById(R.id.img_like);
            tvLike = itemView.findViewById(R.id.tv_like);
            tvBookmark = itemView.findViewById(R.id.tv_bookmark);
            imgReport = itemView.findViewById(R.id.img_report_fb);
            rcImg = itemView.findViewById(R.id.rc_img_post);
//            rcImg.setLayoutManager(new GridLayoutManager(mContext, 3));
            rcImg.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            imgReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomFeedbackAndRipDialog dialog = new CustomFeedbackAndRipDialog(mContext, mData.get(getLayoutPosition()));
                    dialog.show();
                    dialog.setOnItemClickListener(new CustomFeedbackAndRipDialog.IOnItemClickedListener() {
                        @Override
                        public void onItemDelete(Boolean checkDelete) {
                            if (mIOnItemClickedListener != null) {
                                mIOnItemClickedListener.onItemDelete(checkDelete);
                            }
                        }

                        @Override
                        public void onItemEdit(Boolean checkEdit) {
                            if (mIOnItemClickedListener != null) {
                                mIOnItemClickedListener.onItemEdit(checkEdit, getLayoutPosition());
                            }
                        }
                    });

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
                        mIOnItemClickedListener.onItemClickComment(mData.get(getLayoutPosition()));
                    }
                }
            });
            layoutBookmark.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    //set event onclick bookmark
                    if (mData.get(getLayoutPosition()).getCheckBookmark()) {
                        mData.get(getLayoutPosition()).setCheckBookmark(false);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemDeleteBookmark(mData.get(getLayoutPosition()).getIdPost());
                        }
                    } else {
                        mData.get(getLayoutPosition()).setCheckBookmark(true);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemBookmark(mData.get(getLayoutPosition()).getIdPost());
                        }
                    }
                }
            });
            layoutLike.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    //set event onclick like
                    if (mData.get(getLayoutPosition()).getCheckLike()) {
                        mData.get(getLayoutPosition()).setCheckLike(false);
                        mData.get(getLayoutPosition()).setLikes(mData.get(getLayoutPosition()).getLikes() - 1);
                        tvNumLike.setText(mData.get(getLayoutPosition()).getLikes() + "");
                        imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_def));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemDisLike(mData.get(getLayoutPosition()).getIdPost());
                        }
                    } else {
                        mData.get(getLayoutPosition()).setCheckLike(true);
                        mData.get(getLayoutPosition()).setLikes(mData.get(getLayoutPosition()).getLikes() + 1);
                        tvNumLike.setText(mData.get(getLayoutPosition()).getLikes() + "");
                        imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemLike(mData.get(getLayoutPosition()).getIdPost());
                        }
                    }

                }
            });
        }
    }

    public interface IOnItemClickedListener {
        void onItemClick(String id);

        void onItemClickComment(PostItem postItem);

        void onItemDelete(Boolean isDelete);

        void onItemEdit(Boolean isDelete, int pos);

        void onItemLike(String id);

        void onItemDisLike(String id);
        void onItemBookmark(String id);

        void onItemDeleteBookmark(String id);
    }

}
