package com.example.ultimatecm;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalInfoFragment extends Fragment {
    EditText etFirstName, etLastName, etUsername;
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etUsername = view.findViewById(R.id.etUsername);
        btnSave = view.findViewById(R.id.btnSave);

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

        etUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUsername.setText("");
            }
        });
        etFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etFirstName.setText("");
            }
        });
        etLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLastName.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String username = etUsername.getText().toString();
                boolean validFN, validLN, validUN;
                validFN = firstName.length() >= 3 && !containsNumber(firstName);
                validLN = lastName.length() >= 3 && !containsNumber(lastName);
                validUN = username.length() >= 3 && !containsSpecialCharacter(username);
                boolean validAll = validFN && validLN && validUN;

                if (validAll)
                {
                    Person currentUser = getLoggedInPerson();
                    currentUser.setFirstName(firstName);
                    currentUser.setLastName(lastName);
                    currentUser.setUsername(username);
                    DataManager.updatePeopleList();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Updated!");
                    builder.setMessage("Personal Info Updated.");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Failed to update.");
                    builder.setMessage("Personal Info failed to update. Please make sure that the first name & last name not contains a number and " +
                            "the username doesn't contains a special character");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });

        return view;
    }

    public Person getLoggedInPerson() {
        String currentUserEmail = DBManager.getCurrentUserEmail();
        for (Person person : DataManager.getPeople()) {
            if (currentUserEmail.equals(person.getEmail())) {
                return person; // Return the person object for the logged-in user
            }
        }
        return null; // If the logged-in user is not found
    }

    public static boolean containsNumber(String str) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean containsSpecialCharacter(String str) {
        // Regex to match any special character
        Pattern pattern = Pattern.compile("[!@#$%^&*()_+\\-={}|:\"<>?\\[\\]\\\\;',./]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
