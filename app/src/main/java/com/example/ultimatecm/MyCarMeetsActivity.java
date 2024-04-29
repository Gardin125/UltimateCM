package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyCarMeetsActivity extends AppCompatActivity {
    ImageView ivExit;
    ListView lvMyCM;
    CarMeet lastSelected;
    CarMeetAdapter carMeetAdapter;
    ArrayList<CarMeet> carMeets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_meets);
        ivExit = findViewById(R.id.ivSecurity);
        lvMyCM = findViewById(R.id.lvMyCM);

        for (int i = 0; i < DataManager.getCarMeets().size(); i++)
        {
            if (DataManager.getCarMeets().get(i).getCreator().equals(getUsername()))
                carMeets.add(DataManager.getCarMeets().get(i));
        }

        carMeetAdapter = new CarMeetAdapter(this,0,0, carMeets);
        lvMyCM.setAdapter(carMeetAdapter);

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvMyCM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelected = carMeetAdapter.getItem(position);
                Intent intent = new Intent(MyCarMeetsActivity.this, EditMeetingActivity.class);
                intent.putExtra("DATE", lastSelected.getDate());
                intent.putExtra("TIME", lastSelected.getTime());
                intent.putExtra("LONGITUDE", lastSelected.getLocation().getLongitude());
                intent.putExtra("LATITUDE", lastSelected.getLocation().getLatitude());
                intent.putExtra("TAGS", lastSelected.getTags());
                intent.putExtra("PRIVACY", lastSelected.getPrivacy());
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0)
        {
            if (resultCode == RESULT_OK)
            {
                String date = data.getExtras().getString("DATE");
                String time = data.getExtras().getString("TIME");
                float longitude = data.getExtras().getFloat("LONGITUDE");
                float latitude = data.getExtras().getFloat("LATITUDE");
                ArrayList<Tag> tags = (ArrayList<Tag>) data.getExtras().get("TAGS");
                boolean privacy = data.getExtras().getBoolean("PRIVACY");
                carMeetAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Data Saved.", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Data Canceled.", Toast.LENGTH_LONG).show();
            }
        }
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