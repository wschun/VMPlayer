package com.wschun.vmplayer.ui.adapter;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

/**
 * Created by wschun on 2016/10/10.
 */

public class MyQueryHandler extends AsyncQueryHandler {


    public MyQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (token==0 && cookie instanceof MyCursorAdapter){
            MyCursorAdapter myCursorAdapter= (MyCursorAdapter) cookie;
            //通过查询到的Cursor刷新数据
            myCursorAdapter.changeCursor(cursor);
        }
    }
}
