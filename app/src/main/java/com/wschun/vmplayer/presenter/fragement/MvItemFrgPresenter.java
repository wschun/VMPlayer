package com.wschun.vmplayer.presenter.fragement;

import com.wschun.vmplayer.http.HMCallBack;
import com.wschun.vmplayer.http.HttpManager;
import com.wschun.vmplayer.model.MvItemBean;
import com.wschun.vmplayer.presenter.BasePresenter;
import com.wschun.vmplayer.ui.fragement.MvItemFragment;
import com.wschun.vmplayer.utils.Constant;

import okhttp3.Call;

/**
 * Created by wschun on 2016/11/23.
 */

public class MvItemFrgPresenter extends BasePresenter {
    private MvItemFragment mvItemFragment;

    public MvItemFrgPresenter(MvItemFragment mvItemFragment) {
        this.mvItemFragment = mvItemFragment;
    }


    public void getData(String code, int offset, int size) {
        HttpManager httpManager = HttpManager.getInstance().addParam("area", code).addParam("offset", "" + offset).addParam("size", "" + size);
        httpManager.get(Constant.MV_ITEM, new HMCallBack<MvItemBean>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onSuccess(MvItemBean mvItemBean) {
                mvItemFragment.setData(mvItemBean.getVideos());
            }


        });

    }
}
