package com.App;

import java.util.Date;

public class Day
{
    protected Date date;
    protected int steps;
    protected double weight;

    public Day(Date date, int steps, int weight ) {
        this.date = date;
        this.steps = steps;
        this.weight = weight;

    }

    //getters
    public Date getDate()
    {
        return date;
    }

    public int getStepS() {
        return this.steps;
    }

    public double getWeight()
    {
        return this.weight;
    }

    //setters
    public void setSteps(int stepsAmount)
    {
        this.steps = stepsAmount;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }
}
