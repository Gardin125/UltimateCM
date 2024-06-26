package com.example.ultimatecm;

import android.app.Activity;  // Imports necessary classes and packages for Android development.
import android.content.Context;  // Imports necessary classes and packages for Android development.
import android.location.Address;  // Imports necessary classes and packages for Android development.
import android.location.Geocoder;  // Imports necessary classes and packages for Android development.
import android.os.AsyncTask;  // Imports necessary classes and packages for Android development.
import android.view.LayoutInflater;  // Imports necessary classes and packages for Android development.
import android.view.View;  // Imports necessary classes and packages for Android development.
import android.view.ViewGroup;  // Imports necessary classes and packages for Android development.
import android.widget.ArrayAdapter;  // Imports necessary classes and packages for Android development.
import android.widget.TextView;  // Imports necessary classes and packages for Android development.

import com.google.android.gms.maps.model.LatLng;  // Imports necessary classes and packages for Android development.

import java.io.IOException;  // Imports necessary classes and packages for Android development.
import java.util.List;  // Imports necessary classes and packages for Android development.
import java.util.Locale;  // Imports necessary classes and packages for Android development.

public class CarMeetAdapter extends ArrayAdapter<CarMeet> {
    Context context;
    List<CarMeet> objects;

    // Constructor of the meetings adapter that receives the data source, which is from the CarMeet class
    public CarMeetAdapter(Context context, int resource, int textViewResourceId, List<CarMeet> objects) {
        super(context, resource, textViewResourceId, objects);  // Calls the superclass constructor of ArrayAdapter.

        this.context = context;
        this.objects = objects;
    }

    // The getView method gets the position of the item, creates or reuses a view, and populates it with the meeting data
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater li = ((Activity) context).getLayoutInflater();  // Gets the LayoutInflater from the current activity.
        View view = li.inflate(R.layout.car_meet_layout, parent, false);  // Inflates the custom layout for each list item.

        TextView tvTime = view.findViewById(R.id.tvTime); // Attach text and time to the view  // Finds and assigns the TextView for time.
        TextView tvDate = view.findViewById(R.id.tvDate); // Attach text and date to the view  // Finds and assigns the TextView for date.
        TextView tvParticipants = view.findViewById(R.id.tvParticipants);  // Finds and assigns the TextView for participants.
        TextView tvCity = view.findViewById(R.id.tvCity); // Display the city name  // Finds and assigns the TextView for city name.

        CarMeet temp = objects.get(pos);  // Retrieves the CarMeet object corresponding to the current position.

        tvTime.setText(temp.getTime()); // Set the time into the appropriate text view  // Sets the time text in the TextView.
        tvDate.setText(temp.getDate()); // Set the date into the appropriate text view  // Sets the date text in the TextView.
        tvParticipants.setText(Integer.toString(temp.getParticipants()));  // Sets the participants count in the TextView.

        // Get the city name asynchronously  // Initiates an asynchronous task to fetch city name based on coordinates.
        new ReverseGeocodingTask(tvCity).execute(temp.getLocation().getLatitude(), temp.getLocation().getLongitude());

        return view;  // Returns the populated view for the list item.
    }

    // AsyncTask to perform reverse geocoding asynchronously  // Inner class for background geocoding task.
    private class ReverseGeocodingTask extends AsyncTask<Float, Void, String> {
        private TextView textView;  // Declares a variable to store the TextView for displaying city name.

        public ReverseGeocodingTask(TextView textView) {
            this.textView = textView;  // Initializes the textView variable with the provided TextView.
        }

        @Override
        protected String doInBackground(Float... params) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());  // Creates a Geocoder instance with the default locale.
            float latitude = params[0];  // Retrieves latitude from the parameters.
            float longitude = params[1];  // Retrieves longitude from the parameters.

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);  // Retrieves addresses from geocoding.
                if (addresses != null && addresses.size() > 0) {  // Checks if addresses were found.
                    Address address = addresses.get(0);  // Retrieves the first address.
                    return address.getLocality(); // Get the city name  // Retrieves the city name from the address.
                }
            } catch (IOException e) {
                e.printStackTrace();  // Prints stack trace if an IOException occurs.
            }
            return null;  // Returns null if city name cannot be retrieved.
        }

        @Override
        protected void onPostExecute(String cityName) {
            if (cityName != null) {
                textView.setText(cityName);  // Sets the retrieved city name in the TextView.
            } else {
                textView.setText("Unknown"); // Handle cases where city name is not found  // Sets "Unknown" if city name couldn't be retrieved.
            }
        }
    }
}
