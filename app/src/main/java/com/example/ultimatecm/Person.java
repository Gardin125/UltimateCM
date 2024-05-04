package com.example.ultimatecm;

import java.util.ArrayList;
import java.util.HashMap;

public class Person {
    protected String firstName;
    protected String lastName;
    private String email;
    protected String username;
    private String password;
    private HashMap profilePicture; // New field for storing profile picture
    private ArrayList<CarMeet> myCarMeets;
    private ArrayList<CarMeet> othersCarMeets;

    public Person(String firstName, String lastName, String email, String username, String password, HashMap profilePicture, ArrayList<CarMeet> myCarMeets, ArrayList<CarMeet> othersCarMeets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePicture = profilePicture;
        this.myCarMeets = myCarMeets;
        this.othersCarMeets = othersCarMeets;
    }

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(HashMap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<CarMeet> getMyCarMeets() {
        if (myCarMeets == null) {
            myCarMeets = new ArrayList<>();
        }
        return myCarMeets;
    }

    public void setMyCarMeets(ArrayList<CarMeet> myCarMeets) {
        this.myCarMeets = myCarMeets;
    }

    public ArrayList<CarMeet> getOthersCarMeets() {
        if (othersCarMeets == null) {
            othersCarMeets = new ArrayList<>();
        }
        return othersCarMeets;
    }

    public void setOthersCarMeets(ArrayList<CarMeet> othersCarMeets) {
        this.othersCarMeets = othersCarMeets;
    }
}

