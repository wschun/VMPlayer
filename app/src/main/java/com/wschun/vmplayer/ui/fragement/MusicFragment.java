package com.wschun.vmplayer.ui.fragement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.dagger.component.fragement.DaggerMusicComponent;
import com.wschun.vmplayer.dagger.module.fragement.MusicModule;
import com.wschun.vmplayer.presenter.fragement.MusicPresenter;
import com.wschun.vmplayer.ui.activity.MusicPlayerActivity;
import com.wschun.vmplayer.ui.adapter.MyCursorAdapter;
import com.wschun.vmplayer.utils.CommUtils;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wschun on 2016/11/15.
 */

public class MusicFragment extends BaseFragment {

    @BindView(R.id.lv_list)
    ListView lvList;
    @Inject
    public MusicPresenter musicPresenter;

    private MyCursorAdapter myCursorAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_music, container, false);
        ButterKnife.bind(this, rootView);
        initView();

        return rootView;
    }

    private void initView() {
        myCursorAdapter = new MyCursorAdapter(getActivity(), null);
        lvList.setAdapter(myCursorAdapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor) myCursorAdapter.getItem(position);
                Intent mIntent=new Intent(getActivity(), MusicPlayerActivity.class);
                mIntent.putExtra("musicBeanList", (Serializable) CommUtils.getMusicList(cursor));
                mIntent.putExtra("position",position);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DaggerMusicComponent.builder().musicModule(new MusicModule(this)).build().in(this);

        musicPresenter.query(getActivity(),myCursorAdapter);
    }


}
