package nauq.mal.com.formapp.fragments.bookmark;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.CommentActivity;
import nauq.mal.com.formapp.activities.StudySingleVocabulary;
import nauq.mal.com.formapp.adapters.BookmarkAdapter;
import nauq.mal.com.formapp.adapters.ListVocabularyAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetBookmarkOutput;
import nauq.mal.com.formapp.api.models.GetPostOutput;
import nauq.mal.com.formapp.api.models.GetWordsOutput;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.models.Bookmark;
import nauq.mal.com.formapp.models.Words;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.DeleteBookmarkTask;
import nauq.mal.com.formapp.tasks.GetBookmarksTask;
import nauq.mal.com.formapp.tasks.GetPostFollowIdTask;
import nauq.mal.com.formapp.utils.Constants;
import nauq.mal.com.formapp.views.CustomShowVocaDialog;

public class WordBookmarkFragment extends BaseFragment implements View.OnClickListener, ApiListener {
    private RecyclerView rcPost;
    private ArrayList<Words> mData = new ArrayList<>();
    private ListVocabularyAdapter mAdapter;
    private int mStart = 0;
    private int posDel = -1;

    @Override
    protected int initLayout() {
        return R.layout.nav_fragment_add;
    }

    public static WordBookmarkFragment newInstance() {
        WordBookmarkFragment fragment = new WordBookmarkFragment();
        return fragment;
    }

    @Override
    protected void initComponents() {
        rcPost = mView.findViewById(R.id.rcPost);
        rcPost.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ListVocabularyAdapter(mContext, mData);
        rcPost.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        showLoading(true);
        new GetBookmarksTask(mContext, mStart, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void addListener() {
        mAdapter.setOnItemClickListener(new ListVocabularyAdapter.IOnItemClickedListener() {
            @Override
            public void onItemBookmark(String id) {
                //nothing
            }

            @Override
            public void onItemClick(String id) {
//                Intent i = new Intent(mContext, StudySingleVocabulary.class);
//                i.putExtra("array", mData);
//                startActivity(i);
            }

            @Override
            public void onItemClickWord(Words wordsClick) {
                //nothing
                CustomShowVocaDialog dialog = new CustomShowVocaDialog(mContext, wordsClick);
                dialog.show();
            }

            @Override
            public void onItemDeleteBookmark(String id, int pos) {
                showLoading(true);
                posDel = pos;
                new DeleteBookmarkTask(mContext, id, "word", WordBookmarkFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if (task instanceof GetBookmarksTask) {
            showLoading(false);
            GetBookmarkOutput output = (GetBookmarkOutput) data;
            if (output.success) {
                mData.addAll(output.words);
                for (int i = 0; i < mData.size(); i++) {
                    mData.get(i).setBookmark(true);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
        if (task instanceof GetPostFollowIdTask) {
            showLoading(false);
            GetPostOutput output = (GetPostOutput) data;
            if (output.success) {
                Intent i = new Intent(getActivity(), CommentActivity.class);
                i.putExtra(Constants.POST_COMMENT, output.post);
                startActivity(i);
            }
        }
        if (task instanceof DeleteBookmarkTask) {
            showLoading(false);
            BaseOutput output = (BaseOutput) data;
            if (output.success) {
                if (posDel != -1) {
                    mData.remove(posDel);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {

    }
}

