package com.example.ultimatecm;

import java.io.Serializable;
import java.util.ArrayList;

public class CarMeet implements Serializable {
    private String date; // Format: dd/mm/yyyy
    private String time; // Format: mm:ss
    private ArrayList<String> tags; // #Americans, #OldCars
    private Location location;
    private String creator;

    public CarMeet(String date, String time, ArrayList<String> tags,  Location location, String creator) {
        this.date = date;
        this.time = time;
        this.tags = tags;
        this.location = location;
        this.creator = creator;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    // Method to compare two CarMeet objects
    public boolean isEqual(CarMeet other) {
        // Check if all attributes are the same
        boolean dateEqual = this.date.equals(other.date);
        boolean timeEqual = this.time.equals(other.time);
        boolean tagsEqual = this.tags.equals(other.tags);
        boolean locationEqual = this.location.equals(other.location);
        boolean creatorEqual = this.creator.equals(other.creator);

        // Return true if all attributes are equal, false otherwise
        return dateEqual && timeEqual && tagsEqual && locationEqual && creatorEqual;
    }
}
