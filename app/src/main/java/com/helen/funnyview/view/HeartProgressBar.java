package com.helen.funnyview.view;

import android.content.Context;
import android.content.res.TypedArray;
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
    private Bitmap mHeartedBitmap;//实心图
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private int progress=0;//当前进度
    private int max=100;//最大进度
    private boolean isFinish=false;//是否填充完成
    private boolean isAutoFill=false;//是否自动填充
    public HeartProgressBar(Context context) {
        super(context);
        init();
    }

    public HeartProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.HeartProgressBar);
        progress=a.getInteger(R.styleable.HeartProgressBar_progress,0);
        max=a.getInteger(R.styleable.HeartProgressBar_max,100);
        if(max<=0){
            max=100;
        }
        a.recycle();
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
        final int width=getWidth();//mHeartBitmap.getWidth();
        final int height=getHeight();//mHeartBitmap.getHeight();
        float percent=progress*1.0f/max;//进度百分比
        if(percent>=1){
            percent=1;
        }
        canvas.save();
        //绘制空心图
        canvas.drawBitmap(mHeartBitmap, 0, 0,mPaint);
        //计算绘制实心图的范围
        canvas.clipRect(0, height * (1 - percent), width, height);
        //绘制实心图
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
    public int getProgress() {
        return progress;
    }
    /**
     * 是否完成
     * @return
     */
    public boolean isFinish() {
        return isFinish;
    }

    public boolean isAutoFill() {
        return isAutoFill;
    }

    /**
     * 设置最大进度值
     */
    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
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
