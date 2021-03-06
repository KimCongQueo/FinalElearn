package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Answer;
import nauq.mal.com.formapp.models.Question;

public class QuestionAdapter extends  RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private Context mContext;
    private IOnItemClickedListener mIOnItemClickedListener;
    private List<Question> mData;

    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }

    public QuestionAdapter(Context context, List<Question> Question) {
        this.mContext = context;
        this.mData = Question;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false));
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.ViewHolder holder, final int position) {
            Question item = mData.get(position);
            holder.mAdapter = new AnswerAdapter(mContext, item.getArrAns());
            holder.rcAns.setAdapter(holder.mAdapter);
            holder.mAdapter.setOnItemClickListener(new AnswerAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(int id) {
                    holder.mAdapter.notifyDataSetChanged();
            }
        });
            if(item.getQuestion() != ""){
                holder.tvQuestion.setText(item.getQuestion());
            }
            if(item.getImage()!= null) {
                Picasso.with(mContext).load("" + item.getImage()).into(holder.imgQues);
            }
    }
    public ArrayList<Boolean> getmAnsChoose(){
        ArrayList<Boolean> mAnsChoose = new ArrayList<Boolean>(mData.size());
        for(int i=0;i<mData.size();i++){
            boolean temp = false;
            for (int j=0;j<mData.get(i).getArrAns().size();j++){
               if(mData.get(i).getArrAns().get(j).isSelected()){
                   temp = true;
                   break;
               }
            }
            if(temp){
                mAnsChoose.add(true);
            }
            else mAnsChoose.add(false);
        }
        return mAnsChoose;
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        ImageView imgQues;
        RecyclerView rcAns;
        AnswerAdapter mAdapter;
        ArrayList<Answer> mDataString = new ArrayList<>();
        private LinearLayoutManager mLinearLayoutManager;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            imgQues = itemView.findViewById(R.id.img_question);
            rcAns = itemView.findViewById(R.id.rc_answer);
            mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false);
            mLinearLayoutManager.setAutoMeasureEnabled(true);
            rcAns.setLayoutManager(mLinearLayoutManager);
//

        }
    }
    public interface IOnItemClickedListener {
        void onItemClick(int id);
    }
}
