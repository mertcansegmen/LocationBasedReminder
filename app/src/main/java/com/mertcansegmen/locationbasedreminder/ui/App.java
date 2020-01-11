package com.mertcansegmen.locationbasedreminder.ui;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.mertcansegmen.locationbasedreminder.R;

public class App extends Application {
    public static final String CHANNEL_ID = "com.mertcansegmen.locationbasedreminder.CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();
        
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.reminders),
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}