package com.wschun.vmplayer.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by wschun on 2016/11/23.
 */

public class CommUtils {
    /**
     * 得到屏幕的宽度
     *
     * @param context 上下文
     * @return 屏幕的宽度
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return ((Activity) context).getWindowManager().getDefaultDisplay()
                .getWidth();
    }

}
