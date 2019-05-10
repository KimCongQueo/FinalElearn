package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import java.util.Calendar;
import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;
import nauq.mal.com.formapp.utils.StringUtils;
import nauq.mal.com.formapp.views.CustomChooseEditCmtDialog;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<CommentItem> mData;
    public static final int FIRST = 0;
    public static final int NO_FIRST = 1;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public CommentAdapter(Context context, List<CommentItem> CommentItem) {
        this.mContext = context;
        this.mData = CommentItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        switch (viewType) {
            case FIRST:
                View itemView1 = li.inflate(R.layout.item_post, parent, false);
                return new PostViewHolder(itemView1);
            case NO_FIRST:
                View itemView0 = li.inflate(R.layout.item_comment2, parent, false);
                return new ItemViewHolder(itemView0);
            default:
                break;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isCheckFirst())
            return FIRST;
        else return NO_FIRST;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case NO_FIRST:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                CommentItem item = mData.get(position);
                if (item.getContent() != null) {
                    itemViewHolder.tvComment.setText(item.getContent());
                }
                if (item.getCreated() != -1) {
                    itemViewHolder.tvCreate.setText(StringUtils.getFullDate2StringFromTimestampNotTimeZone(item.getCreated()));
                }
                if(item.getUserCmt()!=null){
                    if (item.getUserCmt().getName() != null) {
                        itemViewHolder.tvFullNameCmt.setText(item.getUserCmt().getName());
                    }
                    if (item.getUserCmt().getAvatar() != null && item.getUserCmt().getAvatar().length() > 0) {
                        Picasso.with(mContext).load(Constants.LINK_IMG
                                + item.getUserCmt().getAvatar()).into(itemViewHolder.imgAvaCmt);
                    }
                    if (item.getUserCmt().getId()
                            .equals(SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_USER_ID))){
                        itemViewHolder.imgEdit.setVisibility(View.VISIBLE);
                    } else {
                        itemViewHolder.imgEdit.setVisibility(View.GONE);
                    }
                }
                if (position == 1) {
                    itemViewHolder.vDevider.setVisibility(View.GONE);
                }

                break;
            case FIRST:
                PostViewHolder postViewHolder = (PostViewHolder) holder;
                CommentItem itemPost = mData.get(position);
                if (itemPost.getContent() != null) {
                    postViewHolder.tvQuestion.setText(itemPost.getContent());
                }
                if (itemPost.getCreated() != -1) {
                    postViewHolder.tvDatePost.setText(StringUtils.getFullDate2StringFromTimestampNotTimeZone(itemPost.getCreated()));
                }
                if (itemPost.getUserCmt() != null) {
                    postViewHolder.tvFullname.setText(itemPost.getUserCmt().getName().toString());
                }
                if (itemPost.getUserCmt().getAvatar() != null && itemPost.getUserCmt().getAvatar().length() > 0) {
                    Picasso.with(mContext).load(Constants.LINK_IMG
                            + itemPost.getUserCmt().getAvatar()).into(postViewHolder.imgAvatar);
                }
                if (itemPost.getComments() != -1) {
                    postViewHolder.tvNumCmt.setText(Integer.toString(itemPost.getComments()));
                }
                if (itemPost.getLikes() != -1) {
                    postViewHolder.tvNumLike.setText(Integer.toString(itemPost.getLikes()));
                }
                if (itemPost.getCheckBookmark()) {
                    postViewHolder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
                } else {
                    postViewHolder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
                }
                if (itemPost.getCheckLike()) {
                    postViewHolder.imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
                } else {
                    postViewHolder.imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_def));
                }
                if(itemPost.getImgs() != null && itemPost.getImgs().size() > 0){
                    ArrayList<String> mListImg = new ArrayList<>();
                    for (String file : itemPost.getImgs()) {
                        mListImg.add(Constants.LINK_IMG + file);
                    }
                    postViewHolder.mAdapter = new LoadImgPostAdapter(mContext, mListImg);
                    postViewHolder.rcImg.setAdapter(postViewHolder.mAdapter);
                }
                postViewHolder.imgReport.setVisibility(View.GONE);
                if(!SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)){
                    postViewHolder.layoutLike.setVisibility(View.VISIBLE);
                    postViewHolder.layoutBookmark.setVisibility(View.VISIBLE);
                } else {
                    postViewHolder.layoutLike.setVisibility(View.GONE);
                    postViewHolder.layoutBookmark.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullname;
        ImageView imgAvatar;
        TextView tvDatePost;
        TextView tvQuestion;
        TextView tvNumLike;
        TextView tvNumCmt;
        ImageView imgBookmark, imgLike;
        ImageView imgReport;
        RecyclerView rcImg;
        LoadImgPostAdapter mAdapter;
        LinearLayout layoutLike, layoutBookmark;
        public PostViewHolder(View itemView) {
            super(itemView);
            tvFullname = itemView.findViewById(R.id.tv_fullname_post);
            imgAvatar = itemView.findViewById(R.id.imv_ava_post);
            tvDatePost = itemView.findViewById(R.id.tv_date_post);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvNumLike = itemView.findViewById(R.id.tv_num_like);
            layoutLike = itemView.findViewById(R.id.layout_like);
            layoutBookmark = itemView.findViewById(R.id.layout_bookmark);
            tvNumCmt = itemView.findViewById(R.id.tv_num_comment);
            imgBookmark = itemView.findViewById(R.id.img_bookmark);
            imgLike = itemView.findViewById(R.id.img_like);
            rcImg = itemView.findViewById(R.id.rc_img_post);
            imgReport = itemView.findViewById(R.id.img_report_fb);
//            rcImg.setLayoutManager(new GridLayoutManager(mContext, 3));
            rcImg.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            layoutBookmark.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    //set event onclick bookmark
                    if (mData.get(getLayoutPosition()).getCheckBookmark()) {
                        mData.get(getLayoutPosition()).setCheckBookmark(false);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemDeleteBookmark(mData.get(getLayoutPosition()).getIdComment());
                        }
                    } else {
                        mData.get(getLayoutPosition()).setCheckBookmark(true);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemBookmark(mData.get(getLayoutPosition()).getIdComment());
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
                            mIOnItemClickedListener.onItemDisLike(mData.get(getLayoutPosition()).getIdComment());
                        }
                    } else {
                        mData.get(getLayoutPosition()).setCheckLike(true);
                        mData.get(getLayoutPosition()).setLikes(mData.get(getLayoutPosition()).getLikes() + 1);
                        tvNumLike.setText(mData.get(getLayoutPosition()).getLikes() + "");
                        imgLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_like_cmt_select));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemLike(mData.get(getLayoutPosition()).getIdComment());
                        }
                    }

                }
            });
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        View vDevider;
        TextView tvComment, tvCreate;
        TextView tvFullNameCmt;
        ImageView imgAvaCmt;
        ImageView imgEdit;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvCreate = itemView.findViewById(R.id.tv_date_create);
            vDevider = itemView.findViewById(R.id.view_divider_top);
            tvFullNameCmt = itemView.findViewById(R.id.tv_fullname_cmt);
            imgAvaCmt = itemView.findViewById(R.id.imv_ava_cmt);
            imgEdit = itemView.findViewById(R.id.imv_edit);
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(getLayoutPosition()).getUserCmt().getId()
                            .equals(SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_USER_ID))) {
                        CustomChooseEditCmtDialog dialog = new CustomChooseEditCmtDialog(mContext, mData.get(getLayoutPosition()));
                        dialog.show();
                        dialog.setmIOnItemClickedListener(new CustomChooseEditCmtDialog.IOnItemClickedListener() {
                            @Override
                            public void onItemClickDelete(boolean isDelete) {
                                if (isDelete) {
                                    if (mIOnItemClickedListener != null) {
                                        mIOnItemClickedListener.onItemClickDelete(getLayoutPosition());
                                    }
                                }
                            }

                            @Override
                            public void onItemEdit(String edit) {
                                if (mIOnItemClickedListener != null) {
                                    mIOnItemClickedListener.onItemEdit(edit, mData.get(getLayoutPosition()).getIdComment());
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public interface IOnItemClickedListener {
        void onItemEdit(String edit, String idCmt);
        void onItemLike(String id);

        void onItemDisLike(String id);
        void onItemBookmark(String id);

        void onItemDeleteBookmark(String id);
        void onItemClickDelete(int pos);
    }
}
