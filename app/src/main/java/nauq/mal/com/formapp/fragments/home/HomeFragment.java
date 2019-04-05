package nauq.mal.com.formapp.fragments.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.AddQuesActivity;
import nauq.mal.com.formapp.activities.CommentActivity;
import nauq.mal.com.formapp.adapters.PostAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetNewFeedTask;
import nauq.mal.com.formapp.utils.Constants;

public class HomeFragment extends BaseFragment implements View.OnClickListener, ApiListener {
    private String getNewfeed = "http://f23f6310.ngrok.io/elearn/getNewfeed.php/";
    private RecyclerView rcPost;
    private boolean isLoading;
    private ArrayList<PostItem> mData = new ArrayList<PostItem>();
    private int mStart = 0;
    private boolean mIsLoadMore;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PostAdapter mAdapter;
    private String jsonString;
    private LinearLayout btnAddQuestion;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    protected void initComponents() {
        btnAddQuestion = mView.findViewById(R.id.btn_add_ques);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);
        rcPost = mView.findViewById(R.id.rc_post);
        rcPost.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new PostAdapter(mContext, mData);
        rcPost.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
//        showLoading(true);
        GetNewFeedTasks  asynctask = new GetNewFeedTasks();
        asynctask.execute(getNewfeed);
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
//        mData.add(new PostItem());
        mAdapter.notifyDataSetChanged();
    }
    private class GetNewFeedTasks extends AsyncTask<String, Integer, String> implements ApiListener<GetNewfeedOutput> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading(true);
        }
        @Override
        protected String doInBackground(String... strings) {
            InputStream is = new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            };
            BufferedReader br;
            String line = "";

            try{
                URL url = new URL(getNewfeed);
                is = url.openStream();
                jsonString = "";
                br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) !=null){
                    jsonString += line;
                }
                System.out.println(jsonString);;
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    if(is!=null){
                        is.close();
                    }
                } catch (IOException ioe){

                }
            }
            new GetNewFeedTask(mContext, jsonString,  this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return jsonString;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        public void onConnectionOpen(BaseTask task) {

        }

        @Override
        public void onConnectionSuccess(BaseTask task, GetNewfeedOutput data) {
            showLoading(false);
            if(task instanceof GetNewFeedTask){
                System.out.println(jsonString);
                GetNewfeedOutput output = data;
                if(output != null && output.success){
                    if(output.result != null){

                        for (PostItem item: output.result){
                            mData.add(item);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override
        public void onConnectionError(BaseTask task, Exception exception) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(checkAfterDialog){
//            if(mAdapter.getTextFromDialog().toString() != ""){
//                Toast.makeText(mContext, mAdapter.getTextFromDialog().toString(), Toast.LENGTH_LONG).show();}
//    }
}



    @Override
    protected void addListener() {
        btnAddQuestion.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                mStart = 0;
//                loadData();
            }
        });
        mAdapter.setOnItemClickListener(new PostAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(int id) {
                Toast.makeText(mContext, "hihi", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onItemClickComment(int id) {
                Intent i = new Intent(getActivity(), CommentActivity.class);
                startActivity(i);
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
//            case R.id.img_report_fb:
//                CustomFeedbackAndRipDialog dialog = new CustomFeedbackAndRipDialog(mContext);
//                dialog.show();
            case R.id.btn_add_ques:
                Intent i = new Intent(getContext(), AddQuesActivity.class);
                startActivityForResult(i, Constants.ADD_QUES_CODE);
        }
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if(task instanceof GetNewFeedTask){

        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {
        if (task instanceof GetNewFeedTask ) {
            showLoading(false);
            showAlert(exception);
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == Constants.ADD_QUES_CODE){
//            if(requestCode == Activity.RESULT_OK){
//                PostItem tmp = (PostItem) data.getSerializableExtra(getString(R.string.txt_et_question));
//                mData.add(0, tmp);
//                mAdapter.notifyDataSetChanged();
//            }
//        }
//    }
    
}

