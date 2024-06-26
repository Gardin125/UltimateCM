package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

        ivExit.setOnClickListener(v -> finish());

       currentUser = DataManager.getCurrentLoggedInPersonByUsername(getUsername());

        if (currentUser == null) {
            Log.e("OthersCarMeetsActivity", "Current user not found");
            return;
        }

        // Initialize the othersCarMeetArrayList if it's null
        if (currentUser.getOthersCarMeets() == null) {
            currentUser.setOthersCarMeets(new ArrayList<>());
        }

        // Initialize the adapter with the list of car meets
        carMeetAdapter = new CarMeetAdapter(this, 0, 0, currentUser.getOthersCarMeets());
        lvOthersCM.setAdapter(carMeetAdapter);

        // Set an item click listener for the list view
        lvOthersCM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected car meet
                CarMeet selectedCarMeet = currentUser.getOthersCarMeets().get(position);

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
