package com.example.ultimatecm;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomePageFragment extends Fragment {
    TextView tvMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        tvMsg = view.findViewById(R.id.tvWelcomeMsg);

        String username = getUsername();
        if (username != null) {
            tvMsg.setText("Welcome! " + username + ".");
        } else {
            // Handle the case where username is null
            tvMsg.setText("Welcome!");
        }

        return view;
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
