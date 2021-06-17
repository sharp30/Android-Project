package com.App;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlayersAdapter extends ArrayAdapter<UserHistory>
{
    Context context;
    List<UserHistory> players;
    public PlayersAdapter(Context context, int resource, int textViewResourceId, List<UserHistory> players)
    {
        super(context,resource,textViewResourceId,players);
        this.context = context;
        this.players =players;
    }

    public View getView(int position, View cellView, ViewGroup parent)
    {
        //TODO: fix design

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.player_layout,parent,false);

        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        TextView tvSteps = (TextView)view.findViewById(R.id.tvScore);

        tvName.setText(players.get(position).getName());
        tvSteps.setText(String.valueOf(players.get(position).getStepsAmount()));

        return view;
    }

}
