package com.example.ultimatecm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.ultimatecm.CreateCarMeetActivity;
import com.example.ultimatecm.JoinCarMeetActivity;
import com.example.ultimatecm.MyCarMeetsActivity;
import com.example.ultimatecm.OthersCarMeetsActivity;

public class CarMeetSectionFragment extends Fragment {

    Button btnCreateMeeting, btnJoinMeeting, btnMyCarMeets, btnOthersCarMeets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_meet_section, container, false);

        btnCreateMeeting = view.findViewById(R.id.btnCreateCarMeet);
        btnJoinMeeting = view.findViewById(R.id.btnJoinCarMeet);
        btnMyCarMeets = view.findViewById(R.id.btnMyCM);
        btnOthersCarMeets = view.findViewById(R.id.btnOthersCM);

        btnCreateMeeting.setOnClickListener(v -> startActivity(new Intent(requireContext(), CreateCarMeetActivity.class)));
        btnJoinMeeting.setOnClickListener(v -> startActivity(new Intent(requireContext(), JoinCarMeetActivity.class)));
        btnMyCarMeets.setOnClickListener(v -> startActivity(new Intent(requireContext(), MyCarMeetsActivity.class)));
        btnOthersCarMeets.setOnClickListener(v -> startActivity(new Intent(requireContext(), OthersCarMeetsActivity.class)));

        return view;
    }
}
