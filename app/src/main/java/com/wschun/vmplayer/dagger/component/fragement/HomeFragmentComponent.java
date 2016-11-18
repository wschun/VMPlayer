package com.wschun.vmplayer.dagger.component.fragement;

import com.wschun.vmplayer.dagger.module.fragement.HomeFragmentModule;
import com.wschun.vmplayer.ui.fragement.HomeFragment;

import dagger.Component;

/**
 * Created by wschun on 2016/11/17.
 */
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {

    void in(HomeFragment homeFragment);

}
