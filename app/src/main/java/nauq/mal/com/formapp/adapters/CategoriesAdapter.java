package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Categories;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>
{
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<Categories> mData;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public CategoriesAdapter(Context context, List<Categories> String) {
        this.mContext = context;
        this.mData = String;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Categories item = mData.get(position);
        holder.tvCate.setText(item.getName());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCate;
        LinearLayout layoutCate;
        public ViewHolder(final View itemView) {
            super(itemView);
            tvCate = itemView.findViewById(R.id.tv_name_cate);
            layoutCate = itemView.findViewById(R.id.layout_cate);
            layoutCate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getId());
                    }
                }
            });
        }
    }

    public interface IOnItemClickedListener {
        void onItemClick(String id);
    }

}
