package com.App;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FireBaseHelper
{
    public static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static Boolean addUser(User user)
    {
        //check if username exists
        //Query q  = ref.child("users").orderByChild("username").equalTo(user.username);
/*        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })*/
        ref.child("users").push().setValue(user);
        return true;
    }
}
