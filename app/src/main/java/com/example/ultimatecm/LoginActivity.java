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

                // Attempt to sign in with email and password
                DBManager.getAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // If login is successful, navigate to the home page
                                    openHomePage();
                                } else {
                                    // If login fails, show an error message
                                    showInvalidCredentialsDialog("Invalid email or password. Please try again.");
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
        DataManager.pullClubs();
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