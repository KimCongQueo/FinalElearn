package nauq.mal.com.formapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nauq.mal.com.formapp.fragments.toeic.Part1Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part2Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part3Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part4Fragment;
import nauq.mal.com.formapp.fragments.toeic.Part5Fragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new Part1Fragment();
                break;
            case 1:
                frag = new Part2Fragment();
                break;
            case 2:
                frag = new Part3Fragment();
                break;
            case 3:
                frag = new Part4Fragment();
                break;
            case 4:
                frag = new Part5Fragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "One";
                break;
            case 1:
                title = "Two";
                break;
            case 2:
                title = "Three";
                break;
            case 3:
                title = "Four";
                break;
            case 4:
                title = "Five";
                break;
        }
        return title;
    }
}
