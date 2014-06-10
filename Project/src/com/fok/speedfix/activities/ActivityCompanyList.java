package com.fok.speedfix.activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.fok.speedfix.R;

public class ActivityCompanyList extends Activity {

	public ActivityCompanyList() {
	}
	
	public static void createNotification(String companyName, Context context) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle(companyName)
		        .setContentText("wants to fix your device!")
		        .setAutoCancel(true);
		
		Intent resultIntent = new Intent(context, ActivityCompanyList.class); //TODO resultactivity
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ActivityCompanyList.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	public static void notifyIfNewEngineer(Context context) {
		//TODO
		//if has new engi
		createNotification("HI! I'm Dave! I'm new :3", context);
	}
}
