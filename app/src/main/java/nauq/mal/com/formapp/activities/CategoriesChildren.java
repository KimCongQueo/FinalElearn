package nauq.mal.com.formapp.activities;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.ChooseTopicVocabularyAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.models.Categories;
import nauq.mal.com.formapp.models.TopicItems;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetChildrenCategoriesTask;
import nauq.mal.com.formapp.tasks.GetTopicGrammarTask;
import nauq.mal.com.formapp.tasks.GetTopicTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.views.CustomChooseOptionDialog;

public class CategoriesChildren extends BaseActivity implements View.OnClickListener, ApiListener {
    private ImageView imgBack;
    private ImageView imgSearch;
    private ImageView imgArrow;
    private TextView tvOptions;
    private TextView tvTitle;
    private RecyclerView rcTopic;
    private LinearLayout layoutPractice;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;
    private int mStart = 0;
    private boolean mIsLoadMore;
    private String idParent;
    private String idVoca;
    private String idGrammar;
    private ChooseTopicVocabularyAdapter mAdapter;
    private ArrayList<Categories> mDataTopic = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private CustomChooseOptionDialog dialogg;
    private final int intVoca = 1;
    private final int intPrac = 3;
    private final int intGram = 2;
    private int mCurrentInt = -1;
    private Button btnDoPractice;
    private CheckBox cbSelectAll;
    boolean gotVoca = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_categories_children;
    }

    @Override
    protected void initComponents() {
        Intent i = getIntent();
        imgSearch = findViewById(R.id.imv_nav_right2);
        imgSearch.setVisibility(View.INVISIBLE);
        imgArrow = findViewById(R.id.imv_arrow_down);
        imgArrow.setVisibility(View.VISIBLE);
        tvOptions = findViewById(R.id.tv_options);
        tvOptions.setVisibility(View.VISIBLE);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.GONE);
        tvOptions.setText("Vocabulary");
        idParent = (String) i.getSerializableExtra(Constants.ID_CATEGORY);
        layoutPractice = findViewById(R.id.layout_select_practice);
        cbSelectAll = findViewById(R.id.cb_select_all);
        btnDoPractice = findViewById(R.id.btn_action);
        imgBack = findViewById(R.id.imv_nav_left);
        rcTopic = findViewById(R.id.rc_topic);
        rcTopic.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ChooseTopicVocabularyAdapter(this, mDataTopic);
        rcTopic.setAdapter(mAdapter);
        mCurrentInt = intVoca;
        loadData();
    }

    private void loadData() {
        showLoading(true);
        new GetChildrenCategoriesTask(this, idParent, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @Override
    protected void addListener() {
        cbSelectAll.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new ChooseTopicVocabularyAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(String id, String title) {
                if (mCurrentInt == intVoca) {
                    Intent i = new Intent(CategoriesChildren.this, LearnVocabulary.class);
                    i.putExtra(Constants.id_CHILD_VOCA, id);
                    i.putExtra("title", title);
                    i.putExtra("type", "vocabulary");
                    startActivity(i);
                } else if (mCurrentInt == intGram) {
                    Intent i = new Intent(CategoriesChildren.this, LearnGrammar.class);
                    i.putExtra(Constants.id_CHILD_VOCA, id);
                    i.putExtra("title", title);
                    i.putExtra("type", "grammar");
                    startActivity(i);
                }
            }
        });
        btnDoPractice.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgArrow.setOnClickListener(this);
        tvOptions.setOnClickListener(this);
        rcTopic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (mDataTopic.size() > 0 && mIsLoadMore) {
                            showLoading(true);
                            mStart++;
//                            loadData();
                        }
                    }
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.tv_options:
                //khonong break để dùng chung function với arrow down
            case R.id.imv_arrow_down:
                dialogg = new CustomChooseOptionDialog(CategoriesChildren.this);
                dialogg.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//                dialogShow = 2;
                dialogg.show();
                dialogg.setmIOnItemClickedListener(new CustomChooseOptionDialog.IOnItemClickedListener() {
                    @Override
                    public void onItemClick(int type) {
                        if (type == intGram) {
                            mCurrentInt = intGram;
                            layoutPractice.setVisibility(View.INVISIBLE);
                            tvOptions.setText(getString(R.string.txt_learn_grammar));
                            new GetTopicTask(CategoriesChildren.this, idGrammar, CategoriesChildren.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else if (type == intPrac) {
                            mCurrentInt = intPrac;
                            layoutPractice.setVisibility(View.VISIBLE);
                            mDataTopic.clear();
                            gotVoca = false;
                            new GetTopicTask(CategoriesChildren.this, idVoca, CategoriesChildren.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            tvOptions.setText(getString(R.string.txt_do_practice));
                        } else if (type == intVoca) {
                            mCurrentInt = intVoca;
                            layoutPractice.setVisibility(View.INVISIBLE);
                            tvOptions.setText(getString(R.string.txt_learn_voca));
                            new GetTopicTask(CategoriesChildren.this, idVoca, CategoriesChildren.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    }
                });
                break;
            case R.id.cb_select_all:
                if (cbSelectAll.isChecked()) {
                    for (int i = 0; i < mDataTopic.size(); i++) {
                        mDataTopic.get(i).setChecked(true);
                    }
                } else {
                    for (int i = 0; i < mDataTopic.size(); i++) {
                        mDataTopic.get(i).setChecked(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_action:
                ArrayList<String> mDataTemp = new ArrayList<>();
                for (int i = 0; i < mDataTopic.size(); i++) {
                    if(mDataTopic.get(i).getChecked()){
                        mDataTemp.add(mDataTopic.get(i).getId());
                    }
                }
                Intent i = new Intent(this, DoPracticeActivity.class);
                i.putExtra(Constants.DATA_CATEGORIES, mDataTemp);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetChildrenCategoriesTask) {
            GetCategoriesOutput output = (GetCategoriesOutput) data;
            showLoading(false);
            if (output.success) {
                for (int i = 0; i < output.children.size(); i++) {
                    if (output.children.get(i).getName().toLowerCase().contains("ngu_phap")) {
                        idGrammar = output.children.get(i).getId();
                    }
                    if (output.children.get(i).getName().toLowerCase().contains("tu_vung")
                            || output.children.get(i).getName().toLowerCase().contains("tu_tung")) {
                        idVoca = output.children.get(i).getId();
                        new GetTopicTask(this, output.children.get(i).getId(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        return;
                    }
                }
            } else {
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if (task instanceof GetTopicTask) {
            GetCategoriesOutput output = (GetCategoriesOutput) data;
            if (output.success) {
                if (mCurrentInt != intPrac) {
                    mDataTopic.clear();
                    mDataTopic.addAll(output.children);
                    mAdapter.setIsPractice(false);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mDataTopic.addAll(output.children);
                    if (!gotVoca) {
                        gotVoca = true;
                        new GetTopicTask(CategoriesChildren.this, idGrammar, CategoriesChildren.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    mAdapter.setIsPractice(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
}
