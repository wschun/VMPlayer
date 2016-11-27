package com.wschun.vmplayer.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.model.MusicBean;
import com.wschun.vmplayer.utils.CommUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by wschun on 2016/10/10.
 */

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    /**
     * 加载布局
     *
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_music, null);
        return view;
    }

    /**
     * 设置数据
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MusicBean musicBean = MusicBean.fromCursor(cursor);

        ViewHolder viewHolder = ViewHolder.getViewHolder(view);
        viewHolder.tvTitle.setText(CommUtils.splitName(musicBean.title));
        viewHolder.tvArtist.setText(musicBean.artist);
        viewHolder.tvSize.setText(Formatter.formatFileSize(context,musicBean.size));

    }




    static class ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.tv_size)
        TextView tvSize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public static ViewHolder getViewHolder(View view){
            ViewHolder viewHolder= (ViewHolder) view.getTag();
            if (viewHolder==null){
                viewHolder=new ViewHolder(view);
                view.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
