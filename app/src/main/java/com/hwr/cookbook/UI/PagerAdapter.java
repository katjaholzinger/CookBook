package com.hwr.cookbook.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Thomas on 18.03.2018.
 */

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private FragmentDiscover tabDiscover;
    private FragmentBookmarks tabBookmarks;
    private FragmentPlaner tabPlaner;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        tabDiscover = new FragmentDiscover();
        tabBookmarks = new FragmentBookmarks();
        tabPlaner = new FragmentPlaner();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                //FragmentDiscover tab1 = new FragmentDiscover();
                return tabDiscover;
            case 1:
               // FragmentBookmarks tab2 = new FragmentBookmarks();
                return tabBookmarks;
            case 2:
                //FragmentPlaner tab3 = new FragmentPlaner();
                return tabPlaner;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}

