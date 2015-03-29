package com.hackathon.handiplace.classes;


import java.io.Serializable;

public class Criterion implements Serializable{

    private String name;
    private String description;
    private boolean gooOrBad;
    private int isLiked, like, dislike;

    public Criterion(String name, String description, boolean goodOrBad, int isLiked, int like, int dislike) {
        this.name = name;
        this.description = description;
    }
}
