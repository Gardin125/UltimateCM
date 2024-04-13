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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class EditMeetingActivity extends AppCompatActivity {
    EditText etDate, etTime;
    Button btnChangeLocation, btnChangeTags, btnCancel, btnSave;
    int verify = 0;
    Location location;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_meeting_activity);

        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        btnChangeLocation = findViewById(R.id.btnChangeLocation);
        btnChangeTags = findViewById(R.id.btnChangeTags);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        tvError = findViewById(R.id.tvError);

        Intent intent = getIntent();
        if (intent.getExtras() != null)
        {
            etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDateDialog();
                }
            });

            etTime.setOnClickListener(new View.OnClickListener() {
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
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.getText().toString().length() > 0 && etTime.getText().toString().length() > 0 && verify != 0)
                {
                    Intent intent = new Intent();
                    intent.putExtra("DATE", etDate.getText().toString());
                    intent.putExtra("TIME", etTime.getText().toString());
                    intent.putExtra("LOCATION LATITUDE", location.getLatitude());
                    intent.putExtra("LOCATION LONGITUDE", location.getLongitude());
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    tvError.setText("Please fill all the information .");
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
            etDate.setText(str);
        }
    }

    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = hourOfDay + ":" + minute;
            etTime.setText(str);
        }
    }
}