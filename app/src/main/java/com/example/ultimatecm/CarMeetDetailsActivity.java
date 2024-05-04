package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CarMeetDetailsActivity extends AppCompatActivity {

    TextView tvDate, tvTime;
    Button btnViewLocation, btnDone;
    CarMeet selectedCarMeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_meet_details);

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        btnViewLocation = findViewById(R.id.btnViewLocation);
        btnDone = findViewById(R.id.btnDone);

        // Get the intent that started this activity
        Intent intent = getIntent();

        selectedCarMeet = new CarMeet();

        // Retrieve the selectedCarMeet object from the intent extras
        selectedCarMeet = (CarMeet) intent.getSerializableExtra("SELECTEDCARMEET");

        // Now you can use the selectedCarMeet object as needed
        if (selectedCarMeet != null) {
            // Set the TextViews with the details of the selected car meet
            tvDate.setText(selectedCarMeet.getDate());
            tvTime.setText(selectedCarMeet.getTime());

            // Set click listener for the button to view location
            btnViewLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start MapsActivity to view the location
                    Intent mapIntent = new Intent(CarMeetDetailsActivity.this, MapsActivity.class);
                    mapIntent.putExtra("latitude", selectedCarMeet.getLocation().getLatitude());
                    mapIntent.putExtra("longitude", selectedCarMeet.getLocation().getLongitude());
                    startActivity(mapIntent);
                }
            });

            // Set click listener for the done button
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Close the activity
                }
            });
        } else {
            Log.d("SelectedCarMeet", "Is null.");
        }
    }
}