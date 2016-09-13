package com.example.vungho.mykeyalpha20.Video;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vungho on 20/03/2016.
 */
public class VideoViewPagerAdapter extends FragmentStatePagerAdapter {


    private String[] pageTile;

    public VideoViewPagerAdapter(FragmentManager fm, String[] pageTile) {
        super(fm);
        this.pageTile = pageTile;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        if (position == 0)
                fragment = new VideoFragmentTab1();
        else if (position == 1)
                fragment = new VideoFragmentTab2();

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTile[position];
    }
}
