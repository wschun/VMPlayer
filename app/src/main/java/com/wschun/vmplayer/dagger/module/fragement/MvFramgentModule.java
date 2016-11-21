package com.wschun.vmplayer.dagger.module.fragement;

import com.wschun.vmplayer.presenter.fragement.MvFragmentPresenter;
import com.wschun.vmplayer.ui.fragement.HomeFragment;
import com.wschun.vmplayer.ui.fragement.MvFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wschun on 2016/11/21.
 */
@Module
public class MvFramgentModule {
    private MvFragment mvFragment;

    public MvFramgentModule(MvFragment mvFragment) {
        this.mvFragment = mvFragment;
    }

    @Provides
    MvFragmentPresenter providMvFragmentPresenter(){
        return new MvFragmentPresenter(mvFragment);
    }

}
