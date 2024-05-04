package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class OthersCarMeetsActivity extends AppCompatActivity {
    ImageView ivExit;
    ListView lvOthersCM;
    Person currentUser;
    CarMeetAdapter carMeetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_car_meets);
        ivExit = findViewById(R.id.ivSecurity);
        lvOthersCM = findViewById(R.id.lvOthersCM);
        currentUser = new Person();

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Exit the activity when the exit button is clicked
            }
        });

        // Get the username of the current user
        String username = getUsername();

        // Find the current user in the data manager
        for (int i = 0; i < DataManager.getPeople().size(); i++) {
            if (DataManager.getPeople().get(i).username.equals(username))
                currentUser = DataManager.getPeople().get(i);
        }

        // Create a list to store car meets that the user did not create
        ArrayList<CarMeet> othersCMList = new ArrayList<>();
        for (int i = 0; i < DataManager.getCarMeets().size(); i++) {
            if (!DataManager.getCarMeets().get(i).getCreator().equals(username)) {
                othersCMList.add(DataManager.getCarMeets().get(i));
            }
        }

        // Initialize the adapter with the list of car meets
        carMeetAdapter = new CarMeetAdapter(this, 0, 0, othersCMList);
        lvOthersCM.setAdapter(carMeetAdapter);

        // Set an item click listener for the list view
        lvOthersCM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected car meet
                CarMeet selectedCarMeet = othersCMList.get(position);

                // Create an intent to start CarMeetDetailsActivity
                Intent intent = new Intent(getApplicationContext(), CarMeetDetailsActivity.class);
                // Pass the selected car meet details to the CarMeetDetailsActivity
                intent.putExtra("SELECTEDCARMEET", selectedCarMeet);
                // Start the activity
                startActivity(intent);
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
