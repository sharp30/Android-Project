package com.App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeActivity extends Activity implements SensorEventListener {

    ImageButton historyBtn;

    SensorManager sensorManager;
    Sensor stepCounter;

    ProgressBar pbStep;
    TextView tvStepCount;
    int stepCount;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sp;
    DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED)
        {
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        sp = getSharedPreferences("steps",0);
        bottomNavigationView = findViewById(R.id.btn_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent next = null;
                switch(item.getItemId())
                {
                    case R.id.contests_nav:
                        next = new Intent(getApplicationContext(),ContestsActivity.class);
                        startActivity(next);
                        break;
                    case R.id.profile_nav:
                        next = new Intent(getApplicationContext(),MyDetailsActivity.class);
                        startActivity(next);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        //initials
        historyBtn = findViewById(R.id.ibHistory);

        //on clicks
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepCounter == null)
            //cant find step counter sensor
            return;

        tvStepCount = findViewById(R.id.tvStepsAmount);
        pbStep = findViewById(R.id.progress_bar);

        //other option - save just today - and others on sqldb
         df = new SimpleDateFormat("dd/MM/yyyy");

        stepCount = sp.getInt(df.format(new Date()),0);
        pbStep.setProgress(stepCount);
        tvStepCount.setText(String.valueOf(stepCount));





    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            stepCount++;
            //stepCount = (int) sensorEvent.values[0];
            pbStep.setProgress(stepCount);
            tvStepCount.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    //when activity is finally active
    protected void onResume() {
        super.onResume();
        if (stepCounter != null)
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        stepCount = sp.getInt(df.format(new Date()),0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (stepCounter != null) {
            sensorManager.unregisterListener(this, stepCounter);
            stepCounter = null;
        }
        SharedPreferences.Editor editor = sp.edit() ;
        editor.putInt(df.format(new Date()),stepCount);
        editor.apply();
    }
}