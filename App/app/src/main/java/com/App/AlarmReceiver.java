package com.App;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

//This is the broadcast receiver you create where you place your logic once the alarm is run. Once the system realizes your alarm should be run, it will communicate to your app via the BroadcastReceiver. You must implement onReceive.
public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Dal dal = new Dal(context);
        DateFormat df =new SimpleDateFormat("dd/MM/yyyy");
        SharedPreferences sp = context.getSharedPreferences("values",0);

        String date = df.format(new Date());
        int steps = sp.getInt("steps",0);
        float weight = sp.getFloat("weight",0);

        dal.addDay(new Day(date,steps,weight));

        //initialize steps count
        sp.edit().putInt("steps",0);

    }
}
