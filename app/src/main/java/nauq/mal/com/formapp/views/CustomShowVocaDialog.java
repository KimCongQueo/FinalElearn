package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.models.Words;

public class CustomShowVocaDialog extends Dialog implements View.OnClickListener {
    private TextView tvName;
    private TextView tvSpell;
    private TextView tvMean;
    private TextView tvUsage;
    private TextView tvExample;
    private ImageView btnPlay;
    private RelativeLayout layoutRoot;
    private Words item;
    private Context mContext;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cardview_front_voca);
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setAttributes(wlp);

        init();
        addListener();
    }

    public CustomShowVocaDialog(@NonNull Context context, Words item) {
        super(context);
        this.item = item;
        this.mContext = context;
    }

    private void init() {
        tvExample = findViewById(R.id.tv_exx);
        tvMean = findViewById(R.id.tvMean);
        tvName = findViewById(R.id.tv_word);
        tvSpell = findViewById(R.id.tv_spell);
        tvUsage = findViewById(R.id.tvUsage);
        layoutRoot = findViewById(R.id.layout_mean);
        layoutRoot.setVisibility(View.VISIBLE);
        btnPlay = findViewById(R.id.btn_play_front);

        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });
        if(item.getName() != null){
            tvName.setText(item.getName());
        }
        if(item.getSpell() != null){
            tvSpell.setText(item.getSpell());
        } else {
            tvSpell.setText("");
        }
        if(item.getMean() != null){
            tvMean.setText(item.getMean());
        } else {
            tvMean.setText("No result");
        }
        if(item.getUsage() != null){
            tvUsage.setText(item.getUsage());
        } else {
            tvUsage.setText("No result");
        }
        if(item.getExample() != null){
            tvExample.setText(item.getExample());
        } else {
            tvExample.setText("No result");
        }

    }

    private void addListener() {
        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_front:
                tts.speak(item.getName(), TextToSpeech.QUEUE_ADD, null);
                break;
        }
    }

}