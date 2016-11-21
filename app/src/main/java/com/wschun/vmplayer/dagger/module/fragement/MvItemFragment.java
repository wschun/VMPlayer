package com.wschun.vmplayer.dagger.module.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.ui.fragement.BaseFragment;

/**
 * Created by wschun on 2016/11/21.
 */

public class MvItemFragment extends BaseFragment {

    private String code;

    public static MvItemFragment getInstance(String code){
        MvItemFragment mvItemFragment=new MvItemFragment();
        Bundle bundle=new Bundle();
        bundle.putString("code",code);
        mvItemFragment.setArguments(bundle);
        return mvItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        code = (String) getArguments().get("code");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mv_item, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(code);
    }
}
