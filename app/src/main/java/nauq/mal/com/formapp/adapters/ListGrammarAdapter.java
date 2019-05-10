package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Grammar;
import nauq.mal.com.formapp.models.Words;

public class ListGrammarAdapter extends RecyclerView.Adapter<ListGrammarAdapter.ViewHolder> {
    private Context mContext;
    private List<Grammar> mData;
    private IOnItemClickedListener mIOnItemClickedListener;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }
    public ListGrammarAdapter(Context mContext, List<Grammar> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_learn_grammar, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Grammar item = mData.get(position);
        if(item.getName() != null){
            holder.tvName.setText(item.getName());
        }
        if(item.getForm() != null){
            holder.tvForm.setText(item.getForm());
        }
//        if(item.getSpell() != null){
//            holder.tvSpell.setText(item.getSpell());
//        } else {
//            holder.tvSpell.setText("No spell");
//        }
//        if(item.getMean() != null){
//            holder.tvMean.setText(item.getMean());
//        }
//        if (item.isBookmark()) {
//            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_slted));
//        } else {
//            holder.imgBookmark.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bookmark_deff));
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvForm;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvForm = itemView.findViewById(R.id.tvForm);


        }
    }
    public interface IOnItemClickedListener {
        void onItemBookmark(String id);
        void onItemDeleteBookmark(String id);
    }
}
