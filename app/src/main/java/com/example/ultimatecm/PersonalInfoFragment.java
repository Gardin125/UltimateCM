package com.example.ultimatecm;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class PersonalInfoFragment extends Fragment {
    EditText etFirstName, etLastName, etUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etUsername = view.findViewById(R.id.etUsername);

        // Get the email of the current logged-in user
        String currentUserEmail = DBManager.getCurrentUserEmail();

        // Find the current logged-in person
        Person currentPerson = DataManager.getCurrentLoggedInPersonByEmail(currentUserEmail);
        if (currentPerson != null) {
            // Set the first name, last name, and username in the EditText fields
            etFirstName.setText(currentPerson.getFirstName());
            etLastName.setText(currentPerson.getLastName());
            etUsername.setText(currentPerson.getUsername());
        }

        // Set OnClickListener for etUsername if needed
        etUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event if necessary
            }
        });

        return view;
    }
}
