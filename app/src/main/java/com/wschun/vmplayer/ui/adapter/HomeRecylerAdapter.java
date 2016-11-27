package com.wschun.vmplayer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wschun.vmplayer.R;
import com.wschun.vmplayer.model.VideoBean;
import com.wschun.vmplayer.ui.activity.PlayerActivity;
import com.wschun.vmplayer.ui.activity.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/18.
 */

public class HomeRecylerAdapter extends RecyclerView.Adapter<HomeRecylerAdapter.MyViewHolder> {

    private Context context;
    private List<VideoBean> videoBeanList;
    private int swidth, sheight;

    public HomeRecylerAdapter(Context context, List<VideoBean> videoBeanList, int width, int height) {
        this.context = context;
        this.videoBeanList = videoBeanList;
        this.swidth = width;
        this.sheight = height;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepage_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoBean videoBean=videoBeanList.get(position);

        ViewGroup.LayoutParams params = holder.ivContentimg.getLayoutParams();
        params.width=swidth;
        params.height=sheight;
        holder.viewbg.setLayoutParams(params);

        holder.tvTitle.setText(videoBean.getTitle());
        holder.tvDescription.setText(videoBean.getDescription());
        Glide.with(context).load(videoBean.getPosterPic()).into(holder.ivContentimg);

         final int tag;
        String type = videoBean.getType();
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            tag = 0;
            holder.ivType.setImageResource(R.mipmap.home_page_activity);
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            tag = 1;
            holder.ivType.setImageResource(R.mipmap.home_page_video);
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 2;
            holder.ivType.setImageResource(R.mipmap.home_page_star);
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 3;
            holder.ivType.setImageResource(R.mipmap.home_page_playlist);
        } else if ("AD".equalsIgnoreCase(type)) {
            tag = 4;
            holder.ivType.setImageResource(R.mipmap.home_page_ad);
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            tag = 5;
            holder.ivType.setImageResource(R.mipmap.home_page_program);
        } else if ("bulletin".equalsIgnoreCase(type)) {
            tag = 6;
            holder.ivType.setImageResource(R.mipmap.home_page_bulletin);
        } else if ("fanart".equalsIgnoreCase(type)) {
            tag = 7;
            holder.ivType.setImageResource(R.mipmap.home_page_fanart);
        } else if ("live".equalsIgnoreCase(type)) {
            tag = 8;
            holder.ivType.setImageResource(R.mipmap.home_page_live);
        } else if ("LIVENEW".equalsIgnoreCase(type) || ("LIVENEWLIST".equals(type))) {
            tag = 9;
            holder.ivType.setImageResource(R.mipmap.home_page_live_new);
        } else if ("INVENTORY".equalsIgnoreCase(videoBean.getType())) {//打开页面
            tag = 10;
            holder.ivType.setImageResource(R.mipmap.home_page_project);
        } else {
            tag = -100;
            holder.ivType.setImageResource(0);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent();
                switch (tag){
                    case 0:
                    case 4:
                    case 10:
                        mIntent.setClass(context, WebActivity.class);
                        mIntent.putExtra("url",videoBean.getUrl());
                        context.startActivity(mIntent);
                        break;
                    case 1:
                    case 5:
                    case 7:
                        mIntent.setClass(context, PlayerActivity.class);
                        mIntent.putExtra("id",videoBean.getId());
                        mIntent.putExtra("tag",0);
                        context.startActivity(mIntent);

                        break;
                    case 2:
                    case 3:
                        mIntent.setClass(context, PlayerActivity.class);
                        mIntent.putExtra("id",videoBean.getId());
                        mIntent.putExtra("tag",1);
                        context.startActivity(mIntent);


                        break;
                }
            }

        });


    }


    @Override
    public int getItemCount() {
        return videoBeanList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_item_logo)
        ImageView ivItemLogo;
        @BindView(R.id.iv_contentimg)
        ImageView ivContentimg;
        @BindView(R.id.viewbg)
        View viewbg;
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.rl_item_rootView)
        RelativeLayout rlItemRootView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
