package com.App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class CreateAccountActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button createBtn;
    SharedPreferences prefs;
    //DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        prefs = this.getSharedPreferences("values",MODE_PRIVATE);
        //initial
        createBtn = findViewById(R.id.btn_create_account);
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);


        if(prefs.getString("logged","") != "")
        {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        }

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //todo: check if account details is good
                //todo: register the user to database

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
                            ref.child("users").child(etUsername.getText().toString()).child("height").setValue(70);
                            //FireBaseHelper.addUser(new User(etUsername.getText().toString(),100));
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("logged",etUsername.getText().toString());
                            editor.putInt("steps",0);
                            editor.putFloat("weight",80);//TODO: update here
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });



    }
}