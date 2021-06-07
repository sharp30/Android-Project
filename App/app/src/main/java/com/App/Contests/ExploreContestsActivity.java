package com.App.Contests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.App.Contest;
import com.App.ContestsAdapter;
import com.App.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploreContestsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Contest> contests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_contests);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.list);
        contests = new ArrayList<Contest>();
        final SharedPreferences sp = getSharedPreferences("values",0);


        Query q = ref.child("contests");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Date today = Calendar.getInstance().getTime();
                String name = sp.getString("logged","");

                contests.clear();
                for(DataSnapshot child : snapshot.getChildren())
                {
                    
                    Contest contest =new Contest((Map<String,Object>)child.getValue());
                    if(contest.isJoinable(today,name))
                    {
                        contests.add(contest);
                    }
                }
                refresh();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ContestsAdapter adapter = new ContestsAdapter(this,0,0,contests);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Contest  selectedContest = contests.get(i);

                SharedPreferences sp = getApplicationContext().getSharedPreferences("values",0);

                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


                selectedContest.players.add(sp.getString("logged",""));
                ref.child("contests").child(selectedContest.getName()).child("players").setValue(selectedContest.players);

                //after adding
                Intent intent = new Intent(getApplicationContext(),ViewContestActivity.class);
                intent.putExtra("contest_name",selectedContest.getName());

                startActivity(intent);
            }
        });


    }

    private void refresh()
    {
        ContestsAdapter adapter = new ContestsAdapter(this,0,0,contests);
        listView.setAdapter(adapter);
    }
}