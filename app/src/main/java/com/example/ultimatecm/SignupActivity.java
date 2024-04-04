package com.example.ultimatecm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    TextView tvError;
    EditText etEmail, etFirstName, etLastName, etUsername, etPassword;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ivExit = findViewById(R.id.ivExit);
        tvError = findViewById(R.id.tvError);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        btnFinish = (Button) findViewById(R.id.btnFinish);

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
                if (allFieldsFilled) {
                    DBManager.getAuth().createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                ArrayList<CarMeet> myCarMeets = new ArrayList<CarMeet>();
                                ArrayList<Club> myClubs = new ArrayList<Club>();
                                Person person = new Person(firstName, lastName, email, username, password, myCarMeets, myClubs);
                                DataManager.addNewPerson(person);
                                Log.d("User Auth", "User signed successfully");
                                Toast.makeText(SignupActivity.this, "Successful sign up", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, task.getException().getClass().getName(), Toast.LENGTH_SHORT).show();
                            }
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                tvError.setText("Account is already exist.");
                        }
                    });

                } else {
                    tvError.setText("Please fill all the blank fields");
                }
            }
        });
    }
}