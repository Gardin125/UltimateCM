package com.example.ultimatecm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {
    Button btnMeetingSection;
    ImageView ivPfp;
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btnMeetingSection = findViewById(R.id.btnMeetingSection);
        ivPfp = findViewById(R.id.ivPfp);
        tvMsg = findViewById(R.id.tvWelcomeUser);

        for (int i = 0; i < DataManager.getPeople().size(); i++ )
        {
            if (DBManager.getCurrentUserEmail().equals(DataManager.getPeople().get(i).getEmail()))
                tvMsg.setText("Welcome! " + DataManager.getPeople().get(i).getUsername());
        }



        btnMeetingSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, CarMeetSectionActivity.class);
                startActivity(intent);
            }
        });

        ivPfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(HomePageActivity.this, ivPfp);
                popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_personal_info) {
                            Intent intent = new Intent(HomePageActivity.this, PersonalInfoActivity.class);
                            startActivity(intent);
                        } else if (id == R.id.action_security) {
                            Intent intent = new Intent(HomePageActivity.this, SecurityActivity.class);
                            startActivity(intent);
                        } else if (id == R.id.action_log_out) {
                            finish();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
}