package nauq.mal.com.formapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.BottomSheetPracticeAdapter;
import nauq.mal.com.formapp.adapters.QuestionAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetPointOutput;
import nauq.mal.com.formapp.api.models.GetPracticeOnlyTopicOutput;
import nauq.mal.com.formapp.models.Answer;
import nauq.mal.com.formapp.models.Categories;
import nauq.mal.com.formapp.models.Question;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetPracticeMultiTopicTask;
import nauq.mal.com.formapp.tasks.GetPracticeOnlyTopicTask;
import nauq.mal.com.formapp.tasks.PostAnswerTask;
import nauq.mal.com.formapp.utils.Constants;

public class DoPracticeActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    private String getquestion = Constants.API_URL + "/do_practice.php/";
    private ImageView imgBack, imgArrowLeft, imgArrowRight;
    private ImageView imgStop;
    private RecyclerView rcQuestion, rcGeneralityAns;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<Question> mData = new ArrayList<>();
    private ArrayList<Boolean> mAnsBottom = new ArrayList<>();
    private LinearLayout bottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private QuestionAdapter mAdapter;
    private BottomSheetPracticeAdapter mBottomAdapter;
    private boolean isEnd = false;
    private URL url;
    private String jsonString = "";
    private SnapHelper snapHelper;
    private int currentQuestionIndex = 0;
    private TextView tvTotalNumQues, tvPosQuesBelow;
    private String idNeedGet;
    private String title;
    private String matches = "";
    private ArrayList<String> mDataIdCate = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_do_practice;
    }

    @Override
    protected void initComponents() {
        Intent i = getIntent();
        idNeedGet = (String) i.getSerializableExtra(Constants.id_CHILD_VOCA);
        mDataIdCate = (ArrayList<String>) i.getSerializableExtra(Constants.DATA_CATEGORIES);
        if (mDataIdCate != null && mDataIdCate.size() == 1) {
            idNeedGet = mDataIdCate.get(0);
        }
        setTitle(title = (String) i.getSerializableExtra("title"));
        imgBack = findViewById(R.id.imv_nav_left);
        imgStop = findViewById(R.id.img_stop);
        imgArrowLeft = findViewById(R.id.imgArrowLeft);
        imgArrowRight = findViewById(R.id.imgArrowRight);
        rcGeneralityAns = findViewById(R.id.rc_generality_ans);
        bottomSheet = findViewById(R.id.bottom_sheet);
        rcQuestion = findViewById(R.id.rc_question);
        tvTotalNumQues = findViewById(R.id.tv_total_num_ques);
        tvPosQuesBelow = findViewById(R.id.tv_pos_ques_below);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        rcQuestion.setLayoutManager(mLinearLayoutManager);
        mAdapter = new QuestionAdapter(this, mData);
        rcQuestion.setAdapter(mAdapter);
        loadData();

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rcQuestion);


    }

    private void loadData() {
//        loadQuestion();
        showLoading(true);
        if (idNeedGet != null && !idNeedGet.equals("")) {
            new GetPracticeOnlyTopicTask(this, idNeedGet, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if (mDataIdCate != null && mDataIdCate.size() > 1) {
            new GetPracticeMultiTopicTask(this, mDataIdCate, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }


    @Override
    protected void addListener() {
        imgBack.setOnClickListener(this);
        imgStop.setOnClickListener(this);
        imgArrowRight.setOnClickListener(this);
        imgArrowLeft.setOnClickListener(this);
        rcQuestion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(mLinearLayoutManager);
                    int pos = mLinearLayoutManager.getPosition(centerView);
                    currentQuestionIndex = pos;
                    tvPosQuesBelow.setText(++pos + "");

                    mBottomAdapter.setSeclectIndex(pos);
                    mAnsBottom = new ArrayList<>();
                    mAnsBottom = mAdapter.getmAnsChoose();
                    mBottomAdapter.updateAnswerTable(mAnsBottom);
                    mBottomAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.img_stop:
                if (!isEnd) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.app_name)
                            .setMessage(R.string.end_alert)
                            .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    endPractice();
                                }
                            }).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();
                }
                break;
            case R.id.imgArrowLeft:
                if (currentQuestionIndex == 0)
                    return;
                rcQuestion.smoothScrollToPosition(--currentQuestionIndex);
                break;
            case R.id.imgArrowRight:
                if (currentQuestionIndex == mData.size() - 1)
                    return;
                rcQuestion.smoothScrollToPosition(++currentQuestionIndex);
                break;
        }
    }

    private void endPractice() {
//        ArrayList<String[]> answer = new ArrayList<>(20);
//        answer = mAdapter.getmAnsChooseStrings();
        ArrayList<String> answer = new ArrayList<>(20);
        answer = mAdapter.getmAnsChooseString();
        showLoading(true);
        new PostAnswerTask(this, matches, answer, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadBottomSheet() {
        rcGeneralityAns.setLayoutManager(new GridLayoutManager(this, 5));
        for (int i = 0; i < mData.size(); i++) {
            ArrayList<Answer> answers = new ArrayList<>();
            answers = mData.get(i).getArrAns();
        }
        mBottomAdapter = new BottomSheetPracticeAdapter(this, mData);
        mBottomAdapter.setOnItemClickListener(new BottomSheetPracticeAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(int positon) {
                rcQuestion.smoothScrollToPosition(positon);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        mBottomAdapter.setSeclectIndex(1);
        rcGeneralityAns.setAdapter(mBottomAdapter);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
//                        mAnsBottom = new ArrayList<>();
//                        mAnsBottom = mAdapter.getmAnsChoose();
//                        mBottomAdapter.updateAnswerTable(mAnsBottom);
//                        mBottomAdapter.notifyDataSetChanged();
//                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        mAnsBottom = new ArrayList<>();
                        mAnsBottom = mAdapter.getmAnsChoose();
                        mBottomAdapter.updateAnswerTable(mAnsBottom);
                        mBottomAdapter.notifyDataSetChanged();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mAnsBottom = new ArrayList<>();
                        mAnsBottom = mAdapter.getmAnsChoose();
                        mBottomAdapter.updateAnswerTable(mAnsBottom);
                        mBottomAdapter.notifyDataSetChanged();
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        mAnsBottom = new ArrayList<>();
//                        mAnsBottom = mAdapter.getmAnsChoose();
//                        mBottomAdapter.updateAnswerTable(mAnsBottom);
//                        mBottomAdapter.notifyDataSetChanged();
//                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mAnsBottom = mAdapter.getmAnsChoose();
                mBottomAdapter.updateAnswerTable(mAnsBottom);
                mBottomAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetPracticeOnlyTopicTask || task instanceof  GetPracticeMultiTopicTask) {
            showLoading(false);
            GetPracticeOnlyTopicOutput output = (GetPracticeOnlyTopicOutput) data;
//            if (output.success) {
            for (Question item : output.questions) {
                mData.add(item);
            }
            matches = output.matches;
            if (mData != null) {
                tvTotalNumQues.setText("/ " + mData.size() + "");
                for (int i = 0; i < mData.size(); i++) {
                    ArrayList<Answer> ans = new ArrayList<>();
                    for (int j = 0; j < mData.get(i).getAnswers().size(); j++) {
                        ans.add(new Answer(j, mData.get(i).getAnswers().get(j), false));
                    }
                    mData.get(i).setArrAns(ans);
                }
            }
            mAdapter.notifyDataSetChanged();
            loadBottomSheet();
        }
//        } else {
//            showAlert(R.string.err_unexpected_exception);
//        }
        if (task instanceof PostAnswerTask) {
            showLoading(false);
            GetPointOutput output = (GetPointOutput) data;
            if (output.success) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage("Your point is " + output.match + "/20")
                        .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .create().show();
            } else {
                showAlert(R.string.err_unexpected_exception);
            }
        }
    }


    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }

}
