package nauq.mal.com.formapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nauq.mal.com.formapp.fragments.menulearn.GrammarFragment;
import nauq.mal.com.formapp.fragments.menulearn.PracticeFragment;
import nauq.mal.com.formapp.fragments.menulearn.VocabFragment;
import nauq.mal.com.formapp.fragments.toeic.Part1Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part2Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part3Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part4Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part5Fragment;

public class CourseAdapter extends FragmentStatePagerAdapter {
    public CourseAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new VocabFragment();
                break;
            case 1:
                frag = new GrammarFragment();
                break;
            case 2:
                frag = new PracticeFragment();
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Vocabulary";
                break;
            case 1:
                title = "Grammar";
                break;
            case 2:
                title = "Practice";
                break;

        }
        return title;
    }
}
