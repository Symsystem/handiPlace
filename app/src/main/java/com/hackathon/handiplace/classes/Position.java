package com.hackathon.handiplace.classes;

/**
 * Created by sym on 28/03/15.
 */
public class Position {

    private double latitude;
    private double longitude;

    public Position (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
