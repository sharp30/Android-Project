package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

    Button contestsBtn;
    Button myDetailsBtn;
    Button historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initials
        contestsBtn = findViewById(R.id.btn_contests);
        myDetailsBtn = findViewById(R.id.btn_my_details);
        historyBtn = findViewById(R.id.btn_history);

        //on clicks
        contestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ContestsActivity.class);
                startActivity(i);
            }
        });

        myDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MyDetailsActivity.class);
                startActivity(i);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),HistoryActivity.class);
                startActivity(i);
            }
        });


    }
}