package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ultimatecm.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.Manifest;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float latitude;
    private float longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        boolean fromCreateCarMeet = intent.getBooleanExtra("fromCreateCarMeet", false);
        boolean fromEditMeeting = intent.getBooleanExtra("fromEditMeeting", false);

        if (fromCreateCarMeet || fromEditMeeting) {
            // Allow the user to pick a new point
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("picked_point", latLng);
                    returnIntent.putExtra("VERIFY", 1);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish(); // Close the MapsActivity regardless of which activity started it
                }
            });
        } else {
            // Retrieve latitude and longitude from intent extras
            float latitude = intent.getFloatExtra("latitude", 0);
            float longitude = intent.getFloatExtra("longitude", 0);
            LatLng carMeetLocation = new LatLng(latitude, longitude);

            // Add a marker at the car meet location and move the camera
            MarkerOptions mO = new MarkerOptions();
            mO.title("Car Meet Location");
            mO.position(carMeetLocation);
            mMap.addMarker(mO);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carMeetLocation, 15));
        }
    }

}
