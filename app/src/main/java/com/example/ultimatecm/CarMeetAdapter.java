package com.example.ultimatecm;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CarMeetAdapter extends ArrayAdapter<CarMeet> {
    Context context;
    List<CarMeet> objects;

    // Constructor of the meetings adapter that receives the data source, which is from the CarMeet class
    public CarMeetAdapter(Context context, int resource, int textViewResourceId, List<CarMeet> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.objects = objects;
    }

    // The getView method gets the position of the item, creates or reuses a view, and populates it with the meeting data
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater li = ((Activity) context).getLayoutInflater();
        View view = li.inflate(R.layout.car_meet_layout, parent, false);

        TextView tvTime = view.findViewById(R.id.tvTime); // Attach text and time to the view
        TextView tvDate = view.findViewById(R.id.tvDate); // Attach text and date to the view
        TextView tvParticipants = view.findViewById(R.id.tvParticipants);
        TextView tvCity = view.findViewById(R.id.tvCity); // Display the city name

        CarMeet temp = objects.get(pos);

        tvTime.setText(temp.getTime()); // Set the time into the appropriate text view
        tvDate.setText(temp.getDate()); // Set the date into the appropriate text view
        tvParticipants.setText(Integer.toString(temp.getParticipants()));

        // Get the city name asynchronously
        new ReverseGeocodingTask(tvCity).execute(temp.getLocation().getLatitude(), temp.getLocation().getLongitude());

        return view;
    }

    // AsyncTask to perform reverse geocoding asynchronously
    private class ReverseGeocodingTask extends AsyncTask<Float, Void, String> {
        private TextView textView;

        public ReverseGeocodingTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(Float... params) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            float latitude = params[0];
            float longitude = params[1];

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    return address.getLocality(); // Get the city name
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String cityName) {
            if (cityName != null) {
                textView.setText(cityName);
            } else {
                textView.setText("Unknown"); // Handle cases where city name is not found
            }
        }
    }
}


