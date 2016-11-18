package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wschun.vmplayer.presenter.fragement.HomeFragmentPresenter;

import butterknife.Unbinder;

/**
 * Created by wschun on 2016/11/14.
 */

public class BaseFragment extends Fragment {

    protected View rootView;
    protected Unbinder bind;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (bind != null)
            bind.unbind();
    }
}
