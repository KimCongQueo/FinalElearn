package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Words;

public class ListVocabularyAdapter extends RecyclerView.Adapter<ListVocabularyAdapter.ViewHolder> {
    private Context mContext;
    private List<Words> mData;
    private IOnItemClickedListener mIOnItemClickedListener;
    private String type;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }
    public ListVocabularyAdapter(Context mContext, List<Words> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_learn_voca, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Words item = mData.get(position);
        if(item.getName() != null){
            holder.tvWords.setText(item.getName());
        }
        if(item.getSpell() != null){
            holder.tvSpell.setText(item.getSpell());
        } else {
            holder.tvSpell.setText("No spell");
        }
        if(item.getMean() != null){
            holder.tvMean.setText(item.getMean());
        }
        if (item.isBookmark()) {
            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
        } else {
            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWords;
        TextView tvSpell;
        TextView tvMean;
        ImageView imgBookmark;
        ConstraintLayout layoutBookmark;
        LinearLayout layoutRoot;
        public ViewHolder(View itemView) {
            super(itemView);
            tvWords = itemView.findViewById(R.id.tvWords);
            tvSpell = itemView.findViewById(R.id.tvSpell);
            tvMean = itemView.findViewById(R.id.tvMeaning);
            imgBookmark = itemView.findViewById(R.id.img_bookmark);
            layoutRoot = itemView.findViewById(R.id.layout_topic);
            layoutBookmark = itemView.findViewById(R.id.layout_bookmark);
            layoutBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(getLayoutPosition()).isBookmark()) {
                        mData.get(getLayoutPosition()).setBookmark(false);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemDeleteBookmark(mData.get(getLayoutPosition()).getId(), getLayoutPosition());
                        }
                    } else {
                        mData.get(getLayoutPosition()).setBookmark(true);
                        imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
                        if (mIOnItemClickedListener != null) {
                            mIOnItemClickedListener.onItemBookmark(mData.get(getLayoutPosition()).getId());
                        }
                    }
                }
            });
            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getId());
                        mIOnItemClickedListener.onItemClickWord(mData.get(getLayoutPosition()));
                    }
                }
            });


        }
    }
    public interface IOnItemClickedListener {
        void onItemBookmark(String id);
        void onItemClick(String id);
        void onItemClickWord(Words wordsClick);
        void onItemDeleteBookmark(String id, int pos);
    }
}
