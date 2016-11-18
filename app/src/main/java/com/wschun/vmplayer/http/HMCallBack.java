package com.wschun.vmplayer.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wschun on 2016/11/18.
 */

public abstract class HMCallBack {

    public abstract void onFailure(Call call,  Exception e);

    public abstract void onSuccess(Response response);

}
