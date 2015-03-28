package com.hackathon.handiplace.classes;

import java.util.ArrayList;

public class User {

    private String macAddress;
    private int id;

    private ArrayList<Restaurant> favoritesRestaurant;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Restaurant> getFavoritesRestaurant() {
        return favoritesRestaurant;
    }

    public void setFavoritesRestaurant(ArrayList<Restaurant> favoritesRestaurant) {
        this.favoritesRestaurant = favoritesRestaurant;
    }


}
