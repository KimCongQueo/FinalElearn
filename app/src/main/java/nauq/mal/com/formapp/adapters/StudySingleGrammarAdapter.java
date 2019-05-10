package nauq.mal.com.formapp.adapters;

import android.content.Context;
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
import nauq.mal.com.formapp.views.EasyFlipView;

public class StudySingleGrammarAdapter extends RecyclerView.Adapter<StudySingleGrammarAdapter.ViewHolder> {
    private Context mContext;
    private List<Grammar> mData;
    private IOnItemClickedListener mIOnItemClickedListener;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_study_single_voca, parent, false));
    }

    public StudySingleGrammarAdapter(Context mContext, List<Grammar> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    void setClickListener(IOnItemClickedListener itemClickListener) {
        this.mIOnItemClickedListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Grammar item = mData.get(position);
        if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE
                && item.isFlip()) {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();
        } else if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE
                && !item.isFlip()) {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();
        }
        holder.flipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isFlip()) {
                    item.setFlip(false);
                } else {
                    item.setFlip(true);
                }
                holder.flipView.setFlipDuration(1000);
                holder.flipView.flipTheView();
            }
        });
        if(item.getName() != null){
            holder.tvWord.setText(item.getName());
            holder.tvWordBack.setText(item.getName());
        }
        if(item.getForm()!=null){
            holder.tvSpell.setText(item.getForm());
        }
        holder.btnPlay.setVisibility(View.INVISIBLE);
        holder.btnPlayFront.setVisibility(View.INVISIBLE);
        holder.tvUsage.setVisibility(View.VISIBLE);
        holder.tvExx.setVisibility(View.VISIBLE);
        holder.tvExample.setVisibility(View.VISIBLE);
        if(item.getUsage()!=null){
            holder.tvMean.setText(item.getUsage());
            holder.tvMean.setTextSize(13);
        }
        if(item.getExample()!=null){
            holder.tvExx.setText(item.getExample());
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EasyFlipView flipView;
        TextView tvWord;
        TextView tvSpell;
        TextView tvUsage;
        TextView tvExx;
        TextView tvExample;
        TextView tvWordBack;
        TextView tvMean;
        ImageView btnPlay;
        ImageView btnPlayFront;
        public ViewHolder(View itemView) {
            super(itemView);
            flipView = itemView.findViewById(R.id.flip);
            flipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            tvSpell = itemView.findViewById(R.id.tv_spell);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvExample = itemView.findViewById(R.id.tv_example);
            tvExx = itemView.findViewById(R.id.tv_exx);
            tvUsage = itemView.findViewById(R.id.tv_usage);
            tvMean = itemView.findViewById(R.id.tv_meaning);
            tvWordBack = itemView.findViewById(R.id.tv_words);
            btnPlay = itemView.findViewById(R.id.btn_play);
            btnPlayFront = itemView.findViewById(R.id.btn_play_front);

        }

    }

    public interface IOnItemClickedListener {
        void onItemClick(String name);
    }
}
