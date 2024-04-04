package com.example.ultimatecm;

import java.util.ArrayList;

public class CarMeet {
    private String date; // Format: dd/mm/yyyy
    private String time; // Format: mm:ss
    private ArrayList<String> tags; // #Americans, #OldCars
    private boolean privacy; // True = Public, False = Private
    private Location location;

    public CarMeet(String date, String time, ArrayList<String> tags, boolean privacy, Location location) {
        this.date = date;
        this.time = time;
        this.tags = tags;
        this.privacy = privacy;
        this.location = location;
    }

    public CarMeet() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
