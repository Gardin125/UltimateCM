package com.example.ultimatecm;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataManager {
    private static ArrayList<Person> people;
    private static ArrayList<CarMeet> carMeets;

    static final String dbMainList = "people";
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
                GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {
                };
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
                GenericTypeIndicator<ArrayList<CarMeet>> t = new GenericTypeIndicator<ArrayList<CarMeet>>() {
                };
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

    public static void updateCarMeet(CarMeet carMeet) {
        DBManager.getDb().getReference(dbCarMeetList).setValue(carMeets);
    }

    public static void updatePerson(Person person, OnSuccessListener<Void> user_data_updated_successfully, OnFailureListener failed_to_update_user_data) {
        for (int i = 0; i < people.size(); i++) {
            // Find the person in the list
            if (people.get(i).getUsername().equals(person.getUsername())) {
                // Update the person's data
                people.set(i, person);
                break;
            }
        }
        // Save the updated list to the database
        DBManager.getDb().getReference(dbMainList).setValue(people);
    }

    public static Person getCurrentLoggedInPersonByEmail(String email) {
        if (people != null) {
            for (Person person : people) {
                if (person.getEmail().equals(email)) {
                    return person;
                }
            }
        }
        return null;
    }


    public static Person getCurrentLoggedInPersonByUsername(String username) {
        if (people != null) {
            for (Person person : people) {
                if (person.getUsername().equals(username)) {
                    return person;
                }
            }
        }
        return null;
    }
}



