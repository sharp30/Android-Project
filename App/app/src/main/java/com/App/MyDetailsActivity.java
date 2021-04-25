package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MyDetailsActivity extends AppCompatActivity  {

    SeekBar height;
    SeekBar target;
    SharedPreferences sp;
    TextView tvHeight;
    TextView tvTarget;
    //SeekBar weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);


        sp = this.getSharedPreferences("values", Context.MODE_PRIVATE);

        target =(SeekBar)findViewById(R.id.sbTarget);

        tvTarget = (TextView)findViewById(R.id.tvTarget);
        tvHeight = (TextView)findViewById(R.id.tvHeight);

        //inital values
        target.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                tvTarget.setText(Integer.toString(i));


                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("target",i);
                editor.commit();

                //move textView
                int width = seekBar.getWidth()
                        - seekBar.getPaddingLeft()
                        - seekBar.getPaddingRight();

                float thumbPos =width - width * (seekBar.getProgress() / (float)seekBar.getMax());

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        target.setProgress(sp.getInt("target",5000));

        height = (SeekBar)findViewById(R.id.sbHeight);
        //weight =(SeekBar)findViewById(R.id.sbWeight);
    }
}