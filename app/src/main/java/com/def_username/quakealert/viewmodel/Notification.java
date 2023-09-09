package com.def_username.quakealert.viewmodel;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		createNotificationChannel();
	}

	private void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			String CHANNEL_ID = "1";
			String CHANNEL_NAME = "Currently occurring earthquakes";
			NotificationChannel channel = new NotificationChannel(
					CHANNEL_ID,
					CHANNEL_NAME,
					NotificationManager.IMPORTANCE_HIGH
			);
			channel.setDescription("Send notification on currently occurring earthquakes");
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}

}
