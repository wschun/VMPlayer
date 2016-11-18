package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.dagger.component.fragement.DaggerHomeFragmentComponent;
import com.wschun.vmplayer.dagger.module.fragement.HomeFragmentModule;
import com.wschun.vmplayer.presenter.fragement.HomeFragmentPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wschun on 2016/11/15.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    @Inject
    public HomeFragmentPresenter homeFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this))
                .build().in(this);
        homeFragmentPresenter.getData();

    }


}
