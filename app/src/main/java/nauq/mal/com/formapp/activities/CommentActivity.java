package nauq.mal.com.formapp.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.CommentAdapter;
import nauq.mal.com.formapp.adapters.PostAdapter;
import nauq.mal.com.formapp.models.CommentItem;

public class CommentActivity extends BaseActivity implements View.OnClickListener {
    ImageView imgBack, imgSend;
    private ArrayList<CommentItem> mData = new ArrayList<CommentItem>();
    private int mStart = 0;
    private Calendar calendar;
    private boolean isLoading;
    private boolean mIsLoadMore;
    private LinearLayoutManager mLinearLayoutManager;
    private CommentAdapter mAdapter;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView rcComment;
    private EditText etComment;
    @Override
    protected int initLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initComponents() {
        setTitle("Trần Hồng Quân");
        etComment = findViewById(R.id.et_comment);
        imgSend = findViewById(R.id.img_send_cmt);
        imgBack = findViewById(R.id.imv_nav_left);
        rcComment = findViewById(R.id.rc_comment);
        rcComment.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CommentAdapter(this, mData);
        rcComment.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        calendar = Calendar.getInstance();
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis(), true));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mData.add(new CommentItem("hmmm", calendar.getTimeInMillis()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void addListener() {
        imgBack.setOnClickListener(this);
//        etComment.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        rcComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.img_send_cmt:
                calendar = Calendar.getInstance();
                mData.add(mData.size(), new CommentItem(etComment.getText().toString(), calendar.getTimeInMillis()));
                mAdapter.notifyDataSetChanged();
                etComment.setText("");
                hideKeyBoard();
                break;
        }
    }
}
