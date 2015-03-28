package com.hackathon.handiplace.classes;

import java.util.ArrayList;

public class User {

    private String macAddress;
    private int id;
    private boolean[] disabilities;
    private boolean connected;

    public User(){
        disabilities = new boolean[Utils.idHandicap.size()];
        connected = false;
    }
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean[] getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(boolean[] disabilities) {
        this.disabilities = disabilities;
    }

    public void setDisability(boolean ok, int index){
        disabilities[index] = ok;
    }

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
