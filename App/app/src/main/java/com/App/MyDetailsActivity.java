package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MyDetailsActivity extends AppCompatActivity  {

    SeekBar steps;
    SeekBar weight;
    SharedPreferences sp;
    TextView tvWeight;
    TextView tvSteps;
    EditText edSteps;
    EditText edWeight;
    //SeekBar weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);


        sp = this.getSharedPreferences("values", Context.MODE_PRIVATE);

        weight =(SeekBar)findViewById(R.id.sbWeight);
        steps =(SeekBar)findViewById(R.id.sbHeight);

        tvWeight = (TextView)findViewById(R.id.tvWeight);
        tvSteps = (TextView)findViewById(R.id.tvScore);

        edWeight = (EditText)findViewById(R.id.edWeight);
        edSteps = (EditText)findViewById(R.id.edSteps);
        //inital values
        weight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                edWeight.setText(Integer.toString(i));


                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("weight_target",i);
                editor.commit();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        steps.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                edSteps.setText(Integer.toString(i));

                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("steps_target",i);
                editor.commit();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        edWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }
            @Override
            public void afterTextChanged(Editable editable)
            {
                //check if int
                weight.setProgress(Integer.parseInt(editable.toString()));
            }
        });

        edSteps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //check if int
                steps.setProgress(Integer.parseInt(editable.toString()));
            }
        });
        steps.setProgress(sp.getInt("steps_target",5000));
        weight.setProgress((int)sp.getInt("weight_target",50));
    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);

    }

}