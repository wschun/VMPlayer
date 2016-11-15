package com.wschun.vmplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.wschun.vmplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始执行时调用
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束时调用
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                 //动画重复执行时调用
            }
        });
        ivSplash.startAnimation(animation);
    }
}
