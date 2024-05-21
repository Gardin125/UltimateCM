package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ultimatecm.databinding.EditMeetingActivityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class JoinCarMeetActivity extends AppCompatActivity {
    // Declare UI elements and data lists
    ImageView ivExit;
    ArrayList<CarMeet> carMeetArrayList;
    ListView lv;
    CarMeetAdapter cmAdapter;
    Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.pullPeople();
        setContentView(R.layout.activity_join_meeting); // Set the layout for this activity

        // Initialize UI elements
        ivExit = findViewById(R.id.ivExit);
        lv = findViewById(R.id.lvCarMeeting);

        // Get the current user's username
        String currentUserUsername = getUsername();

        // Find the current user in the data manager
        for (Person person : DataManager.getPeople()) {
            if (person.getUsername().equals(currentUserUsername)) {
                currentUser = person;
                break;
            }
        }

        // Initialize the othersCarMeetArrayList if it's null
        if (currentUser != null && currentUser.getOthersCarMeets() == null) {
            currentUser.setOthersCarMeets(new ArrayList<>());
        }

        // Set click listener for the exit button
        ivExit.setOnClickListener(v -> finish());

        // Initialize the carMeetArrayList
        carMeetArrayList = new ArrayList<>();

        // Add car meets from other users to the list
        for (Person person : DataManager.getPeople()) {
            if (!person.getUsername().equals(currentUserUsername)) {
                for (CarMeet carMeet : person.getMyCarMeets()) {
                    carMeetArrayList.add(carMeet);
                }
            }
        }

        // Set up the adapter for the ListView
        cmAdapter = new CarMeetAdapter(this, 0, 0, carMeetArrayList);
        lv.setAdapter(cmAdapter);

        // Set item click listener for the ListView
        lv.setOnItemClickListener((parent, view, position, id) -> {
            // Show a dialog to confirm joining the selected car meet
            AlertDialog.Builder builder = new AlertDialog.Builder(JoinCarMeetActivity.this);
            builder.setMessage("Do you want to join this Car Meet?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (currentUser != null) {
                            // Get the selected car meet
                            CarMeet selectedCarMeet = carMeetArrayList.get(position);

                            // Add the selected car meet to the current user's list
                            currentUser.getOthersCarMeets().add(selectedCarMeet);

                            // Remove the selected car meet from the displayed list
                            carMeetArrayList.remove(selectedCarMeet);

                            // Notify the adapter of data changes
                            cmAdapter.notifyDataSetChanged();

                        } else {
                            // Show an error if the current user is not found
                            Toast.makeText(JoinCarMeetActivity.this, "Current user not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // Handle "No" option (no action needed)
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    // Method to update user data in the database


    // Method to get the current user's username
    public String getUsername() {
        String username = "";
        for (int i = 0; i < DataManager.getPeople().size(); i++) {
            if (DBManager.getCurrentUserEmail().equals(DataManager.getPeople().get(i).getEmail()))
                username = DataManager.getPeople().get(i).getUsername();
        }
        return username;
    }
}

