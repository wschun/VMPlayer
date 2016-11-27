package com.wschun.vmplayer.model;

import java.io.Serializable;

/**
 * Created by wschun on 2016/11/25.
 */

public class VideoDetialBean implements Serializable {

    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
