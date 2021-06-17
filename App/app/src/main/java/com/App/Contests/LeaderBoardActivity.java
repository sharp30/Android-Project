package com.App.Contests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.App.R;
import com.App.User;
import com.App.UsersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {

    ArrayList<User> users;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        lv = (ListView)findViewById(R.id.lv);

        //TODO: fill here

        users = new ArrayList<User>();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("users").orderByChild("score").limitToFirst(5);
        q.addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot child : snapshot.getChildren())
                {
                    User u = new User(child.getKey(),(Map<String, Object>)child.getValue());
                    users.add(u);

                }
                refresh();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void refresh()
    {
        UsersAdapter adapter = new UsersAdapter(this,0,0,users);
        lv.setAdapter(adapter);
    }
}