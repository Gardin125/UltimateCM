package com.example.ultimatecm;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Person {
    protected String firstName;
    protected String lastName;
    private String email;
    protected String username;
    private String password;
    private ArrayList<CarMeet> myCarMeets;

    public Person(String firstName, String lastName, String email, String username, String password, ArrayList<CarMeet> myCarMeets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.myCarMeets = myCarMeets;
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

    public ArrayList<CarMeet> getMyCarMeets() {
        return myCarMeets;
    }

    public void setMyCarMeets(ArrayList<CarMeet> myCarMeets) {
        this.myCarMeets = myCarMeets;
    }

}
