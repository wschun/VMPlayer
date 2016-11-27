package com.wschun.vmplayer.presenter.fragement;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.wschun.vmplayer.presenter.BasePresenter;
import com.wschun.vmplayer.ui.adapter.MyCursorAdapter;
import com.wschun.vmplayer.ui.adapter.MyQueryHandler;
import com.wschun.vmplayer.ui.fragement.MusicFragment;

/**
 * Created by wschun on 2016/11/26.
 */

public class MusicPresenter extends BasePresenter {

    private  MusicFragment musicFragment;

    public MusicPresenter(MusicFragment musicFragment) {
        this.musicFragment = musicFragment;
    }


    public void query(Context context, MyCursorAdapter cursorAdapter) {

        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={ MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA
        };

        MyQueryHandler myQueryHandler = new MyQueryHandler(context.getContentResolver());
        //token: 用来标识谁在查询，
        myQueryHandler.startQuery(0,cursorAdapter,uri, projection, null, null, null);
    }
}
