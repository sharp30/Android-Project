package com.App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class CreateAccountActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etHeight;
    SeekBar sbHeight;
    Button createBtn;
    SharedPreferences prefs;
    //DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        prefs = this.getSharedPreferences("values",MODE_PRIVATE);
        //initial
        createBtn = findViewById(R.id.btn_create_account);
        etUsername = findViewById(R.id.etUserName);
        etHeight = findViewById(R.id.etHeight);
        sbHeight = findViewById(R.id.sbStesps);

        final int MIN_HEIGHT= 120;
        final int MAX_HEIGHT= 220;
        if(prefs.getString("logged","") != "")
        {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        }

        sbHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                etHeight.setText(Integer.toString(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer val;
                try
                { val = Integer.parseInt(etHeight.getText().toString());

                } catch (NumberFormatException e) {
                    val = MIN_HEIGHT;
                }

                if (val < MIN_HEIGHT)
                {
                    sbHeight.setProgress(MIN_HEIGHT);
                }
                else if (val > MAX_HEIGHT)
                {
                    sbHeight.setProgress(MAX_HEIGHT);
                }
                else
                {
                    sbHeight.setProgress(Integer.parseInt(val.toString()));
                }
            }
        });


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Query q = ref.child("users").child(etUsername.getText().toString());//.orderByChild("username").equalTo(etUsername.getText().toString())
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()  || etUsername.getText().toString() == "")
                        {
                            Toast t = Toast.makeText(getApplicationContext(),"Username exists", Toast.LENGTH_LONG);
                            t.show();
                        }
                        else
                        {
                            User u = new User(etUsername.getText().toString(),sbHeight.getProgress());
                            ref.child("users").child(etUsername.getText().toString()).setValue(u.toMap());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("logged",etUsername.getText().toString());
                            editor.putInt("steps",0);
                            editor.putFloat("weight",80);
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(),MyDetailsActivity.class);
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
            }
        });

        sbHeight.setProgress(170);

    }
}