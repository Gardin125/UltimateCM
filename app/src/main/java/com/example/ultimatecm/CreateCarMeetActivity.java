package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateCarMeetActivity extends AppCompatActivity {
    Button btnDone, btnSelectDate, btnSelectTime, btnAddTags, btnShowTags, btnLocation;
    boolean privacy;
    ImageView ivExit;
    Switch switchSettings;
    TextView tvIsPublic, tvError;
    int verify = 0;
    CarMeet carMeet;
    Location location;
    private ArrayList<String> selectedTagsList = new ArrayList<String>();
    private boolean[] tagCheckedState = new boolean[6]; // 6 tags

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        btnDone = findViewById(R.id.btnDone);
        btnLocation = findViewById(R.id.btnLocation);
        btnAddTags = findViewById(R.id.btnAddTags);
        btnShowTags = findViewById(R.id.btnShowTags);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        ivExit = findViewById(R.id.ivExit);
        switchSettings = findViewById(R.id.switchSettings);
        tvIsPublic = findViewById(R.id.tvIsPublic);
        tvError = findViewById(R.id.tvError);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSelectDate.getText().toString().equals("Select Date")) {
                    tvError.setText("Please select a date.");
                } else if (btnSelectTime.getText().toString().equals("Select Time")) {
                    tvError.setText("Please select a time.");
                } else if (verify == 0) {
                    tvError.setText("Please fill all the information before creating a new Car Meet");
                } else {
                    carMeet = new CarMeet(btnSelectDate.getText().toString(), btnSelectTime.getText().toString()
                            , selectedTagsList, privacy, location, getUsername());
                    DataManager.addNewCarMeet(carMeet);
                    finish();
                }
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPointOnMap();
            }
        });

        btnAddTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CreateCarMeetActivity.this);
                dialog.setContentView(R.layout.dialog_tags);

                // Find checkboxes and button in the dialog
                CheckBox cbAmericans = dialog.findViewById(R.id.cbAmericans);
                CheckBox cbOldCars = dialog.findViewById(R.id.cbOldCars);
                CheckBox cbEveryoneWelcomed = dialog.findViewById(R.id.cbAllCars);
                CheckBox cbNewCars = dialog.findViewById(R.id.cbNewCars);
                CheckBox cbPublic = dialog.findViewById(R.id.cbPublic);
                CheckBox cbPrivate = dialog.findViewById(R.id.cbPrivate);

                // Set the checked state based on the array
                cbAmericans.setChecked(tagCheckedState[0]);
                cbOldCars.setChecked(tagCheckedState[1]);
                cbEveryoneWelcomed.setChecked(tagCheckedState[2]);
                cbNewCars.setChecked(tagCheckedState[3]);
                cbPublic.setChecked(tagCheckedState[4]);
                cbPrivate.setChecked(tagCheckedState[5]);

                Button btnDoneTags = dialog.findViewById(R.id.btnDoneTags);

                // Handle the "Done" button click
                btnDoneTags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the boolean array with the current checked state
                        tagCheckedState[0] = cbAmericans.isChecked();
                        tagCheckedState[1] = cbOldCars.isChecked();
                        tagCheckedState[2] = cbEveryoneWelcomed.isChecked();
                        tagCheckedState[3] = cbNewCars.isChecked();
                        tagCheckedState[4] = cbPublic.isChecked();
                        tagCheckedState[5] = cbPrivate.isChecked();

                        // Process selected tags
                        updateSelectedTags();

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnShowTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CreateCarMeetActivity.this);
                dialog.setContentView(R.layout.dialog_tags);

                // Find checkboxes and button in the dialog
                CheckBox cbAmericans = dialog.findViewById(R.id.cbAmericans);
                CheckBox cbOldCars = dialog.findViewById(R.id.cbOldCars);
                CheckBox cbEveryoneWelcomed = dialog.findViewById(R.id.cbAllCars);
                CheckBox cbNewCars = dialog.findViewById(R.id.cbNewCars);
                CheckBox cbPublic = dialog.findViewById(R.id.cbPublic);
                CheckBox cbPrivate = dialog.findViewById(R.id.cbPrivate);

                // Disable all checkboxes
                cbAmericans.setEnabled(false);
                cbOldCars.setEnabled(false);
                cbEveryoneWelcomed.setEnabled(false);
                cbNewCars.setEnabled(false);
                cbPublic.setEnabled(false);
                cbPrivate.setEnabled(false);

                // Set the checked state based on the array
                cbAmericans.setChecked(tagCheckedState[0]);
                cbOldCars.setChecked(tagCheckedState[1]);
                cbEveryoneWelcomed.setChecked(tagCheckedState[2]);
                cbNewCars.setChecked(tagCheckedState[3]);
                cbPublic.setChecked(tagCheckedState[4]);
                cbPrivate.setChecked(tagCheckedState[5]);

                Button btnDoneTags = dialog.findViewById(R.id.btnDoneTags);

                // Handle the "Done" button click
                btnDoneTags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateDialog();
            }
        });

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTimeDialog();
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublicStatus(switchSettings.isChecked());
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

    public void updatePublicStatus(boolean isPublic) {
        if (isPublic) {
            tvIsPublic.setText("Public");
            privacy = true;
        } else {
            tvIsPublic.setText("Private");
            privacy = false;
        }
    }

    public void createDateDialog() {
        Calendar systemCalender = Calendar.getInstance();
        int year = systemCalender.get(Calendar.YEAR);
        int month = systemCalender.get(Calendar.MONTH);
        int day = systemCalender.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new CreateCarMeetActivity.SetDate(), year, month, day);
        datePickerDialog.show();
    }

    public void createTimeDialog() {
        Calendar systemCalender = Calendar.getInstance();
        int hour = systemCalender.get(Calendar.HOUR_OF_DAY);
        int minute = systemCalender.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new CreateCarMeetActivity.SetYourTime(), hour, minute, true);
        timePickerDialog.show();
    }

    public class SetDate implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear += 1;
            String str = dayOfMonth + "/" + monthOfYear + "/" + year;
            Toast.makeText(CreateCarMeetActivity.this, str, Toast.LENGTH_LONG).show();
            btnSelectDate.setText(str);
        }
    }

    public class SetYourTime implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = hourOfDay + ":" + minute;
            btnSelectTime.setText(str);
        }
    }

    private void updateSelectedTags() {
        selectedTagsList.clear(); // Clear the ArrayList

        if (tagCheckedState[0]) {
            selectedTagsList.add("#Americans");
        }
        if (tagCheckedState[1]) {
            selectedTagsList.add("#Old Cars");
        }
        if (tagCheckedState[2]) {
            selectedTagsList.add("#Everyone Welcomed");
        }
        if (tagCheckedState[3]) {
            selectedTagsList.add("#New Cars");
        }
        if (tagCheckedState[4]) {
            selectedTagsList.add("#Public");
        }
        if (tagCheckedState[5]) {
            selectedTagsList.add("#Private");
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