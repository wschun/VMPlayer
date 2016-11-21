package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.TraceCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.dagger.component.fragement.DaggerHomeFragmentComponent;
import com.wschun.vmplayer.dagger.module.fragement.HomeFragmentModule;
import com.wschun.vmplayer.model.VideoBean;
import com.wschun.vmplayer.presenter.fragement.HomeFragmentPresenter;
import com.wschun.vmplayer.ui.adapter.HomeRecylerAdapter;

import java.util.ArrayList;
import java.util.List;

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
//    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    @Inject
    public HomeFragmentPresenter homeFragmentPresenter;
    private List<VideoBean> videoBeanlist;
    private HomeRecylerAdapter homeRecylerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, rootView);
        srlFresh= (SwipeRefreshLayout) rootView.findViewById(R.id.srl_fresh);
        videoBeanlist=new ArrayList<>();
        observ(640,540);
        initView();
        return rootView;
    }

    private void initView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRecylerAdapter = new HomeRecylerAdapter(getActivity(), videoBeanlist, mWidth, mHeight);
        rvHome.setAdapter(homeRecylerAdapter);
        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFresh=true;
                showDialog();
                homeFragmentPresenter.getData(0,SIZE);
            }
        });
        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((lastVisibleItemPosition+1)==homeRecylerAdapter.getItemCount() && hasMore){
                    srlFresh.setRefreshing(true);
                    showDialog();
                    homeFragmentPresenter.getData(offset,SIZE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this))
                .build().in(this);
        showDialog();
        homeFragmentPresenter.getData(offset,SIZE);
    }


    public void setData(List<VideoBean> datas) {
        if (isFresh){
            videoBeanlist.clear();
            isFresh=false;
        }

        hasMore=(datas.size()>0);

        offset+=datas.size();

        videoBeanlist.addAll(datas);
        homeRecylerAdapter.notifyDataSetChanged();
        srlFresh.setRefreshing(false);
        dismiss();
    }


    public void showError() {
        srlFresh.setRefreshing(false);
        dismiss();
    }
}
