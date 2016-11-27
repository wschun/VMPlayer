package com.wschun.vmplayer.model;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created by wschun on 2016/8/23.
 */
public class MusicBean implements Serializable {
    public String title;
    public String path;
    public long duration;
    public long size;
    public String artist;

    public static MusicBean fromCursor(Cursor cursor){
        MusicBean musicBean=new MusicBean();
        musicBean.title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
        musicBean.path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        musicBean.duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        musicBean.size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
        musicBean.artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        return  musicBean;
    }
}
