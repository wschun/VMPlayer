package com.wschun.vmplayer.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.http.HMCallBack;
import com.wschun.vmplayer.http.HttpManager;
import com.wschun.vmplayer.model.VideoBean;
import com.wschun.vmplayer.model.VideoDetialBean;
import com.wschun.vmplayer.model.YueDanBean;
import com.wschun.vmplayer.utils.CommUtils;
import com.wschun.vmplayer.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;

public class PlayerActivity extends BaseActivity {

    private static final String TAG ="PlayerActivity" ;
    @BindView(R.id.player)
    JCVideoPlayerStandard player;

    private int id;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
        tag = getIntent().getIntExtra("tag", 0);
        getVideoData(id);

    }

    private void getVideoData(int id) {

        HttpManager httpManager = HttpManager.getInstance().addParam("id", "" + id);
        Log.i(TAG, "getVideoData: "+httpManager.getURl());
        if (tag==0){
            httpManager.get(Constant.VIDEO_PATH, new HMCallBack<VideoDetialBean>() {
                @Override
                public void onFailure(Call call, Exception e) {

                }
                @Override
                public void onSuccess(VideoDetialBean videoDetialBean) {
                    String title=videoDetialBean.getTitle();
                    String url=videoDetialBean.getUrl();
                    player.setUp(url,title);
                    player.startButton.performClick();
                }
            });
        }else if (tag==1){
            httpManager.get(Constant.YUEDAN_PATH, new HMCallBack<YueDanBean>() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void onSuccess(YueDanBean yueDanBean) {
                    String title=yueDanBean.getVideos().get(0).getTitle();
                    String url=yueDanBean.getVideos().get(0).getUrl();
                    player.setUp(url,title);
                    player.startButton.performClick();
                }


            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
