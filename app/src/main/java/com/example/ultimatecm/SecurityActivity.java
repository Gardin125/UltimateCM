package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class SecurityActivity extends AppCompatActivity {
    ImageView ivExit;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ivExit = findViewById(R.id.ivExit);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SecurityActivity.this);
                dialog.setContentView(R.layout.change_passowrd_dialog);
                dialog.show();
            }
        });
    }
}