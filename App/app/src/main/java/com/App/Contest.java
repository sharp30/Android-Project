package com.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contest {
    public String name;
    public Date startDate;
    public Date endDate;
    public int playersAmount;
    public List<String> players;
    public Boolean isClosed;

    public Contest() {
    }

    public Contest(Map<String,Object> values)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.name = (String)values.get("name");
        try
        {
            this.startDate = sdf.parse((String)values.get("startDate"));
        }
        catch (ParseException e)
        {
            this.startDate = Calendar.getInstance().getTime();
        }
        try
        {
            this.endDate = sdf.parse((String)values.get("endDate"));
        }
        catch (ParseException e)
        {
            this.endDate = Calendar.getInstance().getTime();
        }

        this.isClosed = (Boolean)values.get("isClosed");
        this.playersAmount = ((Long)values.get("playersAmount")).intValue();

        this.players = (ArrayList<String>)values.get("players");

    }
    public Contest(String name, Date date, int playersNum)
    {

        this.name = name;
        this.startDate = date;
        this.isClosed = false;
        this.playersAmount = playersNum;
        players = new ArrayList<String>();

        initEndDate();
    }


    public Contest(String name, Date date, int playersNum, String creator) {

        this.name = name;
        this.startDate = date;
        this.playersAmount = playersNum;
        players = new ArrayList<String>();
        players.add(creator);
        this.isClosed = false;

        initEndDate();
    }

    public Contest(String name, Date date, int playersNum, String creator, List<String> players) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.name = name;
        this.startDate = date;
        this.playersAmount = playersNum;
        this.isClosed = false;

        players = new ArrayList<String>();
        players.add(creator);

        initEndDate();
    }

    private void initEndDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.startDate);
        c.add(Calendar.DATE,7); //7 days;
        this.endDate = c.getTime();
    }

    public String getName() {
        return this.name;
    }

    public String getStartDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        return sdf.format(this.startDate);
    }

    public String getEndDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        return sdf.format(this.endDate);
    }


    public int getPlayersAmount() {
        return this.playersAmount;
    }

    public void addPlayer(String player) {
        this.players.add(player);
    }

    public List<String> getPlayers() {
        return this.players;
    }

    public boolean isJoinable(Date today, String name)
    {
        return this.startDate.compareTo(today) > 0 && this.playersAmount > this.players.size() && !this.players.contains(name);
    }

    public boolean isMine(Date today, String name)
    {
        return this.players.contains(name) && !this.isClosed;
    }
}
