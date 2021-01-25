package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.App.Contests.ExploreContestsActivity;
import com.App.Contests.LeaderBoardActivity;
import com.App.Contests.MyContestsActivity;

public class ContestsActivity extends AppCompatActivity {

    Button exploreBtn;
    Button leaderBoardBtn;
    Button myContestsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contests);

        exploreBtn = findViewById(R.id.btn_explore);
        leaderBoardBtn = findViewById(R.id.btn_leaderBoard);
        myContestsBtn = findViewById(R.id.btn_myContests);

        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ExploreContestsActivity.class);
                startActivity(i);
            }
        });
        leaderBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                startActivity(i);
            }
        });
        myContestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MyContestsActivity.class);
                startActivity(i);
            }
        });


    }
}