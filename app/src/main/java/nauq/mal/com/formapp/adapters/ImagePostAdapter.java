package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nauq.mal.com.formapp.R;

public class ImagePostAdapter extends RecyclerView.Adapter<ImagePostAdapter.ViewHolder>
{
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<String> mData;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public ImagePostAdapter(Context context, List<String> String) {
        this.mContext = context;
        this.mData = String;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img, parent, false));
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        if(item != null && !item.equals("")){
            Picasso.with(mContext).load(item).into(holder.imgAttached);

        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAttached;
        ImageView imgDelete;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgAttached = itemView.findViewById(R.id.imgAttached);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClickShow(getLayoutPosition());
                    }
                }
            });

        }
    }

    public interface IOnItemClickedListener {
        void onItemClickShow(int id);
    }

}
