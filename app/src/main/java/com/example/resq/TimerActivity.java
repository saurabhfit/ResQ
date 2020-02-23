package com.example.resq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    TextView timerText;
    Button cancelBtn;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerText = (TextView)findViewById(R.id.timer);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        reverseTimer(10, timerText);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimerActivity.this, MainActivity.class));
                finish();
                timer.cancel();
            }
        });

    }

    public void reverseTimer(int Seconds, final TextView tv){

        timer = new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds = seconds % 60;
                tv.setText(String.format("%02d", seconds));
            }

            public void onFinish() {
                synchronized (this){
                    tv.setText("call");
                    Toast.makeText(TimerActivity.this, "Requesting medical assistance", Toast.LENGTH_SHORT).show();
                    cancelBtn.setVisibility(View.INVISIBLE);
                    requestHelp();
                }
            }
        };
        timer.start();
    }


    public void requestHelp(){
        startActivity(new Intent(TimerActivity.this, MapsActivity.class));
        finish();
    }

}
