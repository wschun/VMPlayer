package com.wschun.vmplayer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wschun.vmplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cacheSize)
    TextView cacheSize;
    @BindView(R.id.rl_clear_chche)
    RelativeLayout rlClearChche;
    @BindView(R.id.switch_push)
    SwitchCompat switchPush;
    @BindView(R.id.rl_switch_push)
    RelativeLayout rlSwitchPush;
    @BindView(R.id.switch_loadimg_no_wifi)
    SwitchCompat switchLoadimgNoWifi;
    @BindView(R.id.rl_loadimg_withwifi)
    RelativeLayout rlLoadimgWithwifi;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("VMPlayer");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.rl_clear_chche, R.id.switch_push, R.id.rl_switch_push, R.id.switch_loadimg_no_wifi, R.id.rl_loadimg_withwifi, R.id.rl_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clear_chche:
                break;
            case R.id.switch_push:
                break;
            case R.id.rl_switch_push:
                break;
            case R.id.switch_loadimg_no_wifi:
                break;
            case R.id.rl_loadimg_withwifi:

                break;
            case R.id.rl_about:

                break;
        }
    }
}
