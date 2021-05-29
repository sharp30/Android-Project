package com.App;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditWeightActivity extends AppCompatActivity {

    private TextView mTextView;
    SharedPreferences sp;
    SeekBar seekBar;
    EditText editText;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_weight);

        sp = this.getSharedPreferences("values",0);

        //find views
        seekBar = findViewById(R.id.sbSteps);
        editText = findViewById(R.id.edWeight);

        btnUpdate = findViewById(R.id.btnUpdate);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                editText.setText(Integer.toString(i));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
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
                        seekBar.setProgress(Integer.parseInt(editText.getText().toString()));
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View view)
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        float val = Float.parseFloat(editText.getText().toString());
                        editor.putFloat("weight", val);
                        editor.commit();
                        finish();
                    }
                });
        seekBar.setProgress((int)sp.getFloat("weight", 0));

    }
}