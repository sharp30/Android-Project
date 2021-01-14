package com.App;

import android.content.Context;
import android.widget.ArrayAdapter;

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
}
