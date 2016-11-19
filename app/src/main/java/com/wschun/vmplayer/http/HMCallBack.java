package com.wschun.vmplayer.http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wschun on 2016/11/18.
 */

public abstract class HMCallBack<T> {
    Type type;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public HMCallBack()
    {
        type = getSuperclassTypeParameter(getClass());
    }


    public abstract void onFailure(Call call,  Exception e);

    public abstract void onSuccess(T t);

}
