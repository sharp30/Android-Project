package com.App;

import java.util.Date;

public class Day
{
    protected String date;
    protected int steps;
    protected float weight;

    public Day(String date, int steps, float weight ) {
        this.date = date;
        this.steps = steps;
        this.weight = weight;

    }

    //getters
    public String getDate()
    {
        return date;
    }

    public int getSteps() {
        return this.steps;
    }

    public float getWeight()
    {
        return this.weight;
    }

    //setters
    public void setSteps(int stepsAmount)
    {
        this.steps = stepsAmount;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }
}
