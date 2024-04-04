package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class JoinClubActivity extends AppCompatActivity {
    ImageView ivExit;

    ArrayList<Club> clubArrayList;
    ListView lv;
    ClubAdapter cAdapter;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_club);
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clubArrayList = new ArrayList<Club>();
        for (int i = 0; i < DataManager.getClubs().size(); i++)
        {
            Club club = new Club(DataManager.getClubs().get(i).getClubName(), DataManager.getClubs().get(i).getClubDescription(), DataManager.getClubs().get(i).getPrivacy());
            clubArrayList.add(club);
        }

        cAdapter = new ClubAdapter(this,0,0, clubArrayList);
        lv = findViewById(R.id.lvClub);
        lv.setAdapter(cAdapter);
    }
}