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
}


public Day getDay(Date date)
{

}

public void addDay(Day day)
{

}

public void updateDay(Day day)
