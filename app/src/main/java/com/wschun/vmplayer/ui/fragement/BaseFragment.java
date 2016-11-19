package com.wschun.vmplayer.ui.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.View;

import com.wschun.vmplayer.presenter.fragement.HomeFragmentPresenter;

import butterknife.Unbinder;

/**
 * Created by wschun on 2016/11/14.
 */

public class BaseFragment extends Fragment {

    protected View rootView;
    protected Unbinder bind;
    protected int offset=0;
    protected final int SIZE=10;
    protected int mWidth,mHeight;
    protected boolean isFresh=false;
    protected int lastVisibleItemPosition;
    protected boolean hasMore=true;

    protected void observ(int width,int height){

        DisplayMetrics metrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mWidth=metrics.widthPixels;
        mHeight=(mWidth*height)/width;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (bind != null)
            bind.unbind();
    }
}
