package com.wschun.vmplayer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wschun.vmplayer.R;
import com.wschun.vmplayer.model.MeiNvBean;
import com.wschun.vmplayer.ui.activity.MainActivity;
import com.wschun.vmplayer.ui.activity.MeiNvDetialActivity;
import com.wschun.vmplayer.utils.CommUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/23.
 */

public class MeiNvRvAdapter extends RecyclerView.Adapter<MeiNvRvAdapter.ImgViewHolder> {


    private Context context;
    private List<MeiNvBean.ItemsBean> data;

    public MeiNvRvAdapter(Context context, List<MeiNvBean.ItemsBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meinv_item, parent, false);
        return new ImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImgViewHolder holder, final int position) {
        MeiNvBean.ItemsBean itemsBean = data.get(position);

        ViewGroup.LayoutParams layoutParams = holder.netImg.getLayoutParams();
        int mWidth=CommUtils.getScreenWidth(context)/2;
        int imc_w=Integer.parseInt(itemsBean.getWidth());
        int imc_h=Integer.parseInt(itemsBean.getHeight());
        layoutParams.width= CommUtils.getScreenWidth(context)/2;
        layoutParams.height=(mWidth*imc_h)/imc_w;
        Glide.with(context).
                load(itemsBean.getSmallThumbUrl()).
                diskCacheStrategy( DiskCacheStrategy.ALL ).
                into(holder.netImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startImgActivity(v,position);
            }
        });
    }

    private void startImgActivity(View view,int positon) {
        MeiNvBean.ItemsBean itemsBean = data.get(positon);
        if (Build.VERSION.SDK_INT < 21) {
        } else {
            Intent intent = new Intent(context, MeiNvDetialActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((MainActivity)context, view, "test");
            intent.putExtra("url",itemsBean.getSmallThumbUrl());
            context.startActivity(intent );
        }

    }

    private Listener lisetner;

    public void setLitener(Listener lisenter){
        this.lisetner=lisenter;
    }


    public interface Listener{
        void onItemClickListener(View view,int position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ImgViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.net_img)
        ImageView netImg;

        ImgViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
