package com.App;

import java.util.Date;

public class Contest
{
    protected String name;
    protected Date date;
    public int playersAmount;

    public Contest(String name, Date date,int playersNum)
    {
        this.name = name;
        this.date = date;
        this.playersAmount = playersNum;

    }

    public String getName()
    {
        return this.name;
    }

    public Date getDate()
    {
        return this.date;
    }

    public int getPlayersAmount()
    {
        return this.playersAmount;
    }
}
