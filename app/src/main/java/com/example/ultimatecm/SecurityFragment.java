package com.example.ultimatecm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecurityFragment extends Fragment {

    Button btnChangePassword;
    Person currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        btnChangePassword = view.findViewById(R.id.btnChangePassword);

        currentUser = getLoggedInPerson();


        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open dialog to change password
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.change_password_dialog);

                // Get views from dialog layout
                EditText etCurrentPassword = dialog.findViewById(R.id.etCurrentPassword);
                EditText etNewPassword = dialog.findViewById(R.id.etNewPassword);
                EditText etConfirmPassword = dialog.findViewById(R.id.etConfirmPassword);
                Button btnDone = dialog.findViewById(R.id.btnDone);

                // Handle "Done" button click
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentPassword = etCurrentPassword.getText().toString();
                        String newPassword = etNewPassword.getText().toString();
                        String confirmPassword = etConfirmPassword.getText().toString();

                        // Check if passwords match and current password is correct
                        if (currentUser.checkPassword(currentPassword) && newPassword.equals(confirmPassword)) {
                            // Passwords match and current password is correct
                            // Update the password and show success message
                            currentUser.setPassword(newPassword);
                            // Optionally, you can save the updated password to the database here
                            dialog.dismiss(); // Dismiss the dialog
                            // Show success message
                            Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Either current password is incorrect or new passwords do not match
                            // Show error message
                            String errorMessage;
                            if (!currentUser.checkPassword(currentPassword)) {
                                errorMessage = "Incorrect password.";
                            } else {
                                errorMessage = "Passwords do not match.";
                            }
                            // Create and show an AlertDialog for error message
                            new AlertDialog.Builder(requireContext())
                                    .setMessage(errorMessage)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                });

                dialog.show();
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
}
