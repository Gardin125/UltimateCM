package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ClubSectionActivity extends AppCompatActivity {
    Button btnCreateClub, btnJoinClub;
    ImageView ivExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_section);
        btnCreateClub = findViewById(R.id.btnCreateClub);
        btnJoinClub = findViewById(R.id.btnJoinClub);
        ivExit = findViewById(R.id.ivExit);

        btnCreateClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubSectionActivity.this, CreateClubActivity.class);
                startActivity(intent);
            }
        });

        btnJoinClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubSectionActivity.this, JoinClubActivity.class);
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