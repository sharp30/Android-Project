package com.App.Contests;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.App.Contest;
import com.App.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class
CreateContestActivity extends AppCompatActivity {
    Button btnCreate;
    EditText etContestName;
    EditText tvDate;
    SeekBar sbPlayersAmount;
    EditText etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contest);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final SharedPreferences sp = getSharedPreferences("values",0);


        btnCreate = (Button)findViewById(R.id.btnCreate);
        etContestName = (EditText)findViewById(R.id.etName);
        tvDate = (EditText)findViewById(R.id.etDate);
        sbPlayersAmount = (SeekBar)findViewById(R.id.sbAmount);
        etAmount= (EditText)findViewById(R.id.etAmount);


        sbPlayersAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                etAmount.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        etAmount.addTextChangedListener(new TextWatcher() {
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
                int value = Integer.parseInt(etAmount.getText().toString());
                value = Math.max(1,value);
                value = Math.min(value,sbPlayersAmount.getMax());
                if(value != Integer.parseInt(etAmount.getText().toString()))
                    etAmount.setText(String.valueOf(value));
                sbPlayersAmount.setProgress(value);
            }
        });


        //check for valid name
        final Calendar cal = Calendar.getInstance();
        final Date today =cal.getTime();
        String myFormat = "dd/MM/yyyy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()

        {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                cal.set(Calendar.YEAR, i);
                cal.set(Calendar.MONTH, i1);
                cal.set(Calendar.DAY_OF_MONTH, i2);

                if(cal.getTime().compareTo(today) <= 0)
                {
                    Toast t = Toast.makeText(getApplicationContext(),"Invalid Date",Toast.LENGTH_LONG);
                    cal.setTime(today);
                    cal.add(Calendar.DATE, 1);
                    t.show();

                }

                tvDate.setText(sdf.format(cal.getTime()));
            }
        };



        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateContestActivity.this,date,cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Contest contest = new Contest(etContestName.getText().toString(),cal.getTime(),sbPlayersAmount.getProgress(),sp.getString("logged",""));
                //TODO: check if values are valid
                //TODO:check if name doesnt exist
                ref.child("contests").child(contest.getName()).setValue(contest);
            }
        });

        sbPlayersAmount.setProgress(1);

    }
}