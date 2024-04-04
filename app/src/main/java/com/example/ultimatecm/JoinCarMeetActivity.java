package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class JoinCarMeetActivity extends AppCompatActivity {
    ImageView ivExit;

    ArrayList<CarMeet> carMeetArrayList;
    ListView lv;
    CarMeetAdapter cmAdapter;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);
        ivExit = findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        carMeetArrayList = new ArrayList<CarMeet>();
        for (int i = 0; i < DataManager.getCarMeets().size(); i++)
        {
            CarMeet carMeet = new CarMeet(DataManager.getCarMeets().get(i).getDate(), DataManager.getCarMeets().get(i).getTime(), DataManager.getCarMeets().get(i).getTags(), DataManager.getCarMeets().get(i).getPrivacy(),DataManager.getCarMeets().get(i).getLocation());
            carMeetArrayList.add(carMeet);
        }

        cmAdapter = new CarMeetAdapter(this,0,0, carMeetArrayList);
        lv = findViewById(R.id.lvCarMeeting);
        lv.setAdapter(cmAdapter);
    }
}