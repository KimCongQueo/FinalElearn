package nauq.mal.com.formapp.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.CourseAdapter;
import nauq.mal.com.formapp.adapters.PagerAdapter;

public class LearnTopicActivity extends BaseActivity implements View.OnClickListener {
    private String title;
    private ViewPager pager;
    private TabLayout tabLayout;
    private ImageView imgBack;
    @Override
    protected int initLayout() {
        return R.layout.activity_learn_topic;
    }

    @Override
    protected void initComponents() {
        Intent i = getIntent();
        title = (String) i.getSerializableExtra(getString(R.string.txt_course_name));
        setTitle(title);
        imgBack = findViewById(R.id.imv_nav_left);
        //init view pager
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        pager = findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        CourseAdapter adapter = new CourseAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);//deprecated
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.line_color));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

    }

    @Override
    protected void addListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_nav_left:
                finish();
        }
    }
}
