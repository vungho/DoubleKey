package com.example.vungho.mykeyalpha20.Music;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by vungho on 22/03/2016.
 */
public class MusicViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int numPage = 2;
    private String[] namePage;

    public MusicViewPagerAdapter(FragmentManager fm, String[] namePage) {
        super(fm);
        this.namePage = namePage;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            MusicFragmentTab1 musicFragmentTab1 = new MusicFragmentTab1();
            return musicFragmentTab1;
        }else if (position == 1){
            MusicFragmentTab2 musicFragmentTab2 = new MusicFragmentTab2();
            return musicFragmentTab2;
        }
        return new Fragment();
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
