package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class EditMeetingActivity extends AppCompatActivity {
    Button btnChangeLocation, btnChangeTags, btnCancel, btnSave, btnDate, btnTime;
    Switch swhPrivacy;
    int verify = 0;
    Location location;
    TextView tvError, tvIsPublic;
    boolean privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meeting_activity);

        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnChangeLocation = findViewById(R.id.btnChangeLocation);
        btnChangeTags = findViewById(R.id.btnChangeTags);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        swhPrivacy = findViewById(R.id.swhPrivacy);
        tvError = findViewById(R.id.tvError);

        Intent intent = getIntent();
        privacy = intent.getBooleanExtra("PRIVACY", false);

        String prevDate = intent.getStringExtra("DATE"), prevTime  = intent.getStringExtra("TIME");
        location = new Location(intent.getFloatExtra("LATITUDE",0), intent.getFloatExtra("LONGITUDE",0));
        if (intent.getExtras() != null)
        {
            btnDate.setText(intent.getStringExtra("DATE"));
            btnTime.setText(intent.getStringExtra("TIME"));
            btnDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDateDialog();
                }
            });

            btnTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createTimeDialog();
                }
            });

            btnChangeLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickPointOnMap();
                }
            });

            swhPrivacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatePublicStatus(swhPrivacy.isChecked());
                }
            });
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnDate.getText().toString() != prevDate || btnTime.getText().toString() != prevTime || verify != 0)
                {
                    Intent intent = new Intent();
                    intent.putExtra("DATE", btnDate.getText().toString());
                    intent.putExtra("TIME", btnTime.getText().toString());
                    intent.putExtra("LATITUDE", location.getLatitude());
                    intent.putExtra("LONGITUDE", location.getLongitude());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    tvError.setText("Please change at least 1 thing.");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

    }


    static final int PICK_MAP_POINT_REQUEST = 999;  // The request code

    private void pickPointOnMap() {
        Intent pickPointIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(pickPointIntent, PICK_MAP_POINT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MAP_POINT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                LatLng latLng = (LatLng) data.getParcelableExtra("picked_point");
                verify = data.getIntExtra("VERIFY", 0);
                Toast.makeText(this, "Point Chosen: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();

                // Assuming location is a Location variable in your CarMeet class
                location = new Location();
                location.setLatitude((float) latLng.latitude);
                location.setLongitude((float) latLng.longitude);
            }
        }
    }

    public void createDateDialog() {
        Calendar systemCalender = Calendar.getInstance();
        int year = systemCalender.get(Calendar.YEAR);
        int month = systemCalender.get(Calendar.MONTH);
        int day = systemCalender.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new EditMeetingActivity.SetDate(), year, month, day);
        datePickerDialog.show();
    }

    public void createTimeDialog() {
        Calendar systemCalender = Calendar.getInstance();
        int hour = systemCalender.get(Calendar.HOUR_OF_DAY);
        int minute = systemCalender.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new EditMeetingActivity.SetYourTime(), hour, minute, true);
        timePickerDialog.show();
    }

    public class SetDate implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear += 1;
            String str = dayOfMonth + "/" + monthOfYear + "/" + year;
            Toast.makeText(EditMeetingActivity.this, str, Toast.LENGTH_LONG).show();
            btnDate.setText(str);
        }
    }

    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = hourOfDay + ":" + minute;
            btnTime.setText(str);
        }
    }

    public void updatePublicStatus(boolean isPublic) {
        if (isPublic) {
            tvIsPublic.setText("Public");
            privacy = true;
        } else {
            tvIsPublic.setText("Private");
            privacy = false;
        }
    }
}