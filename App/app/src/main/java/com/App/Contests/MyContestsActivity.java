package com.App.Contests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.App.R;

public class MyContestsActivity extends AppCompatActivity {

    Button createContestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contests);

        createContestBtn = findViewById(R.id.btn_create_contest);

        createContestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateContestActivity.class);
                startActivity(i);
            }
        });
    }



}