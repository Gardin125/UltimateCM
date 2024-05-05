package com.example.ultimatecm;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataManager {
    private static ArrayList<Person> people;
    private static ArrayList<CarMeet> carMeets;

    private static final String dbMainList = "people";
    private static final String dbCarMeetList = "carMeets";

    public static ArrayList<Person> getPeople() {
        if (people == null)
            people = new ArrayList<Person>();
        return people;
    }

    public static ArrayList<CarMeet> getCarMeets() {
        if (carMeets == null)
            carMeets = new ArrayList<CarMeet>();
        return carMeets;
    }

    public static void pullPeople() {
        DBManager.getDb().getReference(dbMainList).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {};
                people = snapshot.getValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    public static void pullCarMeets() {
        DBManager.getDb().getReference(dbCarMeetList).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<CarMeet>> t = new GenericTypeIndicator<ArrayList<CarMeet>>() {};
                carMeets = snapshot.getValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    public static void addNewPerson(Person person) {
        getPeople().add(person);
        // Save to db
        DBManager.getDb().getReference(dbMainList).setValue(people);
    }

    public static void addNewCarMeet(CarMeet carMeet) {
        getCarMeets().add(carMeet);
        // Save to db
        DBManager.getDb().getReference(dbCarMeetList).setValue(carMeets);
    }

    public static DatabaseReference getMainRoot() {
        return DBManager.getDb().getReference();
    }


}
