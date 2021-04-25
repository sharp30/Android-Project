package com.App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultXAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HistoryActivity extends AppCompatActivity {
    SharedPreferences sp;
    Dal dal;
    BottomNavigationView bottomNavigationView;
    boolean type = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dal = new Dal(getApplicationContext());
        sp = getSharedPreferences("values",0);

        bottomNavigationView = findViewById(R.id.btn_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent next = null;
                switch (item.getItemId()) {
                    case R.id.steps_nav:
                        type = true;
                        break;

                    case R.id.weight_nav:
                        type = false;
                        break;
                    default:
                        return false;
                      }
                      buildChart(type);
                      return true;
            }});


            buildChart(type);

    }

    private void buildChart(boolean type)
    {
        if(type)
            getChart("steps");
        else
            getChart("weight");
    }

    public void getChart(String type)
    {


        int target = sp.getInt(type+"_target",5000);

        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);

        final ArrayList<String> labels = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Random random = new Random();
        ArrayList<BarEntry> entries = new ArrayList<>();
        int[] colors = new int[5];

        //today value
        Date day = cal.getTime();
        String date = df.format(day);
        float val = sp.getInt(type,0);

        for(int i=0; i<5;i++)
        {

            // Get current date of calendar which point to the yesterday now
            colors[i]= Color.RED;
            if(val>target)
                colors[i] = Color.GREEN;

            entries.add(new BarEntry(val, 5-i));
            labels.add(date);
            cal.add(Calendar.DATE, -1);
            day = cal.getTime();

            //read week history - from db/
            date =  df.format(day);
            if(type == "steps")
                val = (int)dal.getDay(date).getSteps();
            else
                val = dal.getDay(date).getWeight();


        }
        BarDataSet bardataset = new BarDataSet(entries, "Cells");
        BarData data = new BarData(labels, bardataset);

        barChart.setData(data);

        bardataset.setColors(colors);
        barChart.animateY(5000);


        //----design----
        //delete
        barChart.getLegend().setEnabled(false);

        barChart.setDrawBorders(false);
        barChart.setDrawGridBackground(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);


        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getXAxis().setDrawLabels(false); // hide bottom label
        barChart.getAxisLeft().setDrawLabels(false); // hide left label
        barChart.getAxisRight().setDrawLabels(false); // hide right label

        //font size
        bardataset.setValueTextSize(25);
        barChart.getXAxis().setValueFormatter(new XAxisValueFormatter()
        {
            @Override
            public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
                return labels.get(index);
            }
        });
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        barChart.setNoDataTextDescription("");
    }
}