package com.example.ultimatecm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinClubActivity.this);
                builder.setMessage("Do you want to join this club?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Add your code to handle "Yes" option here
                                // For example, you can start another activity or perform any other action
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
}