package com.hackathon.handiplace.classes;

import java.util.ArrayList;

public class Restaurant {

    private String name;
    private String description;
    private String category;
    private String address;
    private String imageURL;
    private int rating;
    private double kilometers;

    public Restaurant(String name, String description, String category, String address, int rating, ArrayList<Criterion> criterions) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.address = address;
        this.criterions = criterions;
        this.rating = rating;
    }

    public Restaurant(String name, String category, int rating, double kilometers) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.kilometers = kilometers;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private ArrayList<Criterion> criterions;

    public double getKilometers() {
        return kilometers;
    }
}
