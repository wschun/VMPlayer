package com.wschun.vmplayer.utils;

/**
 * Created by wschun on 2016/11/17.
 */

public interface Constant {

    //首页Url 支持分页加载 &offset=“开始位置” ,&size="条目个数"
    String HOME="http://mapi.yinyuetai.com/suggestions/front_page.json?deviceinfo=\n" +
            "{\"aid\":\"10201036\",\"os\":\"Android\",\"ov\":\"6.0\",\"rn\":\"480*800\",\n" +
            "\"dn\":\"Android SDK built for x86\",\"cr\":\"46000\",\"as\":\"WIFI\",\n" +
            "\"uid\":\"dbcaa6c4482bc05ecb0bf39dabf207d2\",\"clid\":110025000}&v=4&rn=640*540";

    //MV Url 获取的数据是条目类型
    String MV="http://mapi.yinyuetai.com/video/get_mv_areas.json?deviceinfo=\n" +
            "{\"aid\":\"10201036\",\"os\":\"Android\",\"ov\":\"6.0\",\"rn\":\"480*800\",\n" +
            "\"dn\":\"Android SDK built for x86\",\"cr\":\"46000\",\"as\":\"WIFI\",\n" +
            "\"uid\":\"dbcaa6c4482bc05ecb0bf39dabf207d2\",\"clid\":110025000}";





}
