package nauq.mal.com.formapp.fragments.dictionary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.CommentActivity;
import nauq.mal.com.formapp.adapters.ListVocabularyAdapter;
import nauq.mal.com.formapp.adapters.PostAdapter;
import nauq.mal.com.formapp.adapters.PostSearchAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.api.models.GetPostOutput;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.fragments.bookmark.PostBookmarkFragment;
import nauq.mal.com.formapp.fragments.bookmark.WordBookmarkFragment;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.models.Words;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.BookmarkTask;
import nauq.mal.com.formapp.tasks.DeleteBookmarkTask;
import nauq.mal.com.formapp.tasks.GetNewFeedTask;
import nauq.mal.com.formapp.tasks.GetPostFollowIdTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.views.CustomSearchDialog;
import nauq.mal.com.formapp.views.CustomShowDialog;
import nauq.mal.com.formapp.views.CustomShowVocaDialog;

public class SearchFragment extends BaseFragment implements View.OnClickListener, ApiListener {
    private LinearLayout btnSearch;
    private int mStart = 0;
    private ArrayList<PostItem> mDataPost = new ArrayList<>();
    private ArrayList<Words> mDataWord = new ArrayList<>();
    private View mCurrentTab;
    private Button btnPost;
    private Button btnWord;
    private ConstraintLayout layoutTopVoca;
    private RecyclerView rcPost;
    private RecyclerView rcWord;
    private PostSearchAdapter mAdapterPost;
    private ListVocabularyAdapter mAdapterWord;
    @Override
    protected int initLayout() {
        return R.layout.fragment_dictionary;
    }

    @Override
    protected void initComponents() {
        btnSearch = mView.findViewById(R.id.btn_search);
        mCurrentTab = mView.findViewById(R.id.btnPost);
        btnPost = mView.findViewById(R.id.btnPost);
        btnWord = mView.findViewById(R.id.btnWord);
        layoutTopVoca = mView.findViewById(R.id.layout_top_voca);
        layoutTopVoca.setVisibility(View.GONE);

        rcPost = mView.findViewById(R.id.rcPost);
        rcPost.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapterPost = new PostSearchAdapter(mContext, mDataPost);
        rcPost.setAdapter(mAdapterPost);


        rcWord = mView.findViewById(R.id.rcWord);
        rcWord.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapterWord = new ListVocabularyAdapter(mContext, mDataWord);
        rcWord.setAdapter(mAdapterWord);

        mCurrentTab.setSelected(true);
        rcPost.setVisibility(View.VISIBLE);
        rcWord.setVisibility(View.GONE);
    }

    @Override
    protected void addListener() {
        btnSearch.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnWord.setOnClickListener(this);
        mAdapterPost.setOnItemClickListener(new PostSearchAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(String id) {
                showLoading(true);
                new GetPostFollowIdTask(mContext, id, SearchFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemClickBookmark(int pos, String id) {

            }
        });
        mAdapterWord.setOnItemClickListener(new ListVocabularyAdapter.IOnItemClickedListener() {
            @Override
            public void onItemBookmark(String id) {
                new BookmarkTask(mContext, id, "word", SearchFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onItemClick(String id) {
                //nothing
            }

            @Override
            public void onItemClickWord(Words wordsClick) {
                CustomShowVocaDialog dialog = new CustomShowVocaDialog(mContext, wordsClick);
                dialog.show();
            }

            @Override
            public void onItemDeleteBookmark(String id, int pos) {
                showLoading(true);
                new DeleteBookmarkTask(mContext, id, "word", SearchFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                CustomSearchDialog dialog = new CustomSearchDialog(mContext);
                dialog.show();
                dialog.setmIOnItemClickedListener(new CustomSearchDialog.IOnItemClickedListener() {
                    @Override
                    public void onItemSearch(String search) {
                        searchData(search);
                    }
                });
                break;
            case R.id.btnPost:
                mCurrentTab.setSelected(false);
                mCurrentTab = v;
                mCurrentTab.setSelected(true);
                rcPost.setVisibility(View.VISIBLE);
                rcWord.setVisibility(View.GONE);
                break;
            case R.id.btnWord:
                mCurrentTab.setSelected(false);
                mCurrentTab = v;
                mCurrentTab.setSelected(true);
                rcPost.setVisibility(View.GONE);
                rcWord.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void searchData(String search) {
        showLoading(true);
        new GetNewFeedTask(mContext, mStart, search, SearchFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetNewFeedTask) {
            showLoading(false);
            GetNewfeedOutput output = (GetNewfeedOutput) data;
            if (output.success) {
                mDataPost.clear();
                mDataPost.addAll(output.posts);
                mDataWord.clear();
                mDataWord.addAll(output.words);
                mAdapterPost.notifyDataSetChanged();
                mAdapterWord.notifyDataSetChanged();
            } else {
                showAlert(R.string.err_unexpected_exception);
            }
        }
        if(task instanceof GetPostFollowIdTask){
            showLoading(false);
            GetPostOutput output= (GetPostOutput) data;
            if(output.success){
                Intent i = new Intent(getActivity(), CommentActivity.class);
                i.putExtra(Constants.POST_COMMENT, output.post);
                startActivity(i);
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
}
