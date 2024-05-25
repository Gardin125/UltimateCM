package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoinCarMeetActivity extends AppCompatActivity {
    // Declare UI elements and data lists
    ImageView ivExit;
    ArrayList<CarMeet> carMeetArrayList;
    ListView lv;
    CarMeetAdapter cmAdapter;
    Person currentUser;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.pullPeople();
        setContentView(R.layout.activity_join_meeting); // Set the layout for this activity

        // Initialize UI elements
        ivExit = findViewById(R.id.ivExit);
        lv = findViewById(R.id.lvCarMeeting);
        geocoder = new Geocoder(this);

        currentUser = DataManager.getCurrentLoggedInPersonByUsername(getUsername());

        // Initialize the othersCarMeetArrayList if it's null
        if (currentUser != null && currentUser.getOthersCarMeets() == null) {
            currentUser.setOthersCarMeets(new ArrayList<>());
        }

        // Set click listener for the exit button
        ivExit.setOnClickListener(v -> finish());

        // Initialize the carMeetArrayList
        carMeetArrayList = new ArrayList<>();

        // Add car meets from other users to the list, excluding ones the current user is already in
        for (Person person : DataManager.getPeople()) {
            if (!person.getUsername().equals(currentUser.getUsername())) {
                for (CarMeet carMeet : person.getMyCarMeets()) {
                    if (!currentUser.getOthersCarMeets().contains(carMeet)) {
                        carMeetArrayList.add(carMeet);
                    }
                }
            }
        }

        // Set up the adapter for the ListView
        cmAdapter = new CarMeetAdapter(this, 0, 0, carMeetArrayList);
        lv.setAdapter(cmAdapter);

        // Set item click listener for the ListView
        lv.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected car meet
            CarMeet selectedCarMeet = carMeetArrayList.get(position);

            // Create the message with car meet details
            String message = "Do you want to join this Car Meet?\n\n" +
                    "The details for this Car Meet are:\n" +
                    "Date: " + selectedCarMeet.getDate() + "\n" +
                    "Time: " + selectedCarMeet.getTime() + "\n";

            // Perform reverse geocoding to get the city name from latitude and longitude
            try {
                List<Address> addresses = geocoder.getFromLocation(selectedCarMeet.getLocation().getLatitude(), selectedCarMeet.getLocation().getLongitude(), 1);
                if (!addresses.isEmpty()) {
                    String city = addresses.get(0).getLocality();
                    message += "Location: " + city;
                } else {
                    message += "Location: Not available";
                }
            } catch (IOException e) {
                e.printStackTrace();
                message += "Location: Not available";
            }

            // Show a dialog to confirm joining the selected car meet
            AlertDialog.Builder builder = new AlertDialog.Builder(JoinCarMeetActivity.this);
            builder.setMessage(message)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (currentUser != null) {
                            if (currentUser.getOthersCarMeets() == null)
                                currentUser.setOthersCarMeets(new ArrayList<CarMeet>());

                            // Add the selected car meet to the current user's list
                            currentUser.getOthersCarMeets().add(selectedCarMeet);

                            // Update current user in database
                            DataManager.updatePeopleList();

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


