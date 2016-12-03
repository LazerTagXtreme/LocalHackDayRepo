package com.example.johnny.localhackday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RotationSensor rotationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the rotation sensor listener
        rotationSensor = new RotationSensor(this);
    }

    public void GoToLocation(View view){
        Intent startNewActivity = new Intent (this, ShowLocation.class);
        startActivity(startNewActivity);
        finish();
    }
}
