package com.wschun.vmplayer.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    private final Gson gson;
    private final Handler handler;
    private final HashMap<String, String> params;

    private HttpManager() {
        client = new OkHttpClient();
        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.myLooper());
        this.params = new HashMap<>();
    }

    public HttpManager addParam(String key,String value){
        this.params.put(key,value);
        return this;
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }

    public void get(String url, HMCallBack callBack) {
        Request request = buildRequest(url, RequestType.GET);
        doRequest(request, callBack);
    }

    public void post(String url, HMCallBack callBack) {
        Request request = buildRequest(url, RequestType.POST);
        doRequest(request, callBack);
    }

    private Request buildRequest(String url, RequestType type) {
        Request.Builder builder = new Request.Builder();
        if (type == RequestType.GET) {
            url=getParamWithString(url);
            builder.get();
        } else if (type == RequestType.POST) {
            RequestBody requestBody = formatRequestBody();
            builder.post(requestBody);
        }
        builder.url(url);
        return builder.build();
    }
    private String url;
    public String getURl(){
        return url;
    }

    private String getParamWithString(String url) {
        if (params==null || params.size()<1){
            this.url=url;
            return url;
        }
        if (url.indexOf("http://")==0 || url.indexOf("https://")==0){
            StringBuilder sb=new StringBuilder();
            sb.append(url+"&");
            for (Map.Entry<String,String> entry:params.entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            Log.i("HttpManager", "getParamWithString: "+sb.toString());
            this.url=sb.toString().substring(0,sb.toString().length()-1);
            return sb.toString().substring(0,sb.toString().length()-1);
        }else {
            throw new RuntimeException("url error");
        }

    }

    private RequestBody formatRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        if (params!=null && params.size()>0 ){
            for (Map.Entry<String,String> entry:params.entrySet()){
               builder.add(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }



    enum RequestType {
        GET,
        POST
    }


    private void doRequest(Request request, final HMCallBack callBack) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailure(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    sendSuccessful(call, json, callBack);
                } else {
                    sendFailure(call, null, callBack);
                }
            }
        });
    }

    private void sendFailure(final Call call, final IOException e, final HMCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private void sendSuccessful(final Call call, final String json, final HMCallBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack.type == String.class) {
                    callBack.onSuccess(json);
                } else {
                    try {
                        Object object = gson.fromJson(json, callBack.type);
                        callBack.onSuccess(object);
                    } catch (JsonSyntaxException e) {
                        callBack.onFailure(call, e);
                    }
                }
            }
        });
    }


}
