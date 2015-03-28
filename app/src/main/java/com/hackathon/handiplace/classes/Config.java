package com.hackathon.handiplace.classes;

import java.util.HashMap;

public class Config {

    public static String BASE_URL = "http://hackathon.bewweb.be/";

    public static HashMap<Integer, Integer> idHandicap = new HashMap<Integer, Integer>();

    static{
        idHandicap.put(1, 0);
        idHandicap.put(2, 2);
        idHandicap.put(3, 3);
        idHandicap.put(4, 4);
        idHandicap.put(5, 5);
        idHandicap.put(6, 1);
    }



}
