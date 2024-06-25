package com.example.ultimatecm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class DataChangeService extends Service {
    public DataChangeService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    int notificationID = 1;
    int counter = 1;
    final String CHANNEL_ID = "UltimateCM";
    final String CHANNEL_NAME = "UltimateCM";
    final String CHANNEL_DESC = "MyApp ...";
    private String username;
    private String index;

    public void sendNotification(String title ,String txt)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(CHANNEL_DESC);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(txt);

        notificationManager.notify(notificationID, builder.build());
        notificationID++;
        counter++;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("USERNAME")) {
            username = intent.getStringExtra("USERNAME");
        }
        index = String.valueOf(DataManager.getCurrentIndex(username));

        DatabaseReference carMeetsRef = DBManager.getMainRoot().child(index).child("othersCarMeets");
        if (carMeetsRef != null) {
            carMeetsRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    // Handle child added event
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    // Handle child changed event
                    sendNotification("CarMeet updated!", "A CarMeet that you signed in to has been updated!");
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    // Handle child removed event
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    // Handle child moved event
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        } else {
            // Handle case where carMeetsRef is null or invalid
        }

        return START_STICKY;
    }



}