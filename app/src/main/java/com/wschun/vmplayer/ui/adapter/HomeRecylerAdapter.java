package com.wschun.vmplayer.ui.adapter;

import android.content.Context;
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
        VideoBean videoBean=videoBeanList.get(position);

        ViewGroup.LayoutParams params = holder.ivContentimg.getLayoutParams();
        params.width=swidth;
        params.height=sheight;
        holder.viewbg.setLayoutParams(params);

        holder.tvTitle.setText(videoBean.getTitle());
        holder.tvDescription.setText(videoBean.getDescription());
        Glide.with(context).load(videoBean.getPosterPic()).into(holder.ivContentimg);
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
