package com.wschun.vmplayer.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by wschun on 2016/11/22.
 */

public class MeiZhiTypeAdapter extends FragmentStatePagerAdapter {
    private List<String> titles;
    private List<Fragment> fragmentList;

    public MeiZhiTypeAdapter(FragmentManager fm, List<String> titles,List<Fragment> fragmentList) {
        super(fm);
        this.titles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
