package nauq.mal.com.formapp.fragments.menulearn;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.CategoriesChildren;
import nauq.mal.com.formapp.activities.ChooseTopicActivity;
import nauq.mal.com.formapp.adapters.CategoriesAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.GetCategoriesOutput;
import nauq.mal.com.formapp.fragments.BaseFragment;
import nauq.mal.com.formapp.models.Categories;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.GetCategoriesTask;
import nauq.mal.com.formapp.utils.Constants;

public class CourseFragment extends BaseFragment implements View.OnClickListener, ApiListener {
    private ArrayList<Categories> mData = new ArrayList<>();
    private RecyclerView rcCAtegories;
    private CategoriesAdapter mAdapter;
    @Override
    protected int initLayout() {
        return R.layout.fragment_menu_learn;
    }

    @Override
    protected void initComponents() {
        rcCAtegories = mView.findViewById(R.id.rcCategories);
        rcCAtegories.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CategoriesAdapter(mContext, mData);
        rcCAtegories.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        showLoading(true);
        new GetCategoriesTask(mContext, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void addListener() {
        mAdapter.setOnItemClickListener(new CategoriesAdapter.IOnItemClickedListener() {
            @Override
            public void onItemClick(String id) {
                Intent i = new Intent(mContext, CategoriesChildren.class);
                i.putExtra(Constants.ID_CATEGORY, id);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onConnectionOpen(BaseTask task) {

    }

    @Override
    public void onConnectionSuccess(BaseTask task, Object data) {
        if(task instanceof GetCategoriesTask){
            showLoading(false);
            GetCategoriesOutput output = (GetCategoriesOutput) data;
            if(output.success){
                mData.addAll(output.categories);
                mAdapter.notifyDataSetChanged();
            }else {
                showLoading(false);
                showAlert(R.string.err_unexpected_exception);
            }
        }
    }

    @Override
    public void onConnectionError(BaseTask task, Exception exception) {
            showLoading(false);
            showAlert(exception);
    }
}
