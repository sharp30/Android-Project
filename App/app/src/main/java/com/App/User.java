package com.App;

import androidx.core.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String username;
    public Double height;

    public Integer score;

    //public Long weightTarget;
    //public Long stepsTarget;
    //protected Day[] history;


    User(String username, float height) {
        this.username = username;
        this.height = (double)height;
        score = 0;

        //weightTarget = (long)80;
        //stepsTarget = (long)5000;
        //history = Collections.singletonMap("1/1/2000" ,new Pair<Long,Long>((long)10,(long)5));
    }

    public User(String username, Map<String, Object> map)
    {
        this.username = username;
        this.height = ((Long)map.get("height")).doubleValue();
        this.score = ((Long)map.get("score")).intValue();
    }

    Map<String,Object> toMap()
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("height",(Double)height);
        map.put("score",score);
        return map;
    }


}