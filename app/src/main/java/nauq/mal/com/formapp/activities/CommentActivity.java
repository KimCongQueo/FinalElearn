package nauq.mal.com.formapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.CommentAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetCommentOutput;
import nauq.mal.com.formapp.models.CommentItem;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.tasks.AddCommentTask;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.BookmarkTask;
import nauq.mal.com.formapp.tasks.DeleteBookmarkTask;
import nauq.mal.com.formapp.tasks.DeleteCommentTask;
import nauq.mal.com.formapp.tasks.DislikeTask;
import nauq.mal.com.formapp.tasks.EditCommentTask;
import nauq.mal.com.formapp.tasks.GetCommentsTask;
import nauq.mal.com.formapp.tasks.LikeTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class CommentActivity extends BaseActivity implements View.OnClickListener, ApiListener {
    ImageView imgBack, imgSend;
    private ArrayList<CommentItem> mData = new ArrayList<>();
    private int mStart = 0;
    private PostItem postItem = new PostItem();
    private boolean isLoading;
    private int posDelete = -1;
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
        setTitle("Comments");
        etComment = findViewById(R.id.et_comment);
        imgSend = findViewById(R.id.img_send_cmt);
        imgBack = findViewById(R.id.imv_nav_left);
        rcComment = findViewById(R.id.rc_comment);
        rcComment.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CommentAdapter(this, mData);
        rcComment.setAdapter(mAdapter);
        if(!SharedPreferenceHelper.getInstance(this).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)){
            etComment.setVisibility(View.VISIBLE);
            imgSend.setVisibility(View.VISIBLE);
        } else {
            etComment.setVisibility(View.GONE);
            imgSend.setVisibility(View.GONE);
        }
        loadData();
    }

    private void loadData() {
        if(mStart == 0){
            Intent i = getIntent();
            postItem = (PostItem) i.getSerializableExtra("POST_COMMENT");
            if (postItem != null) {
                mData.add(new CommentItem(postItem.getIdPost(), postItem.getContent(), postItem.getCreated(), postItem.getUserPost(), true,
                        postItem.getImgs(), postItem.getComments(), postItem.getLikes(), postItem.getCheckBookmark(), postItem.getCheckLike()));
            }
        }
        showLoading(true);
        new GetCommentsTask(this, postItem.getIdPost(), mStart, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
                            loadData();
                        }
                    }
                }
            }
        });
        mAdapter.setOnItemClickListener(new CommentAdapter.IOnItemClickedListener() {
            @Override
            public void onItemEdit(String edit, String idCmt) {
                showLoading(true);
                new EditCommentTask(CommentActivity.this, idCmt ,edit, CommentActivity.this)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemLike(String id) {
                new LikeTask(CommentActivity.this, id, CommentActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemDisLike(String id) {
                new DislikeTask(CommentActivity.this, id, CommentActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemBookmark(String id) {
                new BookmarkTask(CommentActivity.this, id, CommentActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemDeleteBookmark(String id) {
                new DeleteBookmarkTask(CommentActivity.this, id, "", CommentActivity.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemClickDelete(int pos) {
                showLoading(true);
                posDelete = pos;
                new DeleteCommentTask(CommentActivity.this, mData.get(pos).getIdComment(), CommentActivity.this)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_nav_left:
                finish();
                break;
            case R.id.img_send_cmt:
                if (!etComment.getText().toString().trim().equals("") && etComment.getText().toString().trim().length() > 0) {
                    showLoading(true);
                    new AddCommentTask(this, etComment.getText().toString().trim(), postItem.getIdPost(), this)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                break;
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetCommentsTask) {
            showLoading(false);
            GetCommentOutput output = (GetCommentOutput) data;
            if (output.success) {
                mIsLoadMore = output.hasNextPage;
                if (mStart == 0) {
                    mData.clear();
                    Intent i = getIntent();
                    postItem = (PostItem) i.getSerializableExtra("POST_COMMENT");
                    if (postItem != null) {
                        mData.add(new CommentItem(postItem.getIdPost(), postItem.getContent(), postItem.getCreated(), postItem.getUserPost(), true,
                                postItem.getImgs(), postItem.getComments(), postItem.getLikes(), postItem.getCheckBookmark(), postItem.getCheckLike()));
                    }
                }
                for (CommentItem item : output.comments) {
                    mData.add(item);
                }
                mAdapter.notifyDataSetChanged();
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if (task instanceof AddCommentTask) {
            BaseOutput output = (BaseOutput) data;
            if (output.success) {
                etComment.setText("");
                hideKeyBoard();
                loadData();
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if(task instanceof DeleteCommentTask){
            BaseOutput output = (BaseOutput) data;
            if (output.success) {
                showLoading(false);
                mData.remove(posDelete);
                mAdapter.notifyDataSetChanged();
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if(task instanceof EditCommentTask){
            BaseOutput output = (BaseOutput) data;
            if (output.success) {
                loadData();
                mAdapter.notifyDataSetChanged();
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {
        showLoading(false);
        showAlert(R.string.err_unexpected_exception);
    }
}
