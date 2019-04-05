package nauq.mal.com.formapp.fragments.menulearn;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.MenuChooseGrammarAdapter;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.models.TopicVocaItem;

public class GrammarFragment extends BaseFragment implements View.OnClickListener{
    private RecyclerView rcListTopic;
    private boolean isLoading;
    private ArrayList<TopicVocaItem> mData = new ArrayList<TopicVocaItem>();
    private int mStart = 0;
    private boolean mIsLoadMore;
    private LinearLayoutManager mLinearLayoutManager;
    private MenuChooseGrammarAdapter mAdapter;
    private CheckBox cbAll;
    private Button btnAction;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    @Override
    protected int initLayout() {
        return R.layout.fragment_vocab;
    }

    @Override
    protected void initComponents() {
        btnAction = mView.findViewById(R.id.btn_action);
        btnAction.setText("Learn Grammar");
        cbAll = mView.findViewById(R.id.cb_select_all);
        btnAction = mView.findViewById(R.id.btn_action);
        rcListTopic = mView.findViewById(R.id.rc_vocab_topic);
        rcListTopic.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MenuChooseGrammarAdapter(mContext, mData);
        rcListTopic.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        mData.add(new TopicVocaItem(1, "Home"));
        mData.add(new TopicVocaItem(2, "Business"));
        mData.add(new TopicVocaItem(3, "School"));
        mData.add(new TopicVocaItem(4, "Love"));
        mData.add(new TopicVocaItem(5, "Travel"));
        mData.add(new TopicVocaItem(6, "Date and time"));
        mData.add(new TopicVocaItem(7, "Networking"));
        mData.add(new TopicVocaItem(8, "Start up"));
        mData.add(new TopicVocaItem(9, "Sports"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void addListener() {
        btnAction.setOnClickListener(this);

        rcListTopic.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        mAdapter.setOnItemClickListener(new MenuChooseGrammarAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(int id) {
                Toast.makeText(mContext, ""+id, Toast.LENGTH_LONG).show();
            }
        });
        cbAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                String tmp = "";
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getChecked()) {
                        tmp = tmp + " " + mData.get(i).toString();
                    }
                }
                Toast.makeText(mContext, tmp, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
