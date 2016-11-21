package com.wschun.vmplayer.dagger.component.fragement;

import com.wschun.vmplayer.dagger.module.fragement.MvFramgentModule;
import com.wschun.vmplayer.ui.fragement.MvFragment;

import dagger.Component;

/**
 * Created by wschun on 2016/11/21.
 */
@Component(modules = MvFramgentModule.class)
public interface MvFragmentComponent {
    void in(MvFragment mvFragment);
}
