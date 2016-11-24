package com.wschun.vmplayer.dagger.component.fragement;

import com.wschun.vmplayer.ui.fragement.MvItemFragment;
import com.wschun.vmplayer.dagger.module.fragement.MvItemModule;

import dagger.Component;

/**
 * Created by wschun on 2016/11/21.
 */

@Component(modules = MvItemModule.class)
public interface MvItemComponent {
    void in(MvItemFragment mvItemFragment);
}
