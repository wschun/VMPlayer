package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.dagger.component.fragement.DaggerMvFragmentComponent;
import com.wschun.vmplayer.dagger.module.fragement.MvFramgentModule;
import com.wschun.vmplayer.dagger.module.fragement.MvItemFragment;
import com.wschun.vmplayer.model.AreaBean;
import com.wschun.vmplayer.presenter.fragement.MvFragmentPresenter;
import com.wschun.vmplayer.ui.adapter.MvFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/15.
 */

public class MvFragment extends BaseFragment {

    @BindView(R.id.tl_layout)
    TabLayout tlLayout;
    @BindView(R.id.vp_page)
    ViewPager vpPage;
    @Inject
    public MvFragmentPresenter mvFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mv, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerMvFragmentComponent.builder().mvFramgentModule(new MvFramgentModule(this)).build().in(this);
        mvFragmentPresenter.getData();
    }

    private List<Fragment> mvFragments = new ArrayList<>();

    public void setData(List<AreaBean> data) {
        for (int i = 0; i < data.size(); i++) {
            mvFragments.add(MvItemFragment.getInstance(data.get(i).getCode()));
        }
        vpPage.setAdapter(new MvFragmentAdapter(getFragmentManager(), mvFragments,data));
        tlLayout.setupWithViewPager(vpPage);
    }
}
