package com.App.Contests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MyContestsActivity extends AppCompatActivity {

    Button createContestBtn;
    ListView listView;
    ArrayList<Contest> contests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contests);

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
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String name = sp.getString("logged","");

            contests.clear();
            for(DataSnapshot child : snapshot.getChildren())
            {

                Contest contest =new Contest((Map<String,Object>)child.getValue());
                if(contest.isMine(today,name))
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

        createContestBtn = findViewById(R.id.btn_create_contest);

        createContestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateContestActivity.class);
                startActivity(i);
            }
        });
    }

    private void refresh()
    {
        ContestsAdapter adapter = new ContestsAdapter(this,0,0,contests);
        listView.setAdapter(adapter);
    }

}