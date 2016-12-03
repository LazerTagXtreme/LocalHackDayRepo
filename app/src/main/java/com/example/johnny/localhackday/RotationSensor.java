package com.example.johnny.localhackday;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Kathy on 2016-12-03.
 */

public class RotationSensor implements SensorEventListener{
    private SensorManager manager;
    private Sensor sensor;
    private Context context;

    public RotationSensor(Context context){
        super();

        this.context = context;
        this.manager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void onSensorChanged(SensorEvent e){
        
    }

    public void onAccuracyChanged(Sensor s, int i){
        //do nothing
    }
}
