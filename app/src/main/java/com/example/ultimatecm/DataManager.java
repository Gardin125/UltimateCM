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

    static final String dbMainList = "people";

    public static ArrayList<Person> getPeople() {
        if (people == null)
            people = new ArrayList<Person>();
        return people;
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


    public static void addNewPerson(Person person) {
        getPeople().add(person);
        // Save to db
        DBManager.getDb().getReference(dbMainList).setValue(people);
    }

    public static DatabaseReference getMainRoot() {
        return DBManager.getDb().getReference();
    }
    public static void updatePeopleList() {
        getMainRoot().child(dbMainList).setValue(people);
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



