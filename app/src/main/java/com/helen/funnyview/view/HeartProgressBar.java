package com.helen.funnyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.helen.funnyview.R;


/**
 * Created by Helen on 2015/5/14.
 */
public class HeartProgressBar extends View{
    private Bitmap mHeartBitmap;//空心图片
    private Bitmap mHeartedBitmap;
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private int progress=0;//当前进度
    private int max=100;//最大进度
    private boolean isFinish=false;//是否填充完成
    private boolean isScale=false;//图片是否缩放
    private boolean isAutoFill=false;//是否自动填充
    public HeartProgressBar(Context context) {
        super(context);
        init();
    }

    public HeartProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        if(mHeartBitmap==null){
            mHeartBitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
        }
        if(mHeartedBitmap==null){
            mHeartedBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.hearted);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        /*if(mHeartBitmap==null){
            mHeartBitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
            mHeartBitmap=Bitmap.createScaledBitmap(mHeartBitmap,getWidth(),getHeight(),true);
        }
        if(mHeartedBitmap==null){
            mHeartedBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.hearted);
            mHeartedBitmap=Bitmap.createScaledBitmap(mHeartedBitmap, getWidth(), getHeight(), true);
        }*/
        /*if(!isScale) {
            isScale=true;
            mHeartBitmap = Bitmap.createScaledBitmap(mHeartBitmap, getWidth(), getHeight(), true);
            mHeartedBitmap = Bitmap.createScaledBitmap(mHeartedBitmap, getWidth(), getHeight(), true);
        }*/
        final int width=getWidth();//mHeartBitmap.getWidth();
        final int height=getHeight();//mHeartBitmap.getHeight();
        float percent=progress*1.0f/max;//进度百分比
        if(percent>=1){
            percent=1;
        }
        canvas.save();
        canvas.drawBitmap(mHeartBitmap, 0, 0,mPaint);
        canvas.clipRect(0, height * (1 - percent), width, height);
        canvas.drawBitmap(mHeartedBitmap, 0, 0, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        //if(widthMode==MeasureSpec.AT_MOST){//layout_width=wrap_content
            //设置控件宽高跟图片一样
            setMeasuredDimension(mHeartBitmap.getWidth(), mHeartBitmap.getHeight());
        //}
        //setMeasuredDimension(getMeasureSize(widthMeasureSpec, true), getMeasureSize(heightMeasureSpec, true));
    }

    private int getMeasureSize(int spec,boolean isWidth){
        int size=MeasureSpec.getSize(spec);
        int mode=MeasureSpec.getMode(spec);
        if(mode==MeasureSpec.AT_MOST){
            if(isWidth) {
                size =mHeartBitmap.getWidth();
            }else{
                size=mHeartBitmap.getHeight();
            }
        }
        return size;
    }
    /**
     * 设置当前进度
     * @param progress
     */
    public void setProgress(int progress) {
        if(isAutoFill) return;
        this.progress = progress;
        if(!isFinish) {
            invalidate();
        }
        if(progress>=max){
            isFinish=true;
        }
    }

    /**
     * 是否完成
     * @return
     */
    public boolean isFinish() {
        return isFinish;
    }

    /**
     * 开启自动填充
     */
    public void startAutoFill(){
        isAutoFill=true;
        final int step=10;
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                progress += step;
                invalidate();
                if (progress >= max) {
                    isFinish = true;
                }
                if (!isFinish()) {
                    handler.postDelayed(this, 100 - progress);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        });
    }
}
