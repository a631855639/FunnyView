package com.helen.funnyview.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.helen.funnyview.R;
import com.helen.funnyview.view.HeartProgressBar;

/**
 * Created by Helen on 2015/5/14 16:56.
 * TODO
 */
public class HeartProgressBarActivity extends Activity{
    private HeartProgressBar bar;
    private Handler handler=new Handler();
    private int progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_heart_progress_bar);
        bar=(HeartProgressBar)findViewById(R.id.bar);
        progress=bar.getProgress();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress+=5;
                bar.setProgress(progress);
                if(!bar.isFinish()) {
                    handler.postDelayed(this, 500);
                }else{
                    handler.removeCallbacks(this);
                }
            }
        },3000);
        //bar.startAutoFill();
    }
}
