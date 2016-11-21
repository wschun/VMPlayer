package com.wschun.vmplayer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
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


    @Override
    public void onBackPressed() {
        final MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title("退出提示？");
        builder.content("亲，确定要离开吗");
        builder.cancelable(false);
        builder.negativeText("点错了");
        builder.positiveText("确定");

        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                 dialog.dismiss();
            }
        });
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();

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

    private Fragment mFragment;
    private void switchContentUi(Fragment fragment) {
        if (fragment==null)
            return;
        if (mFragment==null){
            mFragment=fragment;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (mFragment!=fragment){
            if (!fragment.isAdded()) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.fl_content, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
            }
            mFragment=fragment;
        }else {
            transaction.add(R.id.fl_content, fragment).commit();
        }
    }

    @OnClick(R.id.iv_setting)
    public void onClick() {
        startActivity(new Intent(MainActivity.this,SettingActivity.class));
    }
}
