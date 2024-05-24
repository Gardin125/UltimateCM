package com.example.ultimatecm;

import java.util.ArrayList;

public class Person {
    protected String firstName;
    protected String lastName;
    private String email;
    protected String username;
    private ArrayList<CarMeet> myCarMeets;
    private ArrayList<CarMeet> othersCarMeets;

    public Person(String firstName, String lastName, String email, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.myCarMeets = new ArrayList<CarMeet>();
        this.othersCarMeets = new ArrayList<CarMeet>();
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