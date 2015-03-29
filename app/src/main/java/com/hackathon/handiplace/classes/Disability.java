package com.hackathon.handiplace.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Disability implements Serializable{

    private int id;
    private String name;
    private String description;

    private ArrayList<Criterion> criterions;

    public Disability(int id, String name, String description, ArrayList<Criterion> criterions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criterions = criterions;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Criterion> getCriterions(){
        return criterions;
    }

    public boolean equals(Disability r){
        if (r.id == this.id){
            return true;
        }
        return false;
    }
}
