package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityActivity extends AppCompatActivity {
    ImageView ivExit;
    Button btnChangePassword;
    Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ivExit = findViewById(R.id.ivExit);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        currentUser = getLoggedInPerson();

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open dialog to change password
                Dialog dialog = new Dialog(SecurityActivity.this);
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
                            Toast.makeText(SecurityActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(SecurityActivity.this);
                            builder.setMessage(errorMessage)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button
                                        }
                                    });
                            // Create the AlertDialog object and show it
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                });

                dialog.show();
            }
        });


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