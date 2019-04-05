package nauq.mal.com.formapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Answer;
import nauq.mal.com.formapp.models.Question;

public class BottomSheetPracticeAdapter extends RecyclerView.Adapter<BottomSheetPracticeAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> mData;
    private IOnItemClickedListener mIOnItemClickedListener;
    private int index = -1;
    private boolean[] resultOverview;
    private boolean showResultOverview;
    private ArrayList<Boolean> mAnsChoose = new ArrayList<>();
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public BottomSheetPracticeAdapter(Context context, List<Question> Question) {
        this.mContext = context;
        this.mData = Question;
        for (int i=0;i<mData.size();i++){
            mAnsChoose.add(false);
        }
    }
    public void setSeclectIndex(int index) {
        this.index = index;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BottomSheetPracticeAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rc_ans, parent, false));
    }

    @Override
    public void onBindViewHolder(BottomSheetPracticeAdapter.ViewHolder holder, int position) {
        holder.tvNumQues.setText((position+1) +"" );
        int selectColor = ContextCompat.getColor(mContext, R.color.text_color);
        if (position + 1 == index) {
            holder.layoutNumQues.setBackgroundResource(R.drawable.answer_item_bottom_active);
            holder.tvNumQues.setTextColor(Color.WHITE);
        } else if (mAnsChoose.get(position)) {
            holder.layoutNumQues.setBackgroundResource(R.drawable.sl_item_ans);
            holder.layoutNumQues.setSelected(true);
            holder.tvNumQues.setTextColor(selectColor);
        } else {
            holder.layoutNumQues.setBackgroundResource(R.drawable.sl_item_ans);
            holder.layoutNumQues.setSelected(false);
            holder.tvNumQues.setTextColor(selectColor);

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public  void updateAnswerTable(ArrayList<Boolean> mAnsChoose){
        this.mAnsChoose = mAnsChoose;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutNumQues;
        TextView tvNumQues;
        ImageView imgWrong, imgTrue;
        public ViewHolder(View itemView) {
            super(itemView);
            layoutNumQues = itemView.findViewById(R.id.layout_num_ques);
            tvNumQues = itemView.findViewById(R.id.tv_num_ques);
            imgTrue = itemView.findViewById(R.id.img_ans);
            imgWrong = itemView.findViewById(R.id.img_ans_warning);
            layoutNumQues.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }
    public interface IOnItemClickedListener {
        void onItemClick(int position);
    }
}
