package com.hackathon.handiplace.classes;

import java.util.ArrayList;

public class Restaurant {

    private String name;
    private String description;
    private String category;
    private String address;
    private String imageURL;

    private ArrayList<Criterion> criterions;

    public Restaurant(String name, String description, String category, String address, ArrayList<Criterion> criterions) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.address = address;
        this.criterions = criterions;
    }
}
