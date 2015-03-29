package com.hackathon.handiplace.classes;


import java.io.Serializable;

public class Criterion implements Serializable{

    private String name;
    private String description;
    private boolean gooOrBad;
    private int isLiked, like, dislike, priority;


    public Criterion(String name, String description, boolean gooOrBad, int isLiked, int like, int dislike, int priority) {
        this.name = name;
        this.description = description;
        this.gooOrBad = gooOrBad;
        this.isLiked = isLiked;
        this.like = like;
        this.dislike = dislike;
        this.priority = priority;
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

    public boolean isGooOrBad() {
        return gooOrBad;
    }

    public void setGooOrBad(boolean gooOrBad) {
        this.gooOrBad = gooOrBad;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
