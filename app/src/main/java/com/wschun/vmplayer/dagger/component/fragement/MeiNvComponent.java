package com.wschun.vmplayer.dagger.component.fragement;

import com.wschun.vmplayer.dagger.module.fragement.MeiNvMpdule;
import com.wschun.vmplayer.ui.fragement.MeiNvFragment;

import dagger.Component;

/**
 * Created by wschun on 2016/11/22.
 */
@Component(modules = MeiNvMpdule.class)
public interface MeiNvComponent {
    void in(MeiNvFragment meiNvFragment);
}
