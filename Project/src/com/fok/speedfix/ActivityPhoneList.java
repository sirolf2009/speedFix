package com.fok.speedfix;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.PhoneListAdapter;
import com.fok.speedfix.util.Storage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ListView;

public class ActivityPhoneList extends Activity {

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.phone_list);
		GooglePlus.updateTitleBar(this);
		String[] array = new String[] {
				"Samsung" ,
				"Samsung" ,
				"Vodafone"
		};
		((ListView)findViewById(R.id.listViewPhones)).setAdapter(new PhoneListAdapter(this, R.id.textView1, Arrays.asList(array)));
	}
	
	public void test(View v) {
		View info = v.findViewById(R.id.moar_info);
		info.setVisibility(info.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void createNotification(String phoneIssue, Context context) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle(phoneIssue)
		        .setContentText("Tap for details")
		        .setAutoCancel(true);
		
		Intent resultIntent = new Intent(context, ActivityPhoneList.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ActivityPhoneList.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	public static void notifyIfNewPhone(Context context, Set<String> phones) {
		Set<String> saved = Storage.readPhones(context);
		for(String string : phones) {
			if(!saved.contains(string)) {
				Map<String, String> phoneInfo = Helper.decipherPhone(string);
				createNotification(phoneInfo.get("brand") + " " + phoneInfo.get("component"), context);
				saved.add(string);
			}
		}
		Storage.savePhones(saved, context);
	}
	
}
