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
    private static int nextCarMeetId = 0; // Track the next available ID

    public static int getNextCarMeetId() {
        return nextCarMeetId += 1;
    }

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

    public static void updateCarMeetEverywhere(CarMeet updatedCarMeet) {
        for (Person person : getPeople()) {
            boolean updated = false;
            for (CarMeet carMeet : person.getOthersCarMeets()) {
                if (carMeet.getId() == updatedCarMeet.getId()) {
                    carMeet.setDate(updatedCarMeet.getDate());
                    carMeet.setTime(updatedCarMeet.getTime());
                    carMeet.setLocation(updatedCarMeet.getLocation());
                    updated = true;
                }
            }
            if (updated) {
                updatePeopleList();

                // If any car meet was updated for this person, update the person's data in the database
                // getMainRoot().child(dbMainList).child(person.getId()).setValue(person);
            }
        }
    }
    public static void updateCarMeetInDB(ArrayList<CarMeet> myCM, CarMeet cm) {
        for (CarMeet carMeet: myCM) {
            if (carMeet.getId() == cm.getId()) {
                cm.setDate(carMeet.getDate());
                cm.setTime(carMeet.getTime());
                cm.setLocation(carMeet.getLocation());
            }
        }

        updatePeopleList();
    }
    public static ArrayList<CarMeet> getAllJoinedCarMeetInApp() {
        ArrayList<CarMeet> allJoinedCarMeetsInDB = new ArrayList<>();
        for (Person p: DataManager.getPeople()) {
            for (CarMeet myCm: p.getMyCarMeets()) {
                allJoinedCarMeetsInDB.add(myCm);
            }
        }
        return allJoinedCarMeetsInDB;
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



