package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Calendar;
import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.utils.StringUtils;

public class CommentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        switch (viewType){
            case FIRST:
                View itemView1 = li.inflate(R.layout.item_post, parent, false);
                return new PostViewHolder(itemView1);
            case NO_FIRST:
                View itemView0 = li.inflate(R.layout.item_comment, parent, false);
                return new ItemViewHolder(itemView0);
            default:
                break;
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).isCheckFirst())
            return FIRST;
        else return NO_FIRST;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        switch (getItemViewType(position)) {
            case NO_FIRST:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                CommentItem item = mData.get(position);
                if(item.getContent() != null){
                    itemViewHolder.tvComment.setText(item.getContent());
                }
                if(item.getCreated() != -1){
                    itemViewHolder.tvCreate.setText(StringUtils.getFullDate2StringFromTimestampNotTimeZone(item.getCreated()));
                }

                if(position == 1){
                    itemViewHolder.vDevider.setVisibility(View.GONE);
                }
                if(item.isCheckLike()){
                    itemViewHolder.imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_select));
                } else {
                    itemViewHolder.imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_def));
                }
                break;
            case FIRST:
                PostViewHolder postViewHolder = (PostViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullname;
        public PostViewHolder(View itemView) {
            super(itemView);
            tvFullname = itemView.findViewById(R.id.tv_fullname_post);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutLikeComment, layoutComment;
        ImageView imgLike;
        View vDevider;
        TextView tvComment, tvCreate;
       public ItemViewHolder(final View itemView) {
            super(itemView);
            layoutLikeComment = itemView.findViewById(R.id.layout_like_cmt);
            layoutComment = itemView.findViewById(R.id.layout_comment);
            imgLike = itemView.findViewById(R.id.img_like_cmt);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvCreate = itemView.findViewById(R.id.tv_date_create);
            vDevider = itemView.findViewById(R.id.view_divider_top);
            layoutLikeComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mData.get(getLayoutPosition()).isCheckLike()){
                        imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_def));
                        mData.get(getLayoutPosition()).setCheckLike(false);
                    } else {
                        imgLike.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_like_cmt_select));
                        mData.get(getLayoutPosition()).setCheckLike(true);                    }
                }
            });


        }
    }
    public interface IOnItemClickedListener {
        void onItemClick(int id);
    }
}
