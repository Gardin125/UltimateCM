package com.example.ultimatecm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DBManager {
    private static FirebaseAuth auth;
    private static FirebaseDatabase db;

    public static FirebaseAuth getAuth() {
        if (auth == null)
            auth = FirebaseAuth.getInstance();
        return auth;
    }
     public static FirebaseDatabase getDb() {
        if (db == null)
            db = FirebaseDatabase.getInstance("https://ultimatecm-41c1f-default-rtdb.europe-west1.firebasedatabase.app/");
        return db;
    }

    public static String getCurrentUserEmail() {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user != null) {
            return user.getEmail();
        } else {
            return null; // No user logged in
        }
    }

    public static DatabaseReference getMainRoot()
    {
        return getDb().getReference("people");
    }
}
