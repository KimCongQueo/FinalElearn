package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Categories;
import nauq.mal.com.formapp.models.TopicItems;

public class ChooseTopicVocabularyAdapter extends RecyclerView.Adapter<ChooseTopicVocabularyAdapter.ViewHolder>
{
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<Categories> mData;
    private Boolean isPractice = false;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public ChooseTopicVocabularyAdapter(Context context, List<Categories> TopicItems) {
        this.mContext = context;
        this.mData = TopicItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_topic, parent, false));
    }

    public void setIsPractice(boolean isPractice){
        this.isPractice = isPractice;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Categories item = mData.get(position);
        holder.tvTopic.setText(item.getName());
        if(isPractice){
            holder.checkBox.setVisibility(View.VISIBLE);
            if(item.getChecked()){
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic;
        LinearLayout layoutTopic;
        CheckBox checkBox;
        public ViewHolder(final View itemView) {
            super(itemView);
            tvTopic = itemView.findViewById(R.id.tv_topic);
            layoutTopic = itemView.findViewById(R.id.layout_topic);
            checkBox = itemView.findViewById(R.id.cb_item);
            layoutTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getId(), mData.get(getLayoutPosition()).getName());
                    }
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        void onItemClick(String id, String title);
    }

}
