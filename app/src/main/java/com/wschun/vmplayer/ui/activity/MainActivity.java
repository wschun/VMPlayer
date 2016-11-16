package com.wschun.vmplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wschun.vmplayer.R;
import com.wschun.vmplayer.ui.fragement.HomeFragment;
import com.wschun.vmplayer.ui.fragement.MusicFragment;
import com.wschun.vmplayer.ui.fragement.MvFragment;
import com.wschun.vmplayer.ui.fragement.OtherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private SparseArray<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

        setListener();

    }

    private void init() {
        fragments = new SparseArray<>();
        fragments.put(R.id.tab_home,new HomeFragment());
        fragments.put(R.id.tab_mv,new MvFragment());
        fragments.put(R.id.tab_music,new MusicFragment());
        fragments.put(R.id.tab_other,new OtherFragment());
    }

    private void setListener() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment = fragments.get(tabId);
                switchContentUi(fragment);
            }
        });
    }

    private void switchContentUi(Fragment fragment) {
        if (fragment==null)
            return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment.isAdded()){
            if (fragment.isVisible()){
                //说明用户再次点击了
            }else {
                transaction.show(fragment);
            }
        }else {
            transaction.replace(R.id.fl_content,fragment);
        }

       transaction.commit();
    }

    @OnClick(R.id.iv_setting)
    public void onClick() {
        startActivity(new Intent(MainActivity.this,SettingActivity.class));
    }
}
