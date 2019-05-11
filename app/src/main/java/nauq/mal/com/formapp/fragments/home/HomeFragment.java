package nauq.mal.com.formapp.fragments.home;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.AddQuesActivity;
import nauq.mal.com.formapp.activities.CommentActivity;
import nauq.mal.com.formapp.activities.EditQuesActivity;
import nauq.mal.com.formapp.activities.MainActivity;
import nauq.mal.com.formapp.adapters.PostAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.api.models.GetTagsOutput;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.models.Tags;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.BookmarkTask;
import nauq.mal.com.formapp.tasks.DeleteBookmarkTask;
import nauq.mal.com.formapp.tasks.DislikeTask;
import nauq.mal.com.formapp.tasks.GetNewFeedTask;
import nauq.mal.com.formapp.tasks.GetTagsTask;
import nauq.mal.com.formapp.tasks.LikeTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.utils.SharedPreferenceHelper;

public class HomeFragment extends BaseFragment implements View.OnClickListener, ApiListener {
    private RecyclerView rcPost;
    private boolean isLoading;
    private ArrayList<PostItem> mData = new ArrayList<PostItem>();
    private int mStart = 0;
    private boolean mIsLoadMore;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PostAdapter mAdapter;
    private LinearLayout btnAddQuestion;
    private ArrayList<Tags> mDataTag = new ArrayList<>();
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance(String search) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void initComponents() {
        btnAddQuestion = mView.findViewById(R.id.btn_add_ques);
        if (!SharedPreferenceHelper.getInstance(mContext).get(Constants.PREF_SESSION_ID).equals(Constants.FAKE_TOKEN)) {
            btnAddQuestion.setVisibility(View.VISIBLE);
        } else {
            btnAddQuestion.setVisibility(View.GONE);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);
        rcPost = mView.findViewById(R.id.rc_post);
        rcPost.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new PostAdapter(mContext, mData, mDataTag);
        rcPost.setAdapter(mAdapter);
//        loadData();
        loadTag();
    }

    private void loadTag() {
        showLoading(true);
        new GetTagsTask(mContext, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadData() {
        showLoading(true);
        new GetNewFeedTask(mContext, mStart, "", this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    @Override
    protected void addListener() {
        btnAddQuestion.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                mStart = 0;
                loadData();
            }
        });
        mAdapter.setOnItemClickListener(new PostAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(String id) {
            }

            @Override
            public void onItemClickComment(PostItem postItem) {
                Intent i = new Intent(getActivity(), CommentActivity.class);
                i.putExtra(Constants.POST_COMMENT, postItem);
                startActivity(i);
            }

            @Override
            public void onItemDelete(Boolean isDelete) {
                if (isDelete) {
                    mStart = 0;
                    loadData();
                }
            }

            @Override
            public void onItemEdit(Boolean isDelete, int pos) {
                Intent i = new Intent(mContext, EditQuesActivity.class);
                i.putExtra("POST", mData.get(pos));
                startActivity(i);
            }

            @Override
            public void onItemLike(String id) {
                new LikeTask(mContext, id, HomeFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemDisLike(String id) {
                new DislikeTask(mContext, id, HomeFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemBookmark(String id) {
                new BookmarkTask(mContext, id, HomeFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemDeleteBookmark(String id) {
                new DeleteBookmarkTask(mContext, id, "", HomeFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

        });
        rcPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (mData.size() > 0 && mIsLoadMore && !isLoading) {
                            if (mData.size() == (mStart + 1) * 10) {
                                showLoading(true);
                                mStart += 1;
                                loadData();
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.img_report_fb:
//                CustomFeedbackAndRipDialog dialog = new CustomFeedbackAndRipDialog(mContext);
//                dialog.show();
            case R.id.btn_add_ques:
                Intent i = new Intent(getContext(), AddQuesActivity.class);
                getActivity().startActivityForResult(i, Constants.ADD_QUES_CODE);
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetNewFeedTask) {
            GetNewfeedOutput output = (GetNewfeedOutput) data;
            if (output.success) {
                mIsLoadMore = output.hasNextPage;
                if (mStart == 0) {
                    mData.clear();
                }
//                mData.addAll(output.posts);
                for (int i = 0; i < output.posts.size(); i++) {
                    mData.add(output.posts.get(i));
                    ArrayList<File> temp = new ArrayList<>();
                    for (int j = 0; j < output.posts.get(i).getImgs().size(); j++) {
                        Uri uri = Uri.parse(Constants.LINK_IMG + output.posts.get(i).getImgs().get(j));
                        temp.add(new File(uri.getPath()));
                    }
                    mData.get(i).setmDataFile(temp);
                }
//                for (PostItem item : output.posts) {
//                    mData.add(item);
//                }
                mAdapter.notifyDataSetChanged();
                showLoading(false);
            } else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
            return;
        }
        if (task instanceof LikeTask) {
            BaseOutput output = (BaseOutput) data;
            if (output.success) {

            }
        }
        if (task instanceof BookmarkTask) {
            BaseOutput output = (BaseOutput) data;
            if (output.success) {

            }
        }
        if (task instanceof DeleteBookmarkTask) {
            BaseOutput output = (BaseOutput) data;
            if (output.success) {

            }
        } if (task instanceof GetTagsTask) {
            showLoading(false);
            GetTagsOutput output = (GetTagsOutput) data;
            if (output.success) {
                mDataTag.addAll(output.tags);
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {
        showLoading(false);
        showAlert(exception);
    }

}

