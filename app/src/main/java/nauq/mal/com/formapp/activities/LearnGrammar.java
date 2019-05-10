package nauq.mal.com.formapp.activities;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.ListGrammarAdapter;
import nauq.mal.com.formapp.adapters.ListVocabularyAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetGrammarOutput;
import nauq.mal.com.formapp.api.models.GetWordsOutput;
import nauq.mal.com.formapp.models.Grammar;
import nauq.mal.com.formapp.models.Words;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.BookmarkTask;
import nauq.mal.com.formapp.tasks.DeleteBookmarkTask;
import nauq.mal.com.formapp.tasks.GetGrammarTask;
import nauq.mal.com.formapp.tasks.GetWordsTask;
import nauq.mal.com.formapp.utils.Constants;

public class LearnGrammar extends BaseActivity implements View.OnClickListener, ApiListener {
    private ImageView imgBack;
    private TextView tvQuiz;
    private TextView tvStudy;
    private TextView tvAddAlarm;
    private TextView tvNumWord;
    private ListGrammarAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<Grammar> mData = new ArrayList<>();
    private RecyclerView rcListVoca;
    private GLSurfaceView surfaceView;
    private String idNeedGet;
    private String title;
    private String type;
    private ConstraintLayout layoutTopVoca;
    private ConstraintLayout layoutTopGrammar;
    @Override
    protected int initLayout() {
        return R.layout.activity_learn_vocabulary;
    }

    @Override
    protected void initComponents() {
        Intent i = getIntent();
        idNeedGet = (String) i.getSerializableExtra(Constants.id_CHILD_VOCA);
        setTitle(title = (String) i.getSerializableExtra("title"));
        setTitle(type = (String) i.getSerializableExtra("type"));
        layoutTopGrammar = findViewById(R.id.layout_top_grammar);
        layoutTopVoca = findViewById(R.id.layout_top_voca);
        if(type.equals("vocabulary")){
            layoutTopVoca.setVisibility(View.VISIBLE);
            layoutTopGrammar.setVisibility(View.GONE);
        } else if(type.equals("grammar")){
            layoutTopVoca.setVisibility(View.GONE);
            layoutTopGrammar.setVisibility(View.VISIBLE);
        }
        imgBack = findViewById(R.id.imv_nav_left);
        tvAddAlarm = findViewById(R.id.tv_add_alarm);
        tvQuiz = findViewById(R.id.tv_quiz);
        tvNumWord = findViewById(R.id.tv_num_words);
        tvStudy = findViewById(R.id.tv_study);
        rcListVoca = findViewById(R.id.rc_list_voca);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rcListVoca.setLayoutManager(mLinearLayoutManager);
        mAdapter = new ListGrammarAdapter(this, mData);
        rcListVoca.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        showLoading(true);
        new GetGrammarTask(this, idNeedGet, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void addListener() {
        imgBack.setOnClickListener(this);
        tvQuiz.setOnClickListener(this);
        tvAddAlarm.setOnClickListener(this);
        tvStudy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.tv_quiz:
                Intent i1 = new Intent(LearnGrammar.this, DoPracticeActivity.class);
                i1.putExtra(Constants.id_CHILD_VOCA, idNeedGet);
                i1.putExtra("title", title);
                startActivity(i1);
                break;
            case R.id.tv_add_alarm:
                break;
            case R.id.tv_study:
                Intent i = new Intent(this, StudySingleGrammar.class);
                i.putExtra("array", mData);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if(task instanceof GetGrammarTask){
            showLoading(false);
            GetGrammarOutput output = (GetGrammarOutput) data;
            if(output.success){
                tvNumWord.setText(output.grammars.size() + " grammars");
                mData.addAll(output.grammars);
                mAdapter.notifyDataSetChanged();
            } else {
                showAlert(R.string.err_unexpected_exception);
            }

        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
}