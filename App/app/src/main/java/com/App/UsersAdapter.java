package com.App;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User>
{
    Context context;
    List<User> users;
    public UsersAdapter(Context context, int resource, int textViewResourceId, List<User> users)
    {
        super(context,resource,textViewResourceId,users);
        this.context = context;
        this.users = users;
    }

    public View getView(int position, View cellView, ViewGroup parent)
    {
        //TODO: fix design

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.user_layout,parent,false);
        TextView tvRank = (TextView)view.findViewById(R.id.tvRank);
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        TextView tvScore = (TextView)view.findViewById(R.id.tvScore);

        tvRank.setText(String.valueOf(position+1) + ". ");
        tvName.setText(users.get(position).username);
        tvScore.setText(String.valueOf(users.get(position).score));

        return view;
    }

}
