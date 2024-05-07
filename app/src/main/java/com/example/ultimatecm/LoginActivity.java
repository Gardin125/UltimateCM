package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnSignUp, btnLogin;
    boolean emailExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Check if email and password are not empty
                if (email.isEmpty() || password.isEmpty()) {
                    // Show an error message if either email or password is empty
                    // You can customize this part based on your requirements
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
                                    // If login is successful, navigate to the home page
                                    openHomePage();
                                } else {
                                    // If email does not exist in the database, prompt user to fix email or create new account
                                    if (!emailExists) {
                                        fixEmailOrCreateNewUserDialog("Please make sure that the email is correct or create a new account with this email");
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

        Intent intent = new Intent(LoginActivity.this, DataChangeService.class);
        startService(intent);

        DataManager.pullPeople();
        DataManager.pullCarMeets();
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
        intent.putExtra("EMAIL", etEmail.getText().toString());
        startActivity(intent);
    }

    public void openSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}