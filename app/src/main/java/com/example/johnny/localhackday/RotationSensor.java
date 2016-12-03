package com.example.johnny.localhackday;

import android.content.Context;
import android.graphics.PointF;
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
    private UnitVector direction;

    public RotationSensor(Context context, TextView label){
        super();

        this.context = context;
        this.manager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        this.label = label;
        this.direction = new UnitVector();

        manager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent e){
        this.direction.update(e.values[0], e.values[1]);

        //update view
        String text = new String(
                "x: " + Float.toString(e.values[0]) +
                "\ny: " + Float.toString(e.values[1]) +
                "\nz: " + Float.toString(e.values[2]) +
                "\n\nunit x: " + this.direction.getX() +
                "\nunit y: " + this.direction.getY()
        );
        this.label.setText(text);
    }

    public void onAccuracyChanged(Sensor s, int i){
        //do nothing
    }

    public boolean isAimedAtPlayer(PointF user, PointF target){
        UnitVector other = new UnitVector(user,target);
        return this.direction.isEqual(other);
    }

}
