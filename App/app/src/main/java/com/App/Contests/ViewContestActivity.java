package com.App.Contests;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.App.Contest;
import com.App.ContestsActivity;
import com.App.ContestsAdapter;
import com.App.PlayersAdapter;
import com.App.R;
import com.App.UserHistory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewContestActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private ListView lvPlayers;
    private HashMap<String, Integer> players;
    private Contest contest;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contest);
        ref = FirebaseDatabase.getInstance().getReference();

        final Intent i = this.getIntent();
        final String contestName = i.getStringExtra("contest_name");

        tvTitle = (TextView) findViewById(R.id.tvName);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        lvPlayers = (ListView) findViewById(R.id.lvPlayers);

        players = new HashMap<String, Integer>();


        tvTitle.setText(contestName);


        Query q = ref.child("contests").child(contestName);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contest = new Contest((Map<String, Object>) snapshot.getValue());

                tvStartDate.setText(contest.getStartDate());
                tvEndDate.setText(contest.getEndDate());

                //check if started
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                final Calendar cal = Calendar.getInstance();
                String today = sdf.format(cal.getTime());


                cal.setTime(contest.startDate);

                boolean isStarted = today.compareTo(sdf.format(cal.getTime())) >= 0;

                final ArrayList<Date> days = new ArrayList<>();
                while (today.compareTo(sdf.format(cal.getTime())) >= 0) {
                    days.add(cal.getTime());
                    cal.add(Calendar.DATE, 1);
                }

                for (final String name : contest.getPlayers()) {
                    Log.e("player:", name);
                    if(!isStarted)
                    {
                        players.put(name, 0);
                        refresh();

                        continue;
                    }
                    //calculate all steps in this week
                    Query q = ref.child("users").child(name).child("history").orderByKey().limitToFirst(days.size());
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int sum = 0;
                            for (Date day : days) {
                                //int index = snapshot.indexOf(day);
                                String a = sdf.format(day);
                                if (snapshot.hasChild(sdf.format(day))) {
                                    sum += ((Long) snapshot.child(sdf.format(day)).child("steps").getValue()).intValue();
                                }
                            }
                            players.put(name, sum);
                            refresh();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            players.put(name, 0);
                            refresh();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void refresh() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(players.entrySet());
        List<UserHistory> ordered = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            ordered.add(new UserHistory(entry.getKey(), entry.getValue()));
            PlayersAdapter adapter = new PlayersAdapter(this, 0, 0, ordered);
            lvPlayers.setAdapter(adapter);

        }
    }
    public void onBackPressed()
    {
        Intent i = new Intent(this, ContestsActivity.class);
        startActivity(i);
    }


};