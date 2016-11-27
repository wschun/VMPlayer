package com.wschun.vmplayer.view;

import com.wschun.vmplayer.model.LyricBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wschun on 2016/10/12.
 */

public class LyricParser {

    public static List<LyricBean> parserLyricFromFile(File file) {
        List<LyricBean> lyricBeanList = new ArrayList<>();
        if (file != null && file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
                String readLine = bufferedReader.readLine();
//                [00:04.05]北京北京
                while (readLine != null) {
                    List<LyricBean> parserLyricFromLine = parserLyricFromLine(readLine);
                    lyricBeanList.addAll(parserLyricFromLine);
                    readLine = bufferedReader.readLine();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            lyricBeanList.add(new LyricBean("未成功加载歌词", 0));
        }

        return lyricBeanList;

    }

    /**
     * 解析一行歌词
     *
     * @param readLine
     */
    private static List<LyricBean> parserLyricFromLine(String readLine) {
//        [00:26.40  ]  [00:38.40   ]  时针它不停在转动
        List<LyricBean> lyricBeanList=new ArrayList<>();
        String[] lyriAcrray = readLine.split("]");
        String text=lyriAcrray[lyriAcrray.length-1];
        for (int i = 0; i < lyriAcrray.length - 1; i++) {
            lyricBeanList.add(new LyricBean(text,parserSplitTime(lyriAcrray[i])));
        }

        return lyricBeanList;
    }

    /**
     * 解析每行歌词对应的时间
     * @param s
     * @return
     */
    private static long parserSplitTime(String s) {
//        [00:26.40
        String[] timeArray = s.split(":");
        //  [00    26.40
        String min=timeArray[0].substring(1); //分钟

        timeArray=timeArray[1].split("\\.");

        String sec=timeArray[0];

        String mSec=timeArray[1];

        int time=Integer.parseInt(min)*60*1000+Integer.parseInt(sec)*1000+Integer.parseInt(mSec)*10;
        return time;
    }
}
