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

public class MenuChooseTopicVocaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<TopicItem> mData;
    private int mCurrentInt = -1;
    public static final int vocabulary = 1;
    public static final int grammar = 2;
    public static final int practice = 3;
    private RecyclerView.ViewHolder holder;
    private int position;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public MenuChooseTopicVocaAdapter(Context context, List<TopicItem> TopicVocaItem) {
        this.mContext = context;
        this.mData = TopicVocaItem;
    }
    public MenuChooseTopicVocaAdapter(Context context, List<TopicItem> TopicVocaItem, int mCurrentInt) {
        this.mContext = context;
        this.mData = TopicVocaItem;
        this.mCurrentInt = mCurrentInt;
    }
  public void setmCurrentInt(int mCurrentInt){
        this.mCurrentInt = mCurrentInt;
  }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        switch (viewType){
            case vocabulary:
                View itemView1 = li.inflate(R.layout.item_list_topic, parent, false);
                return new VocaViewHolder(itemView1);
            case grammar:
                View itemView0 = li.inflate(R.layout.item_list_topic_grammar, parent, false);
                return new GrammarViewHolder(itemView0);
            default:
                break;
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getType() == vocabulary){
            return vocabulary;
        } else if (mData.get(position).getType() == grammar){
            return grammar;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case vocabulary:
                VocaViewHolder vocaViewHolder = (VocaViewHolder) holder;
                TopicItem item = mData.get(position);
                if (item.getChecked()) {
                    vocaViewHolder.checkBox.setChecked(true);
                } else {
                    vocaViewHolder.checkBox.setChecked(false);
                }
                if (item.getTopic() != "") {
                    vocaViewHolder.tvTopic.setText(item.getTopic());
                }
                if(item.getImage()!= null) {
                    Picasso.with(mContext).load("" + item.getImage()).into(vocaViewHolder.imgLeft);
                }
                break;
            case grammar:
                GrammarViewHolder grammarViewHolder = (GrammarViewHolder) holder;
                TopicItem item1 = mData.get(position);
                if (item1.getChecked()) {
                    grammarViewHolder.checkBox.setChecked(true);
                } else {
                    grammarViewHolder.checkBox.setChecked(false);
                }
                if (item1.getTopic() != "") {
                    grammarViewHolder.tvTopicGrammar.setText(item1.getTopic());
                }
//                if(item1.getImage()!= "") {
//                    Picasso.with(mContext).load("" + item1.getImage()).into(grammarViewHolder.imgLeft);
//                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class VocaViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLeft;
        TextView tvTopic;
        CheckBox checkBox;
        LinearLayout layoutTopic;
        TextView tvNumWords;
        public VocaViewHolder(View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.imv_topic);
            tvTopic = itemView.findViewById(R.id.tv_topic);
            checkBox = itemView.findViewById(R.id.cb_item);
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
    public class GrammarViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLeft;
        CheckBox checkBox;
        LinearLayout layoutTopic;
        TextView tvTopicGrammar;
        public GrammarViewHolder(View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.imv_topic);
            tvTopicGrammar = itemView.findViewById(R.id.tv_topic_grammar);
            checkBox = itemView.findViewById(R.id.cb_item);
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
