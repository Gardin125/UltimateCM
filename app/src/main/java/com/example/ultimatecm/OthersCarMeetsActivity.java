package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class OthersCarMeetsActivity extends AppCompatActivity {
    //Car meets that i joined for.
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
                finish();
            }
        });

        for (int i = 0; i < DataManager.getPeople().size(); i++) {
            if (DataManager.getPeople().get(i).username == getUsername())
                currentUser = DataManager.getPeople().get(i);
        }

        ArrayList<CarMeet> othersCMList = new ArrayList<CarMeet>();
        for (int i = 0; i < DataManager.getCarMeets().size(); i++) {
            if (!DataManager.getCarMeets().get(i).getCreator().equals(getUsername())) {
                othersCMList.add(DataManager.getCarMeets().get(i));
            }
        }


        carMeetAdapter = new CarMeetAdapter(this, 0, 0, othersCMList);
        lvOthersCM.setAdapter(carMeetAdapter);
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