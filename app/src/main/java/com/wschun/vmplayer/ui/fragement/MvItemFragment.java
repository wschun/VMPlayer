package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wschun.vmplayer.R;

import com.wschun.vmplayer.dagger.component.fragement.DaggerMvItemComponent;
import com.wschun.vmplayer.dagger.module.fragement.MvItemModule;
import com.wschun.vmplayer.model.ItemBean;
import com.wschun.vmplayer.presenter.fragement.MvItemFrgPresenter;
import com.wschun.vmplayer.ui.adapter.MvItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/21.
 */

public class MvItemFragment extends BaseFragment {

    @BindView(R.id.rv_mvitem)
    RecyclerView rvMvitem;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;
    private String code;
    @Inject
    public MvItemFrgPresenter mvItemFrgPresenter;
    private List<ItemBean> itemBeanlist;
    private MvItemAdapter mvItemAdapter;

    public static MvItemFragment getInstance(String code) {
        MvItemFragment mvItemFragment = new MvItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        mvItemFragment.setArguments(bundle);
        return mvItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        code = (String) getArguments().get("code");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mv_item, container, false);
        ButterKnife.bind(this, rootView);
        observ(640, 360);
        initView();
        return rootView;
    }

    private void initView() {
        itemBeanlist = new ArrayList<>();
        rvMvitem.setLayoutManager(new LinearLayoutManager(getActivity()));
        mvItemAdapter = new MvItemAdapter(getActivity(), mWidth, mHeight, itemBeanlist);
        rvMvitem.setAdapter(mvItemAdapter);

        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFresh=true;
                mvItemFrgPresenter.getData(code, 0, SIZE);
            }
        });

        rvMvitem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((lastVisibleItemPosition+1)==mvItemAdapter.getItemCount() && hasMore){
                    srlFresh.setRefreshing(true);
                    mvItemFrgPresenter.getData(code, offset, SIZE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition=((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerMvItemComponent.builder().mvItemModule(new MvItemModule(this)).build().in(this);
        mvItemFrgPresenter.getData(code, offset, SIZE);
    }

    public void setData(List<ItemBean> data) {
        if (isFresh){
            itemBeanlist.clear();
            isFresh=false;
        }
        hasMore=(data.size()>0);
        offset+=data.size();
        itemBeanlist.addAll(data);
        mvItemAdapter.notifyDataSetChanged();
        srlFresh.setRefreshing(false);
    }
}
