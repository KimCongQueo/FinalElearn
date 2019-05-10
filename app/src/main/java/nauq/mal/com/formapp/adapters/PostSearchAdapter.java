package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Bookmark;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.utils.Constants;

public class PostSearchAdapter extends RecyclerView.Adapter<PostSearchAdapter.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<PostItem> mData;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public PostSearchAdapter(Context context, List<PostItem> PostItem) {
        this.mContext = context;
        this.mData = PostItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookmark, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PostItem item = mData.get(position);
        if (position == mData.size() - 1) {
            holder.dot.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
        } else {
            holder.dot.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        }
        if (item != null) {
            holder.tvName.setText(item.getUserPost().getName());
            holder.tvContent.setText(item.getContent());
        }
        if (item.getImgs() != null && item.getImgs().size() > 0) {
            Picasso.with(mContext).load(Constants.LINK_IMG + item.getImgs().get(0))
                    .placeholder(R.drawable.ic_user_def).error(R.drawable.ic_user_def).into(holder.imgAva);
        } else {
            Picasso.with(mContext).load(R.drawable.ic_user_def).placeholder(R.drawable.ic_user_def)
                    .error(R.drawable.ic_user_def).into(holder.imgAva);
        }
        holder.imgBookmark.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dot;
        ImageView imgAva;
        ImageView imgBookmark;
        View divider;
        TextView tvName;
        TextView tvContent;
        ConstraintLayout layoutRoot;

        public ViewHolder(final View itemView) {
            super(itemView);
            dot = itemView.findViewById(R.id.dot);
            layoutRoot = itemView.findViewById(R.id.layout_root);
            imgAva = itemView.findViewById(R.id.img_ava);
            imgBookmark = itemView.findViewById(R.id.img_bookmark);
            divider = itemView.findViewById(R.id.view);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getIdPost());
                    }
                }
            });
        }

    }

    public interface IOnItemClickedListener {
        void onItemClick(String id);

        void onItemClickBookmark(int pos, String id);
    }

}
