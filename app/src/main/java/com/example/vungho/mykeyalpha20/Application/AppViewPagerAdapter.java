package com.example.vungho.mykeyalpha20.Application;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vungho on 24/03/2016.
 */
public class AppViewPagerAdapter extends FragmentStatePagerAdapter {


    private String[] pageTile;

    public AppViewPagerAdapter(FragmentManager fm, String[] pageTile) {
        super(fm);
        this.pageTile = pageTile;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTile[position];
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        if (position == 0){
            fragment = new AppFragmentTab1();
        }else
            fragment = new AppFragmentTab2();
        return fragment;
    }
}
