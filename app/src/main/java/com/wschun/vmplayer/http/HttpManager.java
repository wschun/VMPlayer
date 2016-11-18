package com.wschun.vmplayer.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public void get(String url,HMCallBack callBack){
        Request request=buildRequest(url,RequestType.GET);
        doRequest(request,callBack);
    }

    public void post(String url,HMCallBack callBack){
        Request request=buildRequest(url, RequestType.POST);
        doRequest(request,callBack);
    }

    private Request buildRequest(String url, RequestType type) {
        Request.Builder builder=new Request.Builder();
        if (type==RequestType.GET){
            builder.get();
        }else if (type==RequestType.POST){
            RequestBody requestBody=formatRequestBody();
            builder.post(requestBody);
        }
        builder.url(url);
        return builder.build();
    }

    private RequestBody formatRequestBody() {
        FormBody.Builder builder=new FormBody.Builder();

        return builder.build();
    }

    enum RequestType{
        GET,
        POST
    }


    private void doRequest(Request request, final HMCallBack callBack){
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String string = response.body().string();

                    callBack.onSuccess(response);
                }else {
                    callBack.onFailure(call,null);
                }

            }

        });
    }


}
