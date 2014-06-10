package com.fok.speedfix.activities;

import com.fok.speedfix.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class ActivityNewPhone extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accept_reparation);
	}
	
	public static void createNotification(Context context) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle("A phone has been damaged")
		        .setContentText("tap for details")
		        .setAutoCancel(true);
		
		Intent resultIntent = new Intent(context, ActivityNewPhone.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ActivityNewPhone.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	public static void notifyIfNewPhone(Context context) {
		//TODO
		//if has new phone
		createNotification(context);
	}

}
