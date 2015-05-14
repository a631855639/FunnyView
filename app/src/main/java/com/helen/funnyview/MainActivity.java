package com.helen.funnyview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.helen.funnyview.test.CircleViewActivity;
import com.helen.funnyview.test.HeartProgressBarActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testCircleView(View view){
        jump(CircleViewActivity.class);
    }

    public void testHeartProgressBar(View view){
        jump(HeartProgressBarActivity.class);
    }
    private void jump(Class<?> clzz){
        startActivity(new Intent(this,clzz));
    }
}
