package com.wschun.vmplayer.model;

import java.io.Serializable;

/**
 * Created by Mr.Wang
 * Date  2016/9/4.
 * Email 1198190260@qq.com
 */
public class AreaBean implements Serializable {
    public String name;
    public String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
