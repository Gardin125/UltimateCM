package com.example.ultimatecm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CarMeet implements Serializable {
    private String date; // Format: dd/mm/yyyy
    private String time; // Format: mm:ss
    private ArrayList<String> tags; // #Americans, #OldCars
    private Location location;
    private String creator;
    private int participants;

    public CarMeet(String date, String time, ArrayList<String> tags, Location location, String creator) {
        this.date = date;
        this.time = time;
        this.tags = tags;
        this.location = location;
        this.creator = creator;
        this.participants = 0;
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

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public boolean checkEqualsCarMeet(CarMeet other) {
        return this.date.equals(other.date) &&
                this.time.equals(other.time) &&
                this.tags.equals(other.tags) &&
                locationsEqual(this.location, other.location) &&
                this.creator.equals(other.creator);
    }

    private boolean locationsEqual(Location a, Location b) {
        return a.getLatitude() == b.getLatitude() && a.getLongitude() == b.getLongitude();
    }
}
