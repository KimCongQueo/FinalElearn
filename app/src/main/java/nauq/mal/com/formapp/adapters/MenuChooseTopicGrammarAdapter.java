package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.TopicItem;

public class MenuChooseTopicGrammarAdapter extends RecyclerView.Adapter<MenuChooseTopicGrammarAdapter.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<TopicItem> mData;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public MenuChooseTopicGrammarAdapter(Context context, List<TopicItem> TopicVocaItem) {
        this.mContext = context;
        this.mData = TopicVocaItem;
    }
    public MenuChooseTopicGrammarAdapter(Context context, List<TopicItem> TopicVocaItem, int mCurrentInt) {
        this.mContext = context;
        this.mData = TopicVocaItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuChooseTopicGrammarAdapter.ViewHolder holder, int position) {
        TopicItem item = mData.get(position);
        holder.tvTopicGrammar.setVisibility(View.VISIBLE);
        holder.tvNumWords.setVisibility(View.GONE);
        holder.tvTopic.setVisibility(View.GONE);
        if (item.getTopic() != "") {
            holder.tvTopicGrammar.setText(item.getTopic());
        }
        if(item.getImage()!= null) {
            Picasso.with(mContext).load("" + item.getImage()).into(holder.imgLeft);
        }
//        holder.imgLeft.setImageResource(R.drawable.ic_practice);
        if (item.getChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLeft;
        TextView tvTopic;
        CheckBox checkBox;
        LinearLayout layoutTopic;
        TextView tvNumWords;
        TextView tvTopicGrammar;
        public ViewHolder(View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.imv_topic);
            tvTopic = itemView.findViewById(R.id.tv_topic);
            checkBox = itemView.findViewById(R.id.cb_item);
            tvTopicGrammar = itemView.findViewById(R.id.tv_topic_grammar);
            layoutTopic = itemView.findViewById(R.id.layout_topic);
            layoutTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mData.get(getLayoutPosition()).getChecked()) {
                        mData.get(getLayoutPosition()).setChecked(false);
                        checkBox.setChecked(false);
                    } else {
                        mData.get(getLayoutPosition()).setChecked(true);
                        checkBox.setChecked(true);
                    }
                }
            });
        }
    }

    public interface IOnItemClickedListener {
        void onItemClick(int id);
    }
}
