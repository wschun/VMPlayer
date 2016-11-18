package com.wschun.vmplayer.dagger.module.fragement;

import com.wschun.vmplayer.presenter.fragement.HomeFragmentPresenter;
import com.wschun.vmplayer.ui.fragement.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wschun on 2016/11/17.
 */
@Module
public class HomeFragmentModule {

    private HomeFragment homeFragment;

    public HomeFragmentModule(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }
    @Provides
    HomeFragmentPresenter provideHomeFragmentPresenter(){
        return new HomeFragmentPresenter(homeFragment);
    }
}
