package com.wschun.vmplayer.presenter.fragement;

import com.wschun.vmplayer.http.HMCallBack;
import com.wschun.vmplayer.http.HttpManager;
import com.wschun.vmplayer.model.AreaBean;
import com.wschun.vmplayer.presenter.BasePresenter;
import com.wschun.vmplayer.ui.fragement.HomeFragment;
import com.wschun.vmplayer.ui.fragement.MvFragment;
import com.wschun.vmplayer.utils.Constant;

import java.util.List;

import okhttp3.Call;

/**
 * Created by wschun on 2016/11/21.
 */

public class MvFragmentPresenter extends BasePresenter {

    private MvFragment mvFragment;

    public MvFragmentPresenter(MvFragment mvFragment) {
        this.mvFragment = mvFragment;
    }

    public void getData() {
        HttpManager.getInstance().get(Constant.MV, new HMCallBack<List<AreaBean>>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }
            @Override
            public void onSuccess(List<AreaBean> areaBeen) {
                int size=areaBeen.size();
                mvFragment.setData(areaBeen);
            }
        });
    }
}
