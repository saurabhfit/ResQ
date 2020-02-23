package com.example.resq;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class SensorService extends Service implements SensorEventListener {

    private static final String TAG = "SensorService";
    double accX = 0,
            accY = 0,
            accZ = 0;
    private double maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE, maxRes = Integer.MIN_VALUE;
    private double minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE, minRes = Integer.MAX_VALUE;
//    private double gravityX= 0, gravityY=0, gravityZ=0;
    private SensorManager sensorManager;
    private Sensor accelerometer;  //, gravitymeter;

    private Ringtone r;

    @Override
    public void onCreate() {
        System.out.println("ENABLED");
        super.onCreate();

        Log.d(TAG, "onCreate: Initializing sensor services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        gravitymeter = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, gravitymeter, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");
        resetValues();

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
//        sensorManager.unregisterListener(this, gravitymeter);
        sensorManager.unregisterListener(this, accelerometer);
        System.out.println("Sensor Stopped");
        resetValues();

        if(r.isPlaying())
            r.stop();
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this){
//            getGravity(event);
            getAcceleration(event);
        }
//        accX -= gravityX;
//        accY -= gravityY;
//        accZ -= gravityZ;

        accX = Math.round(accX);
        accY = Math.round(accY);
        accZ = Math.round(accZ);

        int aX = (int)accX;
        int aY = (int)accY;
        int aZ = (int)accZ;

//        double res = Math.ceil(Math.sqrt((accX*accX)+(accY*accY)+(accZ*accZ)));

        int res = (int) Math.ceil(Math.sqrt((aX*aX)+(aY*aY)+(aZ*aZ)));


        res = res / 10;
        System.out.println("G-FORCE="+res);
        if(res>=3){
            resetValues();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.splash_logo)
                    .setContentTitle("ResQ")
                    .setContentText("IMPACT DETECTED")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1233, builder.build());

            Intent dialogIntent = new Intent(this, TimerActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(dialogIntent);

            try {
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    public void getGravity(SensorEvent event){
//        Sensor sensor = event.sensor;
//        if(sensor.getType()!=Sensor.TYPE_GRAVITY)
//            return;
//        gravityZ = event.values[2];
//        gravityY = event.values[1];
//        gravityX = event.values[0];
//    }

    public void getAcceleration(SensorEvent event){
        Sensor sensor = event.sensor;
        if(sensor.getType()!=Sensor.TYPE_ACCELEROMETER)
            return;
        accX = event.values[0];
        accY = event.values[1];
        accZ = event.values[2];
    }

    public void resetValues(){
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        maxZ = Integer.MIN_VALUE;
        maxRes = Integer.MIN_VALUE;
        minX = Integer.MAX_VALUE; minY = Integer.MAX_VALUE; minZ = Integer.MAX_VALUE; minRes = Integer.MAX_VALUE;
//        gravityX= 0; gravityY=0; gravityZ=0;
    }
}

