package com.App.Contests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.App.Contest;
import com.App.ContestsAdapter;
import com.App.R;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExploreContestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_contests);

        ListView listView = findViewById(R.id.list);

        ArrayList<Contest> contests = new ArrayList<Contest>();
        contests.add(new Contest("first",new Date(),10));
        contests.add(new Contest("second", new Date(),5));
        contests.add(new Contest("third", new Date(),4));

        ContestsAdapter adapter = new ContestsAdapter(this,0,0,contests);
        listView.setAdapter(adapter);
    }
}