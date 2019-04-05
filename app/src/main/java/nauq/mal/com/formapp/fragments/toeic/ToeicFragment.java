package nauq.mal.com.formapp.fragments.toeic;

import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.adapters.PagerAdapter;
import nauq.mal.com.formapp.fragments.BaseFragment;

public class ToeicFragment extends BaseFragment {
    private ViewPager pager;
    private TabLayout tabLayout;
    @Override
    protected int initLayout() {
        return R.layout.fragment_toeic;
    }

    @Override
    protected void initComponents() {
        tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        pager = mView.findViewById(R.id.view_pager);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
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

    }
}



