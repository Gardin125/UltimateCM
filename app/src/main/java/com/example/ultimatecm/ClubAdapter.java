package com.example.ultimatecm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClubAdapter extends ArrayAdapter<Club> {

    Context context;
    List<Club> objects;

    public ClubAdapter (Context context, int resource, int textViewResourceId, List<Club> objects)
    {
        super(context,resource,textViewResourceId,objects);

        this.context = context;
        this.objects = objects;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater li = ((Activity)context).getLayoutInflater();
        View view = li.inflate(R.layout.club_layout,parent,false);

        TextView tvClubName = view.findViewById(R.id.tvClubName);
        TextView tvPrivacy = view.findViewById(R.id.tvPrivacy);

        Club temp = objects.get(pos);

        tvClubName.setText(temp.getClubName());
        boolean privacy = temp.getPrivacy();
        if (privacy)
            tvPrivacy.setText("Public");
        else
            tvPrivacy.setText("Private");

        return view;
    }
}
