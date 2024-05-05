package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyCarMeetsActivity extends AppCompatActivity {
    ImageView ivExit;
    ListView lvMyCM;
    CarMeet lastSelected;
    CarMeetAdapter carMeetAdapter;
    Person currentUser;
    ArrayList<CarMeet> myCMList; // Declare myCMList as a member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_meets);
        ivExit = findViewById(R.id.ivSecurity);
        lvMyCM = findViewById(R.id.lvMyCM);
        currentUser = new Person();

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (int i = 0; i < DataManager.getPeople().size(); i++) {
            if (DataManager.getPeople().get(i).username.equals(getUsername()))
                currentUser = DataManager.getPeople().get(i);
        }

        myCMList = new ArrayList<>(); // Initialize myCMList
        for (int i = 0; i < DataManager.getCarMeets().size(); i++) {
            if (DataManager.getCarMeets().get(i).getCreator().equals(getUsername()))
                myCMList.add(DataManager.getCarMeets().get(i));
        }

        carMeetAdapter = new CarMeetAdapter(this, 0, 0, myCMList);
        lvMyCM.setAdapter(carMeetAdapter);

        lvMyCM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelected = myCMList.get(position);
                Intent intent = new Intent(MyCarMeetsActivity.this, EditMeetingActivity.class);
                intent.putExtra("DATE", lastSelected.getDate());
                intent.putExtra("TIME", lastSelected.getTime());
                intent.putExtra("LONGITUDE", lastSelected.getLocation().getLongitude());
                intent.putExtra("LATITUDE", lastSelected.getLocation().getLatitude());
                intent.putExtra("TAGS", lastSelected.getTags());
                startActivityForResult(intent, 0); // Start EditMeetingActivity with requestCode 0
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String date = data.getStringExtra("DATE");
                String time = data.getStringExtra("TIME");
                float longitude = data.getFloatExtra("LONGITUDE", 0);
                float latitude = data.getFloatExtra("LATITUDE", 0);
                ArrayList<String> tags = data.getStringArrayListExtra("TAGS");
                Location updatedLocation = new Location(latitude, longitude);

                // Find the index of the lastSelected in the myCMList
                int index = myCMList.indexOf(lastSelected);

                if (index != -1) {
                    // Update the lastSelected object directly from myCMList
                    lastSelected = myCMList.get(index);
                    lastSelected.setDate(date);
                    lastSelected.setTime(time);
                    lastSelected.setLocation(updatedLocation);
                    lastSelected.setTags(tags);

                    // Update the CarMeet object in the database
                    DataManager.updateCarMeet(lastSelected);

                    // Notify the adapter of the data change
                    carMeetAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "Data Saved.", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Data Canceled.", Toast.LENGTH_LONG).show();
            }
        }
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
