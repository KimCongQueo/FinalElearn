package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Answer;
import nauq.mal.com.formapp.models.Question;

public class AnswerAdapter extends  RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private Question question;
    private List<Answer> mData;
    private boolean isEnd;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }
    public AnswerAdapter(Context context, List<Answer> question) {
        this.mContext = context;
        this.mData = question;
        this.isEnd = false;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer, parent, false));
    }

    @Override
    public void onBindViewHolder(AnswerAdapter.ViewHolder holder, final int position) {
            Answer item = mData.get(position);
            holder.tvNumAns.setText((position+1) + "");
            holder.tvAns.setText(item.getAnswer());
            if(item.isSelected()){
                holder.layoutNum.setSelected(true);
                holder.tvNumAns.setSelected(true);
                holder.tvAns.setSelected(true);
                item.setSelected(true);
            } else {
                holder.layoutNum.setSelected(false);
                holder.tvNumAns.setSelected(false);
                holder.tvAns.setSelected(false);
                item.setSelected(false);
            }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public boolean isChoose(){
        for(int i=0;i<mData.size();i++){
            if(mData.get(i).isSelected()){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAns, tvNumAns;
        LinearLayout layoutAns, layoutNum;
        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvAns = itemView.findViewById(R.id.tv_answer);
            tvNumAns = itemView.findViewById(R.id.tv_numAns);
            layoutNum = itemView.findViewById(R.id.layout_num);
            layoutAns = itemView.findViewById(R.id.layout_ans);
            layoutAns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mData.get(getLayoutPosition()).isSelected()){
                        layoutNum.setSelected(false);
                        tvNumAns.setSelected(false);
                        tvAns.setSelected(false);
                        mData.get(getLayoutPosition()).setSelected(false);
                    } else {
                        layoutNum.setSelected(true);
                        tvNumAns.setSelected(true);
                        tvAns.setSelected(true);
                        mData.get(getLayoutPosition()).setSelected(true);
                    }
                }
            });

        }
    }
    public interface IOnItemClickedListener {
        void onItemClick(int id);
    }
}
