package com.wschun.vmplayer.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wschun.vmplayer.model.MusicBean;

import java.io.IOException;
import java.util.List;
import java.util.Random;


/**
 * Created by wschun on 2016/10/10.
 */

public class MusicPlayerService extends Service {

    public MusicPlayerProxy musicPlayerProxy;
    private List<MusicBean> musicBeanList;
    private int position;
    private MediaPlayer mediaPlayer;

    public static final int ORDER = 1;//顺序播放
    public static final int RANDOM = 2;//随机播放
    public static final int SINGLE = 3;//单曲循环

    private int currentMode = SINGLE;
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicPlayerProxy;
    }

    public class MusicPlayerProxy extends Binder {
        /**
         * 开始播放
         */
        public void play() {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(musicBeanList.get(position).path);
                //准备监听
                mediaPlayer.setOnPreparedListener(new MyOnPreparedListener());
                //播放完成的监听
                mediaPlayer.setOnCompletionListener(new MyOnCompletionListener());


                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * 获取播放器当前播放时间
         *
         * @return
         */
        public long getCurrentPosition() {
            if (mediaPlayer != null)
                return mediaPlayer.getCurrentPosition();
            else
                return 0;
        }

        /**
         * 获取音乐总时长
         *
         * @return
         */
        public long getDuration() {
            if (mediaPlayer != null)
                return mediaPlayer.getDuration();
            else
                return 0;
        }

        public void seekTo(int progress) {
            if (mediaPlayer != null)
                mediaPlayer.seekTo(progress);
        }

        public void pause() {
            if (mediaPlayer != null)
                mediaPlayer.pause();
        }

        public void start() {
            if (mediaPlayer != null){
                mediaPlayer.start();
                sendUpdataUI();
            }

        }

        /**
         * 控制播放与暂停
         */
        public void togglePlay() {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        }

        /**
         * 播放状态
         *
         * @return
         */
        public boolean isPlaying() {
            if (mediaPlayer != null)
                return mediaPlayer.isPlaying();
            else
                return false;
        }

        /**
         * 播放上一曲
         */
        public void playPre() {
            if (position > 0) {
                position--;
                play();
            }
        }

        /**
         * 播放下一曲
         */
        public void playNext() {
            if (position < musicBeanList.size() - 1) {
                position++;
                play();
            }

        }

        public boolean isFrist() {
            return position == 0;
        }

        public boolean isLast() {
            return position == musicBeanList.size() - 1;
        }

        /*
           停止播放
         */
        public void stop() {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        /**
         * 修改播放模式
         */
        public void switchPlayMode() {
            switch (currentMode){
                case ORDER:
                    currentMode=RANDOM;
                     break;
                case RANDOM:
                    currentMode=SINGLE;
                    break;
                case SINGLE:
                    currentMode=ORDER;
                    break;
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("music_mode",currentMode);
            edit.commit();
        }

        public int getCurrentMode() {
            return currentMode;
        }

        private class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                //发送广播跟新界面UI
                sendUpdataUI();
            }
        }

        private class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
            @Override
            public void onCompletion(MediaPlayer mp) {
                sendBoradOnCompletion();
                playByMode();
            }
        }
    }

    /**
     * 根据播放模式播放音乐
     */
    private void playByMode() {
        switch (currentMode) {
            case ORDER:
                //如果已经播放到最后一首，应该从头开始播放
                if (position == musicBeanList.size() - 1) {
                    position = 0;
                    musicPlayerProxy.play();
                } else {
                    musicPlayerProxy.playNext();
                }

                break;
            case RANDOM:

                position=new Random().nextInt(musicBeanList.size());
                musicPlayerProxy.play();

                break;
            case SINGLE:
                musicPlayerProxy.start();
                break;
        }

    }

    private void sendBoradOnCompletion() {
        Intent mIntent = new Intent();
        mIntent.setAction("completion");
        sendBroadcast(mIntent);
    }

    private void sendUpdataUI() {
        Intent mIntent = new Intent();
        mIntent.setAction("update_ui");
        mIntent.putExtra("musicBean", musicBeanList.get(position));
        sendBroadcast(mIntent);

    }


    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayerProxy = new MusicPlayerProxy();
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        currentMode=sharedPreferences.getInt("music_mode",ORDER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            musicBeanList = (List<MusicBean>) intent.getSerializableExtra("musicBeanList");
            position = intent.getIntExtra("position", -1);
        }
        return super.onStartCommand(intent, flags, startId);
    }


}
