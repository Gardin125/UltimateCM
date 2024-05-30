package com.example.ultimatecm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        setContentView(R.layout.activity_join_meeting); // Set the layout for this activity

        // Initialize UI elements
        ivExit = findViewById(R.id.ivExit);
        lv = findViewById(R.id.lvCarMeeting);
        geocoder = new Geocoder(this);

        currentUser = DataManager.getCurrentLoggedInPersonByUsername(getUsername());

        // Set click listener for the exit button
        ivExit.setOnClickListener(v -> finish());

        // Initialize the carMeetArrayList
        carMeetArrayList = new ArrayList<>();

        // Add car meets created by other users to the list
        for (Person person : DataManager.getPeople()) {
            for (CarMeet carMeet : person.getMyCarMeets()) {
                // Add the car meet to the list if the creator is not the current user
                if (!carMeet.getCreator().equals(currentUser.getUsername())) {
                    carMeetArrayList.add(carMeet);
                }
            }
        }

        // Remove car meets that are in currentUser's myCarMeets and othersCarMeets
//        carMeetArrayList.removeIf(carMeet ->
//                carMeetEqualsAny(currentUser.getMyCarMeets(), carMeet) ||
//                    carMeetEqualsAny(currentUser.getOthersCarMeets(), carMeet));


        carMeetArrayList.removeIf(carMeet -> {
            boolean a = carMeetEqualsAny(currentUser.getMyCarMeets(), carMeet);
            boolean b = carMeetEqualsAny(currentUser.getOthersCarMeets(), carMeet);
            return a || b;
        });




        // Set up the adapter for the ListView
        cmAdapter = new CarMeetAdapter(this, 0, 0, carMeetArrayList);
        lv.setAdapter(cmAdapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected car meet
            CarMeet selectedCarMeet = carMeetArrayList.get(position);

            // Add the details to message
            String message = modifyMessage(selectedCarMeet);

            // Inflate custom dialog layout
            AlertDialog.Builder builder = new AlertDialog.Builder(JoinCarMeetActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_join_car_meet, null);
            builder.setView(dialogView);

            // Get references to dialog elements
            TextView etMessage = dialogView.findViewById(R.id.etMassage);
            Button btnYes = dialogView.findViewById(R.id.btnYes);
            Button btnNo = dialogView.findViewById(R.id.btnNo);

            // Set the message text
            etMessage.setText(etMessage.getText().toString() + "\n" + message);

            // Create and show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();

            // Handle Yes button click
            btnYes.setOnClickListener(v -> {
                if (currentUser != null) {
                    joinCarMeet(selectedCarMeet);
                    carMeetArrayList.remove(selectedCarMeet);
                    cmAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                } else {
                    Toast.makeText(JoinCarMeetActivity.this, "Current user not found", Toast.LENGTH_SHORT).show();
                }
            });

            // Handle No button click
            btnNo.setOnClickListener(v -> dialog.dismiss());
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

    // Method to add to the dialog's message the car meet details
    public String modifyMessage(CarMeet selectedCarMeet) {
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
        return message;
    }

    private void joinCarMeet(CarMeet carMeet) {
        // Get the current user
        Person currentUser = DataManager.getCurrentLoggedInPersonByUsername(getUsername());

        // Ensure the list of carMeets is initialized
        if (currentUser.getOthersCarMeets() == null) {
            currentUser.setOthersCarMeets(new ArrayList<CarMeet>());
        }
        int updatedParticipants = carMeet.getParticipants();
        updatedParticipants += 1;
        carMeet.setParticipants(updatedParticipants);

        // Add the new carMeet to the user's list
        currentUser.getOthersCarMeets().add(carMeet);

        DataManager.updatePeopleList();

    }
    private boolean carMeetEqualsAny(List<CarMeet> carMeets, CarMeet carMeet) {
        for (CarMeet cm : carMeets)
            if (cm.equals(carMeet)) return true;
        return false;
    }
}


