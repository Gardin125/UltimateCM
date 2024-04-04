package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class SecurityActivity extends AppCompatActivity {
    ImageView ivExit;
    Switch switchTwoFactor;
    TextView tvError;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ivExit = findViewById(R.id.ivExit);
        switchTwoFactor = findViewById(R.id.switchTwoFactor);
        tvError = findViewById(R.id.tvError);

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchTwoFactor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            changeSwitch();
        });
    }

    private void changeSwitch() {
        if (flag) {
            flag = !flag; // Update flag before showing dialog
        } else {
            showConfirmationDialog("Disable Two-Factor Authentication?", "Are you sure you want to disable Two-Factor Authentication?");
        }
    }

    private void showConfirmationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvError.setText("Two-Factor Authentication changed successfully");
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvError.setText("Two-Factor Authentication unchanged");
                flag = true;
                switchTwoFactor.setChecked(!switchTwoFactor.isChecked());
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}