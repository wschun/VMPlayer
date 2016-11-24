package com.wschun.vmplayer.dagger.module.fragement;

import com.wschun.vmplayer.presenter.fragement.MvItemFrgPresenter;
import com.wschun.vmplayer.ui.fragement.MvItemFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wschun on 2016/11/21.
 */
@Module
public class MvItemModule {
    private MvItemFragment mvItemFragment;

    public MvItemModule(MvItemFragment mvItemFragment) {
        this.mvItemFragment = mvItemFragment;
    }

    @Provides
    MvItemFrgPresenter providMvItemFrgPresenter() {
        return new MvItemFrgPresenter(mvItemFragment);
    }
}
