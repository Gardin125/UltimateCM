package com.example.ultimatecm;

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

import com.example.ultimatecm.databinding.EditMeetingActivityBinding;

import java.util.ArrayList;

public class JoinCarMeetActivity extends AppCompatActivity {
    ImageView ivExit;

    ArrayList<CarMeet> carMeetArrayList;
    ListView lv;
    CarMeetAdapter cmAdapter;
    CarMeet lastSelected;

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
            CarMeet carMeet = new CarMeet(DataManager.getCarMeets().get(i).getDate(), DataManager.getCarMeets().get(i).getTime(), DataManager.getCarMeets().get(i).getTags(), DataManager.getCarMeets().get(i).getPrivacy(),DataManager.getCarMeets().get(i).getLocation(),DataManager.getCarMeets().get(i).getCreator());
            carMeetArrayList.add(carMeet);
        }

        cmAdapter = new CarMeetAdapter(this,0,0, carMeetArrayList);
        lv = findViewById(R.id.lvCarMeeting);
        lv.setAdapter(cmAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JoinCarMeetActivity.this);
                builder.setMessage("Do you want to join this meeting?")
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

    public String getUsername()
    {
        String username = "";
        for (int i = 0; i < DataManager.getPeople().size(); i++ )
        {
            if (DBManager.getCurrentUserEmail().equals(DataManager.getPeople().get(i).getEmail()))
                username = DataManager.getPeople().get(i).getUsername();
        }
        return username;
    }

}