package com.wschun.vmplayer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wschun.vmplayer.R;
import com.wschun.vmplayer.model.ItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/21.
 */

public class MvItemAdapter extends RecyclerView.Adapter<MvItemAdapter.MyViewHolder> {

    private Context context;
    private int mWidth, mHeight;
    private List<ItemBean> itemBeanList;


    public MvItemAdapter(Context context, int mWidth, int mHeight, List<ItemBean> itemBeanList) {
        this.context = context;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.itemBeanList = itemBeanList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_mvitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemBean itemBean = itemBeanList.get(position);
        ViewGroup.LayoutParams layoutParams = holder.ivPostimg.getLayoutParams();
        layoutParams.width=mWidth;
        layoutParams.height=mHeight;
        holder.viewbgs.setLayoutParams(layoutParams);
        holder.title.setText(itemBean.getTitle());
        holder.author.setText(itemBean.getDescription());
        Glide.with(context).load(itemBean.getPosterPic()).into(holder.ivPostimg);
    }

    @Override
    public int getItemCount() {
        return itemBeanList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_postimg)
        ImageView ivPostimg;
        @BindView(R.id.viewbgs)
        View viewbgs;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
