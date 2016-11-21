package com.wschun.vmplayer.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wschun.vmplayer.model.AreaBean;

import java.util.List;

/**
 * Created by wschun on 2016/11/21.
 */

public class MvFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<AreaBean> areaBeanList;

    public MvFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<AreaBean> areaBeanList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.areaBeanList = areaBeanList;
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
        return areaBeanList.get(position).getName();
    }
}
