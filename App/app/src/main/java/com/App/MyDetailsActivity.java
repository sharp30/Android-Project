package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

public class MyDetailsActivity extends AppCompatActivity {

    SeekBar height;
    SeekBar weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);

        height = (SeekBar)findViewById(R.id.sbHeight);
        weight =(SeekBar)findViewById(R.id.sbWeight);

    }
}