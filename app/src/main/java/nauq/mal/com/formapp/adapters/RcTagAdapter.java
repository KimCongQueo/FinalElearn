package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Bookmark;
import nauq.mal.com.formapp.models.Tags;
import nauq.mal.com.formapp.utils.Constants;

public class RcTagAdapter extends RecyclerView.Adapter<RcTagAdapter.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<Tags> mData;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public RcTagAdapter(Context context, List<Tags> Tags) {
        this.mContext = context;
        this.mData = Tags;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check_tag, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tags item = mData.get(position);
        if (item != null) {
            holder.tvtag.setText(item.getName());
        }
        if(item.isChecked()){
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
        TextView tvtag;
        CheckBox checkBox;

        public ViewHolder(final View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvtag = itemView.findViewById(R.id.tv_tag);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData.get(getLayoutPosition()).isChecked()) {
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
        void onItemClick(String id);

        void onItemClickBookmark(int pos, String id);
    }

}
