package com.wschun.vmplayer.dagger.module.fragement;

import com.wschun.vmplayer.presenter.fragement.MeiNvPresenter;
import com.wschun.vmplayer.ui.fragement.MeiNvFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wschun on 2016/11/22.
 */
@Module
public class MeiNvMpdule {
    private MeiNvFragment meiNvFragment;

    public MeiNvMpdule(MeiNvFragment meiNvFragment) {
        this.meiNvFragment = meiNvFragment;
    }

    @Provides
    MeiNvPresenter provideMeiNvPresenter(){
        return new MeiNvPresenter(meiNvFragment);
    }

}
