package com.hackathon.handiplace.dummies;

import com.hackathon.handiplace.classes.Restaurant;

import java.util.ArrayList;

public class DummyData {

    static {

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Au délice", "Venez vous régaler chez nous!", "Frites",
                "Impasse de la Fidélité 4A 1000 Ville de Bruxelles", 2, null));
        restaurants.add(new Restaurant("Pépé café", "Pour bien manger !", "Italien",
                "Rue de Tabora 11 1000 Ville de Bruxelles", 3, null));
        restaurants.add(new Restaurant("Xu ji", "Venez vous régaler chez nous!", "Chinois",
                "Rue des Poissonniers 5 1000 Ville de Bruxelles", 1, null));

    }

}
