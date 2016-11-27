package com.wschun.vmplayer.dagger.module.fragement;

import com.wschun.vmplayer.presenter.fragement.MusicPresenter;
import com.wschun.vmplayer.ui.fragement.MusicFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wschun on 2016/11/26.
 */

@Module
public class MusicModule {
    private MusicFragment musicFragment;

    public MusicModule(MusicFragment musicFragment) {
        this.musicFragment = musicFragment;
    }

    @Provides
    MusicPresenter provideMusicPresenter(){
        return new MusicPresenter(musicFragment);
    }

}
