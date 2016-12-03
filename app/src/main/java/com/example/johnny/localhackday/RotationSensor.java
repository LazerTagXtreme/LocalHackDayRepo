package com.example.johnny.localhackday;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

/**
 * Created by Kathy on 2016-12-03.
 */

public class RotationSensor implements SensorEventListener{
    private SensorManager manager;
    private Sensor sensor;
    private Context context;
    private TextView label;

    public RotationSensor(Context context, TextView label){
        super();
        this.context = context;
        this.manager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        this.label = label;
    }

    public void onSensorChanged(SensorEvent e){
        this.label.setText(Float.toString(e.values[0]));
    }

    public void onAccuracyChanged(Sensor s, int i){
        //do nothing
    }
}
