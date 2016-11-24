package com.wschun.vmplayer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wschun on 2016/11/21.
 */

public class MvItemBean implements Serializable{
    private List<ItemBean> videos;

    public MvItemBean(List<ItemBean> videos) {
        this.videos = videos;
    }

    public List<ItemBean> getVideos() {
        return videos;
    }

    public void setVideos(List<ItemBean> videos) {
        this.videos = videos;
    }
}
