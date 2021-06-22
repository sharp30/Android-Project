package com.App;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

//This is the broadcast receiver you create where you place your logic once the alarm is run. Once the system realizes your alarm should be run, it will communicate to your app via the BroadcastReceiver. You must implement onReceive.
public class AlarmReceiver extends BroadcastReceiver
{
    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "alarm.running") {

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            SharedPreferences sp = context.getSharedPreferences("values", 0);
            String date = df.format(new Date());
            int steps = sp.getInt("steps", 0);
            float weight = sp.getFloat("weight", 0);
            Map<String, Long> map = new HashMap<String, Long>();
            map.put("steps", (long) steps);
            map.put("weight", (long) weight);
            ref.child("users").child(sp.getString("logged", "")).child("history").child(date).setValue(map);

            //initialize steps count
            sp.edit().putInt("steps", 0);
        }
    }
    public static void createAlarm(Context context)
    {
        int DATA_FETCHER_RC = 123;
        //Create an alarm manager
        AlarmManager mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //just before midnight
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,58);

        //Create an intent that points to the receiver. The system will notify the app about the current time, and send a broadcast to the app
        Intent newIntent = new Intent(context, AlarmReceiver.class);
        newIntent.setAction("alarm.running");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DATA_FETCHER_RC,newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Also set the interval using the AlarmManager constants
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
