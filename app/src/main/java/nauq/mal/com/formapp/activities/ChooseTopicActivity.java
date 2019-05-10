package nauq.mal.com.formapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.MenuChooseTopicVocaAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.api.models.GetTopicOutput;
import nauq.mal.com.formapp.models.TopicItem;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetCategoriesTask;
import nauq.mal.com.formapp.tasks.GetChildrenCategoriesTask;
import nauq.mal.com.formapp.tasks.GetTopicGrammarTask;
import nauq.mal.com.formapp.tasks.GetTopicTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.views.CustomChooseOptionDialog;

public class ChooseTopicActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    private String linkVoca = Constants.API_URL + "getTopicVocavulary.php/";
    private String linkGrammar = Constants.API_URL + "getTopicGrammar.php/";
    private String linkPractice = Constants.API_URL + "getTopicPractice.php/";
    private TextView tvOptions;
    private boolean isLoading;
    private int mStart = 0;
    private boolean mIsLoadMore;
    private ImageView imvArrow;
    private RecyclerView rcTopic;
    private ArrayList<TopicItem> mData = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private MenuChooseTopicVocaAdapter mAdapter;
    private CheckBox cbAll;
    private Button btnAction;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private String jsonString;
    private String jsonStringGrammar;
    private int totalItemCount;
    private ImageView imgBack;
    private int dialogShow = -1;
    private final int intVoca = 1;
    private final int intPrac = 3;
    private final int intGram = 2;
    private int mCurrentInt = 1;
    private String idParent = "";
    private CustomChooseOptionDialog dialogg;

    @Override
    protected int initLayout() {
        return R.layout.activity_choose_topic;
    }

    @Override
    protected void initComponents() {
        Intent i = getIntent();
        idParent = (String) i.getSerializableExtra(Constants.ID_CATEGORY);
        imgBack = findViewById(R.id.imv_nav_left);
        btnAction = findViewById(R.id.btn_action);
        cbAll = findViewById(R.id.cb_select_all);
        tvOptions = findViewById(R.id.tv_options);
        tvOptions.setVisibility(View.VISIBLE);
        imvArrow = findViewById(R.id.imv_arrow_down);
        imvArrow.setVisibility(View.VISIBLE);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.GONE);
        rcTopic = findViewById(R.id.rc_topic);
        rcTopic.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MenuChooseTopicVocaAdapter(this, mData);
        mAdapter.setmCurrentInt(intVoca);
        rcTopic.setAdapter(mAdapter);
        loadData(mCurrentInt);
    }

    private void loadData(int i) {
        showLoading(true);
        mAdapter.setmCurrentInt(intVoca);
        new GetChildrenCategoriesTask(this, idParent, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadNewData() {
        new GetTopicGrammarTask(this, jsonStringGrammar, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void addListener() {
        imgBack.setOnClickListener(this);
        tvOptions.setOnClickListener(this);
        imvArrow.setOnClickListener(this);
        btnAction.setOnClickListener(this);

        rcTopic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (mData.size() > 0 && mIsLoadMore && !isLoading) {
                            showLoading(true);
                            mStart++;
//                            loadData();
                        }
                    }
                }
            }
        });
        mAdapter.setOnItemClickListener(new MenuChooseTopicVocaAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(int id) {
//                Toast.makeText(LearnTopicActivity2.class, ""+id, Toast.LENGTH_LONG).show();
            }
        });
        cbAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_options:
                //khonong break để dùng chung function với arrow down
            case R.id.imv_arrow_down:
                dialogg = new CustomChooseOptionDialog(ChooseTopicActivity.this);
                dialogg.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//                dialogShow = 2;
                dialogg.show();
                dialogg.setmIOnItemClickedListener(new CustomChooseOptionDialog.IOnItemClickedListener() {
                    @Override
                    public void onItemClick(int type) {
                        if (type == intGram) {
                            mCurrentInt = intGram;
                            btnAction.setText(getString(R.string.txt_learn_grammar));
                            tvOptions.setText(getString(R.string.txt_learn_grammar));
                            new GetTopicGrammarTask(ChooseTopicActivity.this, jsonStringGrammar, ChooseTopicActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else if (type == intPrac) {
                            mCurrentInt = intPrac;
                            btnAction.setText(getString(R.string.txt_do_practice));
                            tvOptions.setText(getString(R.string.txt_do_practice));
                        } else if (type == intVoca) {
                            mCurrentInt = intVoca;
                            btnAction.setText(getString(R.string.txt_learn_voca));
                            tvOptions.setText(getString(R.string.txt_learn_voca));
                            new GetTopicTask(ChooseTopicActivity.this, jsonString, ChooseTopicActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    }
                });

                break;
            case R.id.cb_select_all:
                if (cbAll.isChecked()) {
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setChecked(true);
                    }
                } else {
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setChecked(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_action:
//                test get id item choose
                switch (mCurrentInt) {
                    case intVoca:
                        Intent i = new Intent(this, LearnVocabulary.class);
                        startActivity(i);
                        break;
                    case intGram:
                        break;
                    case intPrac:
                        String tmp = "";
                        for (int j = 0; j < mData.size(); j++) {
                            if (mData.get(j).getChecked()) {
                                tmp = tmp + " " + mData.get(j).toString();
                            }
                        }
                        Toast.makeText(this, tmp, Toast.LENGTH_LONG).show();
                        Intent iii = new Intent(this, DoPracticeActivity.class);
                        startActivity(iii);
                        break;
                }
                break;
            case R.id.imv_nav_left:
                finish();
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetTopicTask) {
            showLoading(false);
            GetTopicOutput output = (GetTopicOutput) data;
            if (output != null) {
                if (output.success) {
                    mData.clear();
                    for (TopicItem tmp : output.result) {
                        mData.add(new TopicItem(tmp.getId(), tmp.getTopic(), tmp.getImage(), intVoca));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                showAlert(getString(R.string.err_network_available));
            }
        } else if (task instanceof GetTopicGrammarTask) {
            GetTopicOutput output = (GetTopicOutput) data;
            if (output != null) {
                if (output.success) {
//                    mData = new ArrayList<>();
                    mData.clear();
                    for (TopicItem tmp : output.result) {
                        mData.add(new TopicItem(tmp.getId(), tmp.getTopic(), intGram));
                    }
                    showLoading(false);
//                    mAdapter = new MenuChooseTopicVocaAdapter(this, mData, intGram);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                showAlert(getString(R.string.err_network_available));
            }
        }
        if (task instanceof GetChildrenCategoriesTask) {
            GetCategoriesOutput output = (GetCategoriesOutput) data;
            showLoading(false);
            if (output.success) {
                for (int i = 0; i < output.children.size(); i++) {
                    if (output.children.get(i).getName().toLowerCase().contains("tu_vung")) {
                        new GetTopicTask(this, output.children.get(i).getId(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        return;
                    }
                }
            } else {
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if(task instanceof GetTopicTask){
            GetCategoriesOutput output = (GetCategoriesOutput) data;
            if(output.success){
                
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }


}
