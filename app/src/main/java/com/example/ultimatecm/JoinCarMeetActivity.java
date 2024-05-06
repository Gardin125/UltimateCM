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
    ImageView ivExit;

    ArrayList<CarMeet> carMeetArrayList, othersCarMeetArrayList;
    ListView lv;
    CarMeetAdapter cmAdapter;
    Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);
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

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        carMeetArrayList = new ArrayList<>();
        for (CarMeet carMeet : DataManager.getCarMeets()) {
            // Exclude the car meets created by the current user
            if (!carMeet.getCreator().equals(currentUserUsername)) {
                // Exclude the car meets already joined by the current user
                boolean alreadyJoined = false;
                if (currentUser != null && currentUser.getOthersCarMeets() != null) {
                    for (CarMeet joinedCarMeet : currentUser.getOthersCarMeets()) {
                        if (carMeet.equals(joinedCarMeet)) {
                            alreadyJoined = true;
                            break;
                        }
                    }
                }
                if (!alreadyJoined) {
                    carMeetArrayList.add(carMeet);
                }
            }
        }

        cmAdapter = new CarMeetAdapter(this, 0, 0, carMeetArrayList);
        lv.setAdapter(cmAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinCarMeetActivity.this);
                builder.setMessage("Do you want to join this Car Meet?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Check if currentUser is found
                                if (currentUser != null) {
                                    // Get the selected car meet from the carMeetArrayList
                                    CarMeet selectedCarMeet = carMeetArrayList.get(position);

                                    // Add the selected car meet to the currentUser's othersCarMeets list
                                    if (currentUser.getOthersCarMeets() != null) {
                                        currentUser.getOthersCarMeets().add(selectedCarMeet);
                                    }

                                    // Remove the selected car meet from the carMeetArrayList
                                    carMeetArrayList.remove(selectedCarMeet);

                                    // Notify the adapter that the data set has changed
                                    cmAdapter.notifyDataSetChanged();

                                    // Update the user data in the database asynchronously
                                    updateUserDataInDatabase(currentUser);
                                } else {
                                    // Handle case where currentUser is not found
                                    Toast.makeText(JoinCarMeetActivity.this, "Current user not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Add your code to handle "No" option here
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    // Method to update user data in the database asynchronously
    private void updateUserDataInDatabase(Person user) {
        DataManager.updatePerson(user, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Database update successful
                Toast.makeText(JoinCarMeetActivity.this, "User data updated successfully", Toast.LENGTH_SHORT).show();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Database update failed
                Toast.makeText(JoinCarMeetActivity.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    public String getUsername() {
        String username = "";
        for (int i = 0; i < DataManager.getPeople().size(); i++) {
            if (DBManager.getCurrentUserEmail().equals(DataManager.getPeople().get(i).getEmail()))
                username = DataManager.getPeople().get(i).getUsername();
        }
        return username;
    }
}
