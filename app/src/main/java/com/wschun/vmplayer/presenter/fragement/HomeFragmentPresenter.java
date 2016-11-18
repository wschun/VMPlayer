package com.wschun.vmplayer.presenter.fragement;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wschun.vmplayer.http.HMCallBack;
import com.wschun.vmplayer.http.HttpManager;
import com.wschun.vmplayer.model.VideoBean;
import com.wschun.vmplayer.presenter.BasePresenter;
import com.wschun.vmplayer.ui.fragement.HomeFragment;
import com.wschun.vmplayer.utils.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wschun on 2016/11/17.
 */

public class HomeFragmentPresenter extends BasePresenter {

    private static final String TAG = "HomeFragmentPresenter";
    private HomeFragment homeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }


    @Override
    public void getData() {
//        HttpManager.getInstance().get(Constant.HOME, new HMCallBack<Bean>() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//
//            }
//
//            @Override
//            public void onSuccess(Bean bean) {
//                list
//            }
//        });
    }
}
