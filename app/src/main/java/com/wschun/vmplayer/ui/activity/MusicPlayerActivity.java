package com.wschun.vmplayer.ui.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.model.MusicBean;
import com.wschun.vmplayer.service.MusicPlayerService;
import com.wschun.vmplayer.utils.CommUtils;
import com.wschun.vmplayer.view.LyricView;

import java.io.File;
import java.io.Serializable;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MusicPlayerActivity extends AppCompatActivity {

    private static final String TAG = "MusicPlayerActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.iv_animation)
    ImageView ivAnimation;

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.sb_seekbar)
    SeekBar sbSeekbar;
    @BindView(R.id.iv_play_mode)
    ImageView ivPlayMode;
    @BindView(R.id.iv_pre)
    ImageView ivPre;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.activity_music_player)
    LinearLayout activityMusicPlayer;
    @BindView(R.id.lyricView)
    LyricView lyricView;
    private MusicPlayerService.MusicPlayerProxy musicPlayerProxy;
    private MusicBroadCastReceiver musicBroadCastReceiver;
    private AnimationDrawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        ButterKnife.bind(this);
        initView();
        //注册接收广播
        registerReceivers();
        List<MusicBean> musicBeanList = (List<MusicBean>) getIntent().getSerializableExtra("musicBeanList");
        int position = getIntent().getIntExtra("position", -1);

        Intent mintent = new Intent(this, MusicPlayerService.class);
        mintent.putExtra("musicBeanList", (Serializable) musicBeanList);
        mintent.putExtra("position", position);
        MyServiceConnection myServiceConnection = new MyServiceConnection();

        //为了传递数据到服务中
        startService(mintent);
        //BindView_AUTO_CREATE :当服务没有创建的时候，会创建服务
        bindService(mintent, myServiceConnection, Service.BIND_AUTO_CREATE);

    }

    private void initView() {
        background = (AnimationDrawable) ivAnimation.getBackground();
        sbSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 当进度发生改变的时候
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicPlayerProxy.seekTo(progress);
                }
            }

            /**
             * 当手指按下的时候回调
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                musicPlayerProxy.pause();
            }

            /**
             * 当手指抬起时回调
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicPlayerProxy.start();
            }
        });
    }

    private void registerReceivers() {
        musicBroadCastReceiver = new MusicBroadCastReceiver();
        IntentFilter intentFiler = new IntentFilter();
        intentFiler.addAction("completion");
        intentFiler.addAction("update_ui");
        registerReceiver(musicBroadCastReceiver, intentFiler);
    }

    private class MusicBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到更新UI的广播
            if ("update_ui".equals(intent.getAction())) {
                MusicBean musicBean = (MusicBean) intent.getSerializableExtra("musicBean");
                updateUI(musicBean);
            } else if ("completion".equals(intent.getAction())) {
                onPlayCompletion();
            }
        }
    }

    private void onPlayCompletion() {
        background.stop();
        ivPlay.setBackgroundResource(R.drawable.selector_btn_audio_pause);
        //手动设置时间
        tvTime.setText(CommUtils.formatDuratiom((int) musicPlayerProxy.getDuration()) + "/"
                + CommUtils.formatDuratiom((int) musicPlayerProxy.getDuration()));
        //播放完成后移除定时任务
        handler.removeMessages(UPDATE_TIME_SEEKBAR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(UPDATE_TIME_SEEKBAR);
        unregisterReceiver(musicBroadCastReceiver);
    }

    /**
     * 更新UI的方法
     *
     * @param musicBean
     */
    private void updateUI(MusicBean musicBean) {
//        设置SeekBar的MAX
        sbSeekbar.setMax((int) musicPlayerProxy.getDuration());
        //设置标题和音乐播放按钮图标，作家
        tvTitle.setText(CommUtils.splitName(musicBean.title));
        ivPlay.setBackgroundResource(R.drawable.selector_btn_audio_play);
        tvArtist.setText(musicBean.artist);
        //初始化播放时间
        tvTime.setText(CommUtils.formatDuratiom((int) musicPlayerProxy.getCurrentPosition()) + "/"
                + CommUtils.formatDuratiom((int) musicPlayerProxy.getDuration())
        );
        //开启示波器帧动画
        background.start();
        //初始化播放模式图标
        updateModeIcon();
        //更新时间和进度条
        updateTimeAndSeekBar();
        File file= new File(Environment.getExternalStorageDirectory()+"/test/audio/"+CommUtils.splitName(musicBean.title)+".lrc");
        lyricView.parserLyric(file);

        //开启歌词滚动
        startLyricScroll();
    }

    private void startLyricScroll() {
        lyricView.roll((int) musicPlayerProxy.getCurrentPosition(), (int) musicPlayerProxy.getDuration());
//        handler.sendEmptyMessage(SCROLL_LYRIC);
        handler.sendEmptyMessageDelayed(SCROLL_LYRIC,50);
    }

    private static final int UPDATE_TIME_SEEKBAR = 1;
    private static final int SCROLL_LYRIC =2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME_SEEKBAR:
                    updateTimeAndSeekBar();
                    break;
                case SCROLL_LYRIC:
                    startLyricScroll();
                    break;
            }
        }
    };

    /**
     * 定时任务的方法，实时更新UI
     */
    private void updateTimeAndSeekBar() {
        //跟新时间
        tvTime.setText(CommUtils.formatDuratiom((int) musicPlayerProxy.getCurrentPosition()) + "/"
                + CommUtils.formatDuratiom((int) musicPlayerProxy.getDuration()));
        //设置进度
        sbSeekbar.setProgress((int) musicPlayerProxy.getCurrentPosition());
        handler.sendEmptyMessageDelayed(UPDATE_TIME_SEEKBAR, 200);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        musicPlayerProxy.stop();
    }

    @OnClick({R.id.iv_back, R.id.iv_play_mode, R.id.iv_pre, R.id.iv_play, R.id.iv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                musicPlayerProxy.stop();
                finish();
                break;
            case R.id.iv_play_mode://播放模式
                musicPlayerProxy.switchPlayMode();
                updateModeIcon();
                break;
            case R.id.iv_pre:
                if (musicPlayerProxy.isFrist()) {
                    Toast.makeText(MusicPlayerActivity.this, "当前已经是第一首啦", Toast.LENGTH_SHORT).show();
                } else {
                    musicPlayerProxy.playPre();
                }
                break;
            case R.id.iv_next:
                if (musicPlayerProxy.isLast()) {
                    Toast.makeText(MusicPlayerActivity.this, "当前已经是最后一首啦", Toast.LENGTH_SHORT).show();
                } else
                    musicPlayerProxy.playNext();
                break;
            case R.id.iv_play:
                musicPlayerProxy.togglePlay();
                if (musicPlayerProxy.isPlaying()) {
                    ivPlay.setBackgroundResource(R.drawable.selector_btn_audio_play);
                    background.start();
                } else {
                    ivPlay.setBackgroundResource(R.drawable.selector_btn_audio_pause);
                    background.stop();
                }
                break;

        }
    }

    /**
     * 修改播放模式的图标
     */
    private void updateModeIcon() {
        switch (musicPlayerProxy.getCurrentMode()) {
            case MusicPlayerService.ORDER:
                ivPlayMode.setBackgroundResource(R.drawable.selector_btn_playmode_order);
                break;
            case MusicPlayerService.RANDOM:
                ivPlayMode.setBackgroundResource(R.drawable.selector_btn_playmode_random);
                break;
            case MusicPlayerService.SINGLE:
                ivPlayMode.setBackgroundResource(R.drawable.selector_btn_playmode_single);
                break;
        }

    }

    class MyServiceConnection implements ServiceConnection {
        /**
         * 和服务连接成功
         *
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicPlayerProxy = (MusicPlayerService.MusicPlayerProxy) service;
            musicPlayerProxy.play();
        }

        /**
         * 和服务断开连接
         *
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
