package com.wschun.vmplayer.dagger.component.fragement;

import com.wschun.vmplayer.dagger.module.fragement.MusicModule;
import com.wschun.vmplayer.ui.fragement.MusicFragment;

import dagger.Component;

/**
 * Created by wschun on 2016/11/26.
 */
@Component(modules = MusicModule.class)
public interface MusicComponent {
    void in(MusicFragment musicFragment);
}
