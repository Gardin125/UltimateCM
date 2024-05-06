package com.example.ultimatecm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        TextView tvMsg = findViewById(R.id.tvWelcomeMsg);
        tvMsg.setText("Welcome! " + getUsername() + ".");

        // Initialize the BottomNavigationView and set the listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomePageFragment());
                return true;
            } else if (item.getItemId() == R.id.carMeet) {
                replaceFragment(new CarMeetSectionFragment());
                return true;
            } else if (item.getItemId() == R.id.security) {
                replaceFragment(new SecurityFragment());
                return true;
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new PersonalInfoFragment());
                return true;
            } else if (item.getItemId() == R.id.logout) {
                finish();
                return true;
            } /*else if (item.getItemId() == R.id.carGame) {
                replaceFragment();
            } */
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
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
