package com.App;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public void onReceive(Context context, Intent intent)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        DateFormat df =new SimpleDateFormat("ddMMyyyy");
        SharedPreferences sp = context.getSharedPreferences("values",0);
        String date = df.format(new Date());
        int steps = sp.getInt("steps",0);
        float weight = sp.getFloat("weight",0);
        Map<String,Long> map =new HashMap<String,Long>();
        map.put("steps",(long)steps);
        map.put("weight",(long)weight);
        ref.child("users").child(sp.getString("logged","")).child("history").child(date).setValue(map);

        //initialize steps count
        sp.edit().putInt("steps",0);

    }
}
