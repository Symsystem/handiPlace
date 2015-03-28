package com.hackathon.handiplace.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable{

    private int id;

    private String name;
    private String description;
    private String category;
    private String address;
    private String imageURL;
    private int rating;
    private double kilometers;
    private ArrayList<Disability> disabilities;

    public Restaurant(int id, String name, String description, String category, String address, int rating, ArrayList<Disability> disabilities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.address = address;
        this.disabilities = disabilities;
        this.rating = rating;
    }

    public Restaurant(int id, String name, String category, int rating, double kilometers) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.kilometers = kilometers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public ArrayList<Disability> getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(ArrayList<Disability> disabilities) {
        this.disabilities = disabilities;
    }


}
