package com.App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends Activity implements SensorEventListener {

    ImageButton historyBtn;

    SensorManager sensorManager;
    Sensor stepCounter;

    ProgressBar pbStep;
    TextView tvStepCount;
    TextView tvTrophyAmount;

    int stepCount;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sp;
    DateFormat df;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = getSharedPreferences("values",0);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED)
        {
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
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
                    case R.id.weight_nav:
                        next = new Intent(getApplicationContext(),EditWeightActivity.class);
                        startActivity(next);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        //initials
        tvTrophyAmount  = (TextView)findViewById(R.id.tvTrophy);
        historyBtn = (ImageButton) findViewById(R.id.ibHistory);
        pbStep = (ProgressBar)findViewById(R.id.progress_bar);
        //on clicks
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);

                saveCount();
                startActivity(i);
            }
        });


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (stepCounter == null)
            //cant find step counter sensor
            return;

        tvStepCount = findViewById(R.id.tvStepsAmount);

        pbStep.setMax(sp.getInt("steps_target",5000));        //other option - save just today - and others on sqldb
        df = new SimpleDateFormat("dd/MM/yyyy");



        stepCount = sp.getInt("steps",0);
        updateProgressBar();

        AlarmReceiver.createAlarm(getApplicationContext());


       ref =  FirebaseDatabase.getInstance().getReference();
       closeContests();
       Query q = ref.child("users").child(sp.getString("logged","")).child("score");

       q.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot)
           {
                Integer value = snapshot.getValue(Integer.class);
                tvTrophyAmount.setText(value.toString());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            stepCount++;
           updateProgressBar();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    //when activity is finally active
    protected void onResume() {
        super.onResume();
        //if (stepCounter != null)
        sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if(stepCount == 0)
        {
            stepCount = sp.getInt("steps",0);
        }
        updateProgressBar();


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (stepCounter != null) {
            sensorManager.unregisterListener(this, stepCounter);
            //stepCounter = null;
        }
        saveCount();
    }
    protected void saveCount()
    {
        SharedPreferences.Editor editor = sp.edit() ;
        editor.putInt("steps",stepCount);
        editor.apply();

    }
    protected void updateProgressBar()
    {
        pbStep.setProgress(stepCount);
        tvStepCount.setText(String.valueOf(stepCount));
    }

    public void onBackPressed()
    {
    }

    public void closeContests()
    {
        Query q = ref.child("contests");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Date today = Calendar.getInstance().getTime();

                for (DataSnapshot child : snapshot.getChildren()) {

                    Contest contest = new Contest((Map<String, Object>) child.getValue());
                    if (contest.isFinished(today))
                    {
                        HashMap<String,Integer> players = new HashMap<>();
                        for(String player : contest.getPlayers())
                        {
                            players.put(player, 0);
                        }
                        calcPlayers(players,contest.startDate,contest.endDate);
                        Map.Entry<String, Integer> maxEntry = null;

                        for (Map.Entry<String, Integer> entry : players.entrySet()) {
                            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                                maxEntry = entry;
                            }
                        }
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("isClosed", true);

                        final String winner = maxEntry.getKey();
                        ref.child("contests").child(contest.name).child("isClosed").setValue(true);

                        Query q = ref.child("users").child(winner);

                        q.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                ref.child("users").child(winner).child("score").setValue(((Long)snapshot.child("score").getValue()).intValue() +1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void calcPlayers(final HashMap<String, Integer> players, Date startDate, Date endDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        String end = sdf.format(cal.getTime());
        cal.setTime(startDate);


        final ArrayList<Date> days = new ArrayList<>();
        while (end.compareTo(sdf.format(cal.getTime())) >= 0) {
            days.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }

        for (final String name : players.keySet()) {
            {
                //calculate all steps in this week
                Query q = ref.child("users").child(name).child("history").orderByKey().limitToFirst(days.size());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer sum = 0;
                        for (Date day : days) {
                            //int index = snapshot.indexOf(day);
                            String a = sdf.format(day);
                            if (snapshot.hasChild(sdf.format(day))) {
                                sum += ((Long) snapshot.child(sdf.format(day)).child("steps").getValue()).intValue();
                            }
                        }
                        players.put(name, sum);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
    }

}