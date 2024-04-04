package com.example.ultimatecm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CarMeetAdapter extends ArrayAdapter<CarMeet> {
    Context context;
    List<CarMeet> objects;

    public CarMeetAdapter (Context context, int resource, int textViewResourceId, List<CarMeet> objects)
    {
        super(context,resource,textViewResourceId,objects);

        this.context = context;
        this.objects = objects;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater li = ((Activity)context).getLayoutInflater();
        View view = li.inflate(R.layout.car_meet_layout,parent,false);

        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvPrivacy = view.findViewById(R.id.tvPrivacy);

        CarMeet temp = objects.get(pos);

        tvTime.setText(temp.getTime());
        tvDate.setText(temp.getDate());
        boolean privacy = temp.getPrivacy();
        if (privacy)
            tvPrivacy.setText("Public");
        else
            tvPrivacy.setText("Private");

        return view;
    }
}
