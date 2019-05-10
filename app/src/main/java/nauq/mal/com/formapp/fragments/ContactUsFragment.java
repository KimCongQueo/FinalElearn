package nauq.mal.com.formapp.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.AddQuesActivity;
import nauq.mal.com.formapp.activities.CommentActivity;
import nauq.mal.com.formapp.activities.EditQuesActivity;
import nauq.mal.com.formapp.adapters.PostAdapter;
import nauq.mal.com.formapp.api.ApiListener;
import nauq.mal.com.formapp.api.models.BaseOutput;
import nauq.mal.com.formapp.api.models.GetNewfeedOutput;
import nauq.mal.com.formapp.models.PostItem;
import nauq.mal.com.formapp.tasks.BaseTask;
import nauq.mal.com.formapp.tasks.BookmarkTask;
import nauq.mal.com.formapp.tasks.DeleteBookmarkTask;
import nauq.mal.com.formapp.tasks.DislikeTask;
import nauq.mal.com.formapp.tasks.GetNewFeedTask;
import nauq.mal.com.formapp.tasks.LikeTask;
import nauq.mal.com.formapp.utils.Constants;

public class ContactUsFragment extends BaseFragment {
    @Override
    protected int initLayout() {
        return R.layout.contacts_layout;
    }
    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        return fragment;
    }
    @Override
    protected void initComponents() {
    }
  @Override
    protected void addListener() {
    }

}

