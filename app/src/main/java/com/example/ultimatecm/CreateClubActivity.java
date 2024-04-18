package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CreateClubActivity extends AppCompatActivity {
    EditText etClubName, etClubDescription;
    TextView tvError, tvMaxLength, tvIsPublic;
    Button btnFinish;
    ImageView ivExit;
    Switch aSwitch;
    boolean privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        etClubName = findViewById(R.id.etClubName);
        etClubDescription = findViewById(R.id.etClubDescription);
        tvError = findViewById(R.id.tvError);
        tvMaxLength = findViewById(R.id.tvMaxLength);
        tvIsPublic = findViewById(R.id.tvIsPublic);
        btnFinish = findViewById(R.id.btnFinish);
        ivExit = findViewById(R.id.ivExit);
        aSwitch = findViewById(R.id.schSettings);

        etClubDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                InputFilter[] filters = etClubDescription.getFilters();
                int maxLength = -1;  // Default value if not found

                // Check if there are filters and if the first filter is a LengthFilter
                if (filters != null && filters.length > 0 && filters[0] instanceof InputFilter.LengthFilter) {
                    maxLength = ((InputFilter.LengthFilter) filters[0]).getMax();
                }

                // Check if the maximum character limit is reached
                if (charSequence.length() == maxLength) {
                    // Display a Toast indicating that the maximum character limit has been reached
                    Toast.makeText(CreateClubActivity.this, "Max Characters Limit Reached", Toast.LENGTH_SHORT).show();
                }

                // Update tvMaxLength with the current character count
                tvMaxLength.setText(String.format("%d/%d", charSequence.length(), maxLength));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this example
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etClubDescription.getText().toString().length() == 0) {
                    tvError.setText("Please enter club description.");
                } else if (etClubName.getText().toString().length() == 0){
                    tvError.setText("Please enter club name.");
                } else if (etClubDescription.getText().toString().length() <= 3) {
                    tvError.setText("Please enter club description that is more then 3 letter.");
                } else if (etClubName.getText().toString().length() <= 3){
                    tvError.setText("Please enter club name that is more then 3 letter.");
                } else {
                    Club club = new Club();
                    club.setClubName(etClubName.getText().toString());
                    club.setClubDescription(etClubDescription.getText().toString());
                    club.setPrivacy(privacy);
                    DataManager.addNewClub(club);
                    finish(); // FOR NOW!
                }
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublicStatus(aSwitch.isChecked());
            }
        });
    }

    public void updatePublicStatus(boolean isPublic) {
        if (isPublic) {
            tvIsPublic.setText("Public");
            privacy = true;
        } else {
            tvIsPublic.setText("Private");
            privacy = false;
        }
    }
}