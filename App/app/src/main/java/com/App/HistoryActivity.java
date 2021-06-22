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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


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
    BottomNavigationView bottomNavigationView;
    boolean type = true;
    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> labels;

    ArrayList<BarEntry> entries;
    ArrayList<Integer> colors;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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
        labels = new ArrayList<String>();
        entries = new ArrayList<>();
        colors = new ArrayList<>();

        if(type)
            getChart("steps");
        else
            getChart("weight");
    }

    public void getChart(final String type)
    {
        final int target = sp.getInt(type+"_target",5000);
        final BarChart barChart = (BarChart) findViewById(R.id.bar_chart);
        final Calendar cal = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        final DateFormat fbForamt = new SimpleDateFormat("yyyyMMdd");
        barChart.clear();


        Random random = new Random();
        //today value
        Date day = cal.getTime();
        String date = df.format(day);
        float val;

        if(type == "steps")
            val = sp.getInt(type,0);
        else
            val = sp.getFloat(type,0);

        addEntry(val,type,target,0,date);

        final String[] dates = new String[4];
        for(int i =0; i < 4; i++)
        {
            cal.add(Calendar.DATE, -1);
            day = cal.getTime();

            dates[i] = df.format(day);

        }

        Query q = ref.child("users").child(sp.getString("logged","")+"/history");
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(int k = 0;k<dates.length;k++)
                {
                    float val = 0;
                    try
                    {
                        String fbDate = fbForamt.format(df.parse(dates[k]));
                        val = ((Long)snapshot.child(fbDate).child(type).getValue()).floatValue();
                    }
                    catch (Exception e)
                    {
                        val = 0;
                    }

                    addEntry(val,type,target,k+1,dates[k]);


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
                barChart.getAxisLeft().setDrawAxisLine(false);

                barChart.getXAxis().setDrawGridLines(false);
                barChart.getXAxis().setDrawAxisLine(false);

                barChart.getAxisRight().setDrawGridLines(false);
                barChart.getAxisRight().setDrawAxisLine(false);


                barChart.getAxisLeft().setDrawGridLines(false);
                barChart.getXAxis().setDrawGridLines(false);

                barChart.getXAxis().setDrawLabels(true); // hide bottom label
                barChart.getAxisLeft().setDrawLabels(false); // hide left label
                barChart.getAxisRight().setDrawLabels(false); // hide right label

                //font size
                bardataset.setValueTextSize(25);
                barChart.getXAxis().setValueFormatter(new XAxisValueFormatter()
                {
                    @Override
                    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
                        return labels.get(4-index);
                    }
                });
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.setNoDataTextDescription("");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });

    }
    private void addEntry(float val, String type, int target,int i,String date)
    {
        if (type == "steps" && val > target || type == "weight" && val <= target)
            colors.add(Color.GREEN);
        else
            colors.add(Color.RED);

        entries.add(new BarEntry(val, 4-i));
        labels.add(date);

    }
}