package com.App;

public class UserHistory
{
    public String username;
    public Integer steps;

    public UserHistory(String username, Integer steps)
    {
        this.username = username;
        this.steps = steps;
    }

    public String getName()
    {
        return username;
    }
    public Integer getStepsAmount()
    {
        return steps;
    }
}
