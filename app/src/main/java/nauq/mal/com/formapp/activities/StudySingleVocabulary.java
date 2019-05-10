package nauq.mal.com.formapp.activities;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.StudySingleVocaAdapter;
import nauq.mal.com.formapp.models.Words;

public class StudySingleVocabulary extends BaseActivity implements View.OnClickListener {
    private ImageView imgBack;
    private RecyclerView rcSingleVoca;
    private LinearLayoutManager linearLayoutManager;
    private SnapHelper snapHelper;
    private StudySingleVocaAdapter mAdapter;
    private TextToSpeech tts;
    private ArrayList<Words> mData = new ArrayList<>();
    @Override
    protected int initLayout() {
        return R.layout.activity_study_single_vocabulary;
    }

    @Override
    protected void initComponents() {
        Intent i = getIntent();
        mData = (ArrayList<Words>) i.getSerializableExtra("array");
        imgBack = findViewById(R.id.imv_nav_left);
        rcSingleVoca = findViewById(R.id.rc_single_voca);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rcSingleVoca.setLayoutManager(linearLayoutManager);
        mAdapter = new StudySingleVocaAdapter(this, mData);
        rcSingleVoca.setAdapter(mAdapter);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
            }
        });
        loadData();

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rcSingleVoca);
    }

    private void loadData() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void addListener() {
        imgBack.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new StudySingleVocaAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(String name) {
                tts.speak(name, TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_nav_left:
                finish();
                break;
        }
    }
}
