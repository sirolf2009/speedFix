package com.fok.speedfix;

import java.util.Set;

import com.fok.speedfix.MainActivity;
import com.fok.speedfix.R;
import com.fok.speedfix.util.Storage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class ActivityCompanyList extends Activity {

	public ActivityCompanyList() {
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void createNotification(String companyName, Context context) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle(companyName)
		        .setContentText("wants to fix your device!")
		        .setAutoCancel(true);
		
		Intent resultIntent = new Intent(context, MainActivity.class); //TODO resultactivity
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	public static void notifyIfNewEngineer(Context context, Set<String> engineers) {
		Set<String> saved = Storage.readEngineers(context);
		for(String string : engineers) {
			if(!saved.contains(string)) {
				createNotification(string, context);
				saved.add(string);
			}
		}
		Storage.saveEngineers(saved, context);
	}
}
