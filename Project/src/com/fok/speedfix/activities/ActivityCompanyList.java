package com.fok.speedfix.activities;

import com.fok.speedfix.MainActivity;
import com.fok.speedfix.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class ActivityCompanyList extends Activity {

	public ActivityCompanyList() {
	}
	
	public static void createNotification(String companyName) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(MainActivity.instance)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle(companyName)
		        .setContentText("wants to fix your device!")
		        .setAutoCancel(true);
		
		Intent resultIntent = new Intent(MainActivity.instance, MainActivity.class); //TODO resultactivity
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.instance);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) MainActivity.instance.getSystemService(MainActivity.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
}
