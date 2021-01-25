package com.App;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContestsAdapter extends ArrayAdapter<Contest>
{
    Context context;
    List<Contest> contests;
    public ContestsAdapter(Context context, int resource, int textViewResourceId, List<Contest> contests)
    {
        super(context,resource,textViewResourceId,contests);
        context = context;
        this.contests =contests;
    }
    public View getView(int position, View cellView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.contest_layout,parent,false);

        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        TextView tvPlayersNum =  (TextView)view.findViewById(R.id.tvAmount);

        Contest temp = contests.get(position);

        tvName.setText(temp.getName());
        tvDate.setText(temp.getDate().toString());
        tvName.setText(String.valueOf(temp.getPlayersAmount()));

        return view;
    }

}
