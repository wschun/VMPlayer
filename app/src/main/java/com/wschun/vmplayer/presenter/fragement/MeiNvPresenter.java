package com.wschun.vmplayer.presenter.fragement;

import com.wschun.vmplayer.http.HMCallBack;
import com.wschun.vmplayer.http.HttpManager;
import com.wschun.vmplayer.model.MeiNvBean;
import com.wschun.vmplayer.presenter.BasePresenter;
import com.wschun.vmplayer.ui.fragement.MeiNvFragment;
import com.wschun.vmplayer.utils.Constant;

import okhttp3.Call;

/**
 * Created by wschun on 2016/11/22.
 */

public class MeiNvPresenter extends BasePresenter {
    private MeiNvFragment meiNvFragment;

    public MeiNvPresenter(MeiNvFragment meiNvFragment) {
        this.meiNvFragment = meiNvFragment;
    }

    public void getData() {
        HttpManager.getInstance().get(Constant.MEINV, new HMCallBack<MeiNvBean>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onSuccess(MeiNvBean meiNvBean) {
                int size=meiNvBean.getItems().size();
                meiNvFragment.setData(meiNvBean.getItems());
            }
        });
    }
}
