package com.hackathon.handiplace;

import android.app.Application;

import com.hackathon.handiplace.classes.User;

public class HandiPlaceApplication extends Application {

    public static User user = new User();
    public static boolean isLocated = false;

}
