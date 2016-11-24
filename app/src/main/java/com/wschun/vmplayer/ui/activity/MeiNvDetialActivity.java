package com.wschun.vmplayer.ui.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.wschun.vmplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class MeiNvDetialActivity extends BaseActivity {

    @BindView(R.id.iv_photo)
    PhotoView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_nv_detial);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url).into(ivPhoto);

    }
}
