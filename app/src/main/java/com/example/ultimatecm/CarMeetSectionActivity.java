package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CarMeetSectionActivity extends AppCompatActivity {
    Button btnCreateMeeting, btnJoinMeeting;
    ImageView ivExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_meet_section);
        btnCreateMeeting = findViewById(R.id.btnCreateCarMeet);
        btnJoinMeeting = findViewById(R.id.btnJoinCarMeet);
        ivExit = findViewById(R.id.ivExit);

        btnCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarMeetSectionActivity.this, CreateCarMeetActivity.class);
                startActivity(intent);
            }
        });

        btnJoinMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarMeetSectionActivity.this, JoinCarMeetActivity.class);
                startActivity(intent);
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}