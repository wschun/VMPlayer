package com.wschun.vmplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.wschun.vmplayer.R;
import com.wschun.vmplayer.model.LyricBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wschun on 2016/10/11.
 */

public class LyricView extends TextView {

    private static final String TAG ="LyricView" ;
    private int hightColor;
    private int nomalColor;
    private float hightTextSize;
    private float nomalTextSize;
    private Paint paint;
    private int currentLightLLyric;
    private List<LyricBean> lyricBeans;
    private float lyricHight;
    private int mDuration;
    private int mPositionTime;

    public LyricView(Context context) {
        super(context);
        init();
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        hightColor = getResources().getColor(R.color.hightLightColor);
        nomalColor = Color.WHITE;
        hightTextSize = getResources().getDimension(R.dimen.hight_text_size);
        nomalTextSize = getResources().getDimension(R.dimen.nomal_text_size);
        lyricHight = getResources().getDimensionPixelOffset(R.dimen.hight_lyric);

        paint = new Paint();
        paint.setAntiAlias(true);//开启抗锯齿
        paint.setColor(hightColor);
        paint.setTextSize(hightTextSize);

        lyricBeans = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            lyricBeans.add(new LyricBean("当前歌词行数" + i, 2000*i));
        }
        currentLightLLyric = 0;
    }

    private int halfViewWidth, halfViewHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        halfViewWidth = w / 2;
        halfViewHeight = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制单行文本
//        drawSingleLyric(canvas);

        drawMulitLyric(canvas);


    }

    private void drawMulitLyric(Canvas canvas) {
//        x:根据文本就可以获取
//        y:高亮行的Y位置+（当前的行数-高亮行）*行高
        LyricBean lyricBean = lyricBeans.get(currentLightLLyric);

//        centerY+offsetY(偏移量)
//        offsetY=（presentTime）经过的时间百分比*行高
//        presentTime=（pastTime）经过的时间/行所用时间
//        pastTime=当前播放时间-高亮行歌词开始时间
//        行所用时间=下一行开始时间-高亮行歌词开始时间
        int endTime;
        if (currentLightLLyric+1==lyricBeans.size()){
            endTime=mDuration;
        }else {
            LyricBean lyricBeanNext = lyricBeans.get(currentLightLLyric + 1);
            endTime= (int) lyricBeanNext.getStartPoint();
        }
        int lyricTime= (int) (endTime-lyricBean.getStartPoint());
        int pastTime= (int) (mPositionTime-lyricBean.getStartPoint());
        float presentTime=pastTime/(float)lyricTime;
        int offsetY= (int) (presentTime*lyricHight);

        Rect bounds = new Rect();
        paint.getTextBounds(lyricBean.getContent(), 0, lyricBean.getContent().length(), bounds);
        int halfTextHeight = bounds.height() / 2;
        int centerY = halfViewHeight + halfTextHeight-offsetY;
        for (int i = 0; i < lyricBeans.size(); i++) {

            if (i == currentLightLLyric) {
                paint.setTextSize(hightTextSize);
                paint.setColor(hightColor);
            } else {
                if (i <currentLightLLyric-2){
                    paint.setTextSize(nomalTextSize);
                    paint.setColor(getResources().getColor(R.color.color_halfwhite));
                }else if(i >currentLightLLyric+2){
                    paint.setTextSize(nomalTextSize);
                    paint.setColor(getResources().getColor(R.color.color_halfwhite));
                }else {
                    paint.setTextSize(nomalTextSize);
                    paint.setColor(nomalColor);
                }



            }
            //计算每一行的Y位置
            int drawY = (int) (centerY + (i - currentLightLLyric) * lyricHight);
            //绘制单行文本
            drawHorizontalLyric(canvas, lyricBeans.get(i).getContent(), drawY);

        }
//        drawHorizontalLyric(canvas, text,drawY);
    }

    /**
     * 绘制一行文本
     *
     * @param canvas
     */
    private void drawSingleLyric(Canvas canvas) {
        //        x=view的一半减去文本的一半宽度
//        y=view的一半高度加上文本的一半高度
        String text = "正在加载歌词。。。";
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        bounds.width();///
        float layricWidth = paint.measureText(text);
        bounds.height();
        int halfTextHeight = bounds.height() / 2;
        int drawY = halfViewHeight + halfTextHeight;

        drawHorizontalLyric(canvas, text, drawY);
    }

    /**
     * 绘制水平居中的文本
     *
     * @param canvas
     * @param text
     * @param drawY
     */
    private void drawHorizontalLyric(Canvas canvas, String text, int drawY) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int halfTextWidth = bounds.width() / 2;
//        int  halfTextWidth= (int) paint.measureText(text);
        int drawX = halfViewWidth - halfTextWidth;
        canvas.drawText(text, drawX, drawY, paint);
    }

    /**
     * 歌词滚动
     *
     * @param positionTime
     * @param duration
     */
    public void roll(long positionTime, long duration) {
        mDuration = (int) duration;
        mPositionTime = (int) positionTime;

        Log.i(TAG, "roll: "+positionTime+":"+duration);
        for (int i = 0; i < lyricBeans.size(); i++) {
            LyricBean lyricBean = lyricBeans.get(i);
            int endTime;
            //防止角标越界
            if (i == (lyricBeans.size() - 1)) {
                endTime = (int) duration;
            } else {
                LyricBean lyricBeanNext = lyricBeans.get(i + 1);
                endTime = (int) lyricBeanNext.getStartPoint();
            }
            //判断当前播放的时间是否大于该行歌词的开始时间 并且 小于下一行开始时间
            if (positionTime > lyricBean.getStartPoint() && positionTime <= endTime) {
                currentLightLLyric = i;
                break;
            }
        }
        invalidate();

    }

    public void parserLyric(File file){
         lyricBeans =LyricParser.parserLyricFromFile(file);
         currentLightLLyric=0;
    }
}
