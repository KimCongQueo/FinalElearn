package nauq.mal.com.formapp.adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.Words;
import nauq.mal.com.formapp.views.EasyFlipView;

public class StudySingleVocaAdapter extends RecyclerView.Adapter<StudySingleVocaAdapter.ViewHolder> {
    private Context mContext;
    private List<Words> mData;
    private IOnItemClickedListener mIOnItemClickedListener;
    public void setOnItemClickListener(IOnItemClickedListener listener) {
        mIOnItemClickedListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_study_single_voca, parent, false));
    }

    public StudySingleVocaAdapter(Context mContext, List<Words> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    void setClickListener(IOnItemClickedListener itemClickListener) {
        this.mIOnItemClickedListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Words item = mData.get(position);
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
        if(item.getMean() != null){
            holder.tvMean.setText(item.getMean());
        }
        if(item.getSpell() != null){
            holder.tvSpell.setText(item.getSpell());
        } else {
            holder.tvSpell.setText("");
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
            tvMean = itemView.findViewById(R.id.tv_meaning);
            tvWordBack = itemView.findViewById(R.id.tv_words);
            btnPlay = itemView.findViewById(R.id.btn_play);
            btnPlayFront = itemView.findViewById(R.id.btn_play_front);
            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getName());
                    }
                }
            });
            btnPlayFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIOnItemClickedListener != null) {
                        mIOnItemClickedListener.onItemClick(mData.get(getLayoutPosition()).getName());
                    }
                }
            });
        }

    }

    public interface IOnItemClickedListener {
        void onItemClick(String name);
    }
}
