package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.dagger.component.fragement.DaggerMeiNvComponent;
import com.wschun.vmplayer.dagger.module.fragement.MeiNvMpdule;
import com.wschun.vmplayer.model.MeiNvBean;
import com.wschun.vmplayer.presenter.fragement.MeiNvPresenter;
import com.wschun.vmplayer.ui.adapter.MeiNvRvAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/15.
 */

public class MeiNvFragment extends BaseFragment {


    @Inject
    public MeiNvPresenter meiNvPresenter;
    @BindView(R.id.rv_meinv)
    RecyclerView rvMeinv;
    private List<MeiNvBean.ItemsBean> data;
    private MeiNvRvAdapter meiNvRvAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_meizhi, container, false);
        ButterKnife.bind(this, rootView);
        data = new ArrayList<>();
        initView();
        return rootView;
    }

    private void initView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvMeinv.setLayoutManager(staggeredGridLayoutManager);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerMeiNvComponent.builder().meiNvMpdule(new MeiNvMpdule(this)).build().in(this);
        showDialog();
        meiNvPresenter.getData();
    }


    public void setData(List<MeiNvBean.ItemsBean> datas) {
        meiNvRvAdapter = new MeiNvRvAdapter(getActivity(), datas);
        rvMeinv.setAdapter(meiNvRvAdapter);

        dismiss();
    }
}
