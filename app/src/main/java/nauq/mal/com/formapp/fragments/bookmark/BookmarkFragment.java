package nauq.mal.com.formapp.fragments.bookmark;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import java.nio.file.attribute.PosixFileAttributes;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.fragments.BaseFragment;

public class BookmarkFragment extends BaseFragment implements View.OnClickListener {
    private View mCurrentTab;
    private Button btnPost;
    private Button btnWord;
    private ConstraintLayout layoutTopVoca;
    @Override
    protected int initLayout() {
        return R.layout.fragment_bookmark;
    }

    public static BookmarkFragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
        return fragment;
    }

    @Override
    protected void initComponents() {
        mCurrentTab = mView.findViewById(R.id.btnPost);
        btnPost = mView.findViewById(R.id.btnPost);
        btnWord = mView.findViewById(R.id.btnWord);
        layoutTopVoca = mView.findViewById(R.id.layout_top_voca);
        layoutTopVoca.setVisibility(View.GONE);
        mCurrentTab.setSelected(true);
        setNewPageChild(new PostBookmarkFragment());
    }

    @Override
    protected void addListener() {
        btnPost.setOnClickListener(this);
        btnWord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPost:
                mCurrentTab.setSelected(false);
                mCurrentTab = v;
                mCurrentTab.setSelected(true);
                layoutTopVoca.setVisibility(View.GONE);
                setNewPageChild(new PostBookmarkFragment());
                break;
            case R.id.btnWord:
                mCurrentTab.setSelected(false);
                mCurrentTab = v;
                mCurrentTab.setSelected(true);
                layoutTopVoca.setVisibility(View.VISIBLE);
                setNewPageChild(new WordBookmarkFragment());
                break;
        }
    }

}

