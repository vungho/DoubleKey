package com.example.vungho.mykeyalpha20.Image;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vungho on 20/03/2016.
 */
public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int numPage = 2;
    private String[] namePage;

    public ImageViewPagerAdapter(FragmentManager fm, String[] namePage) {
        super(fm);
        this.namePage = namePage;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        if (position == 0){
             fragment = new ImageFragmentTab1();

        }else if (position == 1){
             fragment = new ImageFragmentTab2();

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return namePage[position];
    }
}
