package com.example.dynamicnavigationdrawer;

import android.graphics.drawable.Drawable;

public class MenuItem {

    private int id;
    private String title;
    private Drawable icon;
    private boolean isParent;
    private boolean isExpanded;
    private boolean isClickable; // To differentiate parent items that are just headers vs. clickable parents

    public MenuItem(int id, String title, Drawable icon, boolean isParent, boolean isClickable) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.isParent = isParent;
        this.isExpanded = false; // Initially collapsed
        this.isClickable = isClickable;
    }

    //--- Getters and Setters ---
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public boolean isParent() {
        return isParent;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }
}