package com.App;

import androidx.core.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String username;
    public Double height;
    //public Long weightTarget;
    //public Long stepsTarget;
    public Map<String,Pair<Long,Long>> history;
    //protected Day[] history;


    User(String username, float height) {
        this.username = username;
        this.height = (double)height;
        //weightTarget = (long)80;
        //stepsTarget = (long)5000;
        //history = Collections.singletonMap("1/1/2000" ,new Pair<Long,Long>((long)10,(long)5));
    }
}
