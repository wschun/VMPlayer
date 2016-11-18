package com.wschun.vmplayer.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wschun on 2016/11/17.
 */

public class HttpManager {

    private static HttpManager httpManager = null;
    private final OkHttpClient client;

    private HttpManager() {
        client = new OkHttpClient();
        client.newBuilder().connectTimeout(10,TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10,TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10,TimeUnit.SECONDS);
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }


}
