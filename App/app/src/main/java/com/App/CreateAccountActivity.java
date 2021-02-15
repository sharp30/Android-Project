package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button createBtn;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        prefs = this.getSharedPreferences("values",MODE_PRIVATE);
        //initial
        createBtn = findViewById(R.id.btn_create_account);
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //todo: check if account details is good
                //todo: register the user to database

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("logged",etUsername.getText().toString());
                editor.apply();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });



    }
}