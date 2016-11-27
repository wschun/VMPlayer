package com.wschun.vmplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import com.wschun.vmplayer.model.MusicBean;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 去掉.mp3
     * @param name
     * @return
     */
    public static String splitName(String name){
        int i = name.lastIndexOf(".");
        String newName = name.substring(0, i);
        return newName;
    }

    /**
     * 通过Cursor获取音乐集合数据
     * @param cursor
     * @return
     */
    public static List<MusicBean> getMusicList(Cursor cursor) {
        List<MusicBean> musicBeanList=new ArrayList<>();
        cursor.moveToPosition(-1);
        if (cursor!=null){
            while (cursor.moveToNext()){
                MusicBean musicBean = MusicBean.fromCursor(cursor);
                musicBeanList.add(musicBean);
            }
        }
        return musicBeanList;
    }

    public static String formatDuratiom(int duration){
        int HOUR=60*60*1000;//小时
        int MIN=60*1000;//分钟
        int SEC=1000;//秒

        int hour = duration / HOUR;
        int offset = duration % HOUR;

        int min = offset / MIN;
        offset=offset % MIN;

        int sec = offset / SEC;
        //格式化时间
        if (hour==0){
            return String.format("%02d:%02d", min, sec);
        }else {
            return   String.format("%02d:%02d:%02d",hour,min,sec);
        }
    }

}
