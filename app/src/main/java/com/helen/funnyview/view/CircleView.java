package com.helen.funnyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * Helen 2015-05-13
 */
public class CircleView extends ImageView{
    private Bitmap mSrcBitmap;//ImageView设置的图像资源文件
    private Bitmap mOut;
    private Paint mPaint;//画笔
    public CircleView(Context context) {
        super(context);
        //init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //init();
    }

    private void init(){
        //setLayerType(LAYER_TYPE_SOFTWARE,null);//禁用硬件加速
        Drawable d=getDrawable();
        if(d==null) return;
        mSrcBitmap=((BitmapDrawable)d).getBitmap();
        //mOut=Bitmap.createBitmap(mSrcBitmap.getWidth(),mSrcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mSrcBitmap=Bitmap.createScaledBitmap(mSrcBitmap,getWidth(),getHeight(),true);//设置缩放
        mOut=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas=new Canvas(mOut);
        //Dst
        canvas.drawCircle(getWidth() / 2, getHeight() / 2,getWidth() / 2, mPaint);
        //canvas.drawCircle(mOut.getWidth() / 2, mOut.getHeight() / 2,mOut.getWidth() / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //Src
        canvas.drawBitmap(mSrcBitmap,0,0,mPaint);
        mPaint.setXfermode(null);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //只有在onDraw中才能获取到控件的宽高
        init();
        canvas.drawBitmap(mOut,0,0,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=Math.min(getMeasuredHeight(),getMeasuredWidth());
        //因为是要圆形图像，所以将控件的宽高设置为一样
        setMeasuredDimension(width,width);
    }
}
