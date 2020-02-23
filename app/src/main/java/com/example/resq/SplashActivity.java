package com.example.resq;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashLogo = (ImageView) findViewById(R.id.splash_logo);

        ObjectAnimator logoAnim = ObjectAnimator.ofFloat(splashLogo, "alpha", 0f, 1f);
        AnimatorSet aSet = new AnimatorSet();
        aSet.setDuration(1500);
        aSet.playTogether(logoAnim);
        aSet.start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                woke();
            }
        }).start();
    }
    public void woke(){
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
