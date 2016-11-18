package com.wschun.vmplayer.model;

import java.io.Serializable;

/**
 * Created by wschun on 2016/11/17.
 */

public class VideoBean implements Serializable {

    private String description;
    private String posterPic;
    private String title;
    private String type;

    public VideoBean(String type, String title, String posterPic, String description) {
        this.type = type;
        this.title = title;
        this.posterPic = posterPic;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPic() {
        return posterPic;
    }

    public void setPosterPic(String posterPic) {
        this.posterPic = posterPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
