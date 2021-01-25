package com.App.Contests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.App.Contest;
import com.App.ContestsAdapter;
import com.App.R;

import java.sql.Date;
import java.util.ArrayList;

public class ExploreContestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_contests);

        ListView listView = findViewById(R.id.list);

        ArrayList<Contest> contests = new ArrayList<Contest>();
        contests.add(new Contest("first", Date.valueOf("15.11.2020"),10));
        contests.add(new Contest("second", Date.valueOf("10.11.2020"),5));
        contests.add(new Contest("third", Date.valueOf("15.10.2020"),4));

        ContestsAdapter adapter = new ContestsAdapter(this,0,0,contests);
        listView.setAdapter(adapter);
    }
}