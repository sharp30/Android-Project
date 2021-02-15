package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button loginBtn;
    Button createAccountBtn;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        String logged = prefs.getString("logged",null);

        if(logged != null)
        {
            Intent next = new Intent(this,HomeActivity.class);
            startActivity(next);
        }

        //todo: check if user is already logged - using shared preference
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.btn_login);
        createAccountBtn = findViewById(R.id.btn_register);

        //initials

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                //todo: check if login is ok!

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("logged",etPassword.getText().toString());
                editor.apply();
                Intent next = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(next);
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(),CreateAccountActivity.class);
                startActivity(next);
            }
        });
    }
}