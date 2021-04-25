package com.App;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Date;


public class Dal extends SQLiteAssetHelper {

    public Dal(Context context) {

        super(context, "my_db.db", null, 1);
    }



    public Day getDay(String date)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = String.format("Select * from DAYS where date = '%s'",date);

        int steps = 0;
        float weight = 0;

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()) {
                steps = cursor.getInt(cursor.getColumnIndex("steps"));
                weight = cursor.getFloat(cursor.getColumnIndex("weight"));
        }
        else
            return new Day(date,50,0);


        return new Day(date,steps,weight);
    }

    public void addDay(Day day)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = String.format("INSERT INTO Days (date,steps,weight) (%s,%d,%f)",day.getDate(),day.getSteps(),day.getWeight());
        SQLiteStatement statement = db.compileStatement(sql);

        statement.execute();
    }

    public void updateDay(Day day)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = String.format("UPDATE Days SET steps = %d, weight = %f WHERE date = %s",day.getSteps(),day.getWeight(),day.getDate());
        SQLiteStatement statement = db.compileStatement(sql);

        statement.execute();

    }
}
