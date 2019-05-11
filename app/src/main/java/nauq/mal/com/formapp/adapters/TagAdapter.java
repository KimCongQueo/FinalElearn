package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Tags;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<String> mData;
    private List<Tags> tags;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public TagAdapter(Context context, List<String> String, List<Tags> tags) {
        this.mContext = context;
        this.mData = String;
        this.tags = tags;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        if (item != null && item != "") {
            for (int i = 0; i < tags.size(); i++) {
                if(item.equals(tags.get(i).getId())){
                    holder.tvTag.setText(tags.get(i).getName());
                    break;
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTag;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tv_tag);

        }

    }

    public interface IOnItemClickedListener {
        void onItemClick(int pos);
    }

}
