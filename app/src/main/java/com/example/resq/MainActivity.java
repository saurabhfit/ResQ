package com.example.resq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Switch aSwitch;
    Intent sensorServiceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = (Switch)findViewById(R.id.switch1);

        sensorServiceIntent = new Intent(getApplicationContext(), SensorService.class);

        findViewById(R.id.signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                stopService(sensorServiceIntent);
                finish();
            }
        });

        findViewById(R.id.requestMed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        if(isMyServiceRunning(SensorService.class))
            aSwitch.setChecked(true);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enableDriveMode();
                }else{
                    disableDriveMode();
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    private void enableDriveMode(){
        Toast.makeText(this, "Drive Mode ENABLED", Toast.LENGTH_SHORT).show();
        aSwitch.setText("Drive Mode On");
        startService(sensorServiceIntent);
    }
    
    private void disableDriveMode(){
        Toast.makeText(this, "Drive Mode DISABLED", Toast.LENGTH_SHORT).show();
        aSwitch.setText("Drive Mode Off");
        stopService(sensorServiceIntent);
    }

}
