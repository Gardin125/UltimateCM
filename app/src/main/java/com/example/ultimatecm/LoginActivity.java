package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    CheckBox cbRememberMe;
    Button btnSignUp, btnLogin;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    boolean emailExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadSavedPreferences();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Check if email and password are not empty
                if (email.isEmpty() || password.isEmpty()) {
                    // Show an error message if either email or password is empty
                    showInvalidCredentialsDialog("Please enter both email and password.");
                    return;
                }

                // Check if the entered email exists in the database
                emailExists = false;
                for (Person p : DataManager.getPeople()) {
                    if (p.getEmail().equals(email)) {
                        emailExists = true;
                        break;
                    }
                }

                // Attempt to sign in with email and password
                DBManager.getAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // If login is successful, save preferences if checkbox is checked
                                    if (cbRememberMe.isChecked()) {
                                        savePreferences(email, password);
                                    } else {
                                        clearPreferences();
                                    }
                                    // Navigate to the home page
                                    openHomePage();
                                } else {
                                    // If email does not exist in the database, prompt user to fix email or create new account
                                    if (!emailExists) {
                                        fixEmailOrCreateNewUserDialog("Please make sure that the email is correct or create a new account with this email");
                                    } else {
                                        showInvalidCredentialsDialog("Password is incorrect. Please try again.");
                                    }
                                }
                            }
                        });
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });

        DataManager.pullPeople();
    }

    private void loadSavedPreferences() {
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        if (!email.isEmpty() && !password.isEmpty()) {
            etEmail.setText(email);
            etPassword.setText(password);
            cbRememberMe.setChecked(true);
        }
    }

    private void savePreferences(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void clearPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }

    private void showInvalidCredentialsDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid Credentials");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void fixEmailOrCreateNewUserDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Email is wrong or user doesn't exist");
        builder.setMessage(message);
        builder.setPositiveButton("Fix Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Create New User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent createNewAccIntent = new Intent(LoginActivity.this, SignupActivity.class);
                createNewAccIntent.putExtra("EMAIL", etEmail.getText().toString());
                createNewAccIntent.putExtra("CHECK", true); // Set CHECK to true
                startActivity(createNewAccIntent);
            }
        });
        builder.create().show();
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void openSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
