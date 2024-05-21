package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    ImageView ivExit;
    EditText etEmail, etFirstName, etLastName, etUsername, etPassword;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ivExit = findViewById(R.id.ivExit);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        btnFinish = (Button) findViewById(R.id.btnFinish);

        Intent intent = getIntent();
        boolean check = intent.getBooleanExtra("CHECK",false);
        if (check)
            etEmail.setText(intent.getStringExtra("EMAIL"));


        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int emailLength = etEmail.getText().toString().length();
                int usernameLength = etUsername.getText().toString().length();
                int firstNameLength = etFirstName.getText().toString().length();
                int lastNameLength = etLastName.getText().toString().length();
                int passwordLength = etPassword.getText().toString().length();

                String email = etEmail.getText().toString(), username = etUsername.getText().toString(), firstName = etFirstName.getText().toString(), lastName = etLastName.getText().toString(), password = etPassword.getText().toString();
                boolean allFieldsFilled = emailLength > 0 && usernameLength > 0 && firstNameLength > 0 && lastNameLength > 0 && passwordLength > 0;

                boolean checkUsername = false;
                for (int i = 0; i < DataManager.getPeople().size(); i++) {
                    if (DataManager.getPeople().get(i).getUsername().equalsIgnoreCase(username)) {
                        checkUsername = true;
                    }
                }

                boolean checkEmail = false;
                for (int i = 0; i < DataManager.getPeople().size(); i++) {
                    if (DataManager.getPeople().get(i).getEmail().equalsIgnoreCase(email)) {
                        checkEmail = true;
                    }
                }

                if (checkEmail)
                    userIsAlrExist("Email is already exist. Please try to log in");
                else if (checkUsername)
                    userIsAlrExist("Username is already exist. Please change the username");
                else {

                    if (allFieldsFilled) {
                        DBManager.getAuth().createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Person person = new Person(firstName, lastName, email, username, password);
                                    DataManager.addNewPerson(person);
                                    Log.d("User Auth", "User signed successfully");
                                    Toast.makeText(SignupActivity.this, "Successful sign up", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                    startActivity(intent);
                                } else {

                                }
                            }
                        });

                    } else {
                        fillAllfields("Please fill all the blank fields");
                    }
                }
            }
        });
    }

    public void userIsAlrExist(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User is already exists");
        builder.setMessage(msg);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void fillAllfields(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fill all fields");
        builder.setMessage(msg);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}