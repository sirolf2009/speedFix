package com.fok.speedfix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.PhoneListAdapter;
import com.fok.speedfix.util.Storage;

public class ActivityPhoneList extends Activity {

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.phone_list);
		GooglePlus.updateTitleBar(this);
		
		Set<String> phonesRaw = Storage.readPhones(this);
		List<Map<String, String>> phones = new ArrayList<Map<String, String>>();
		if(phonesRaw == null) {
			phonesRaw = new HashSet<String>();
		}
		for(String phone : phonesRaw) {
			phones.add(Helper.decipherPhone(phone));
		}
		List<String> brands = new ArrayList<String>();
		for(Map<String, String> phone : phones) {
			brands.add(phone.get("device_brand"));
		}
		((ListView)findViewById(R.id.listViewPhones)).setAdapter(new PhoneListAdapter(this, R.id.textView1, brands, phones));
		((ListView)findViewById(R.id.listViewPhones)).setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
			}
		});
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
	
	public static void notifyIfNewPhone(Context context, Iterable<String> phones) {
		Set<String> saved = Storage.readPhones(context);
		for(String string : phones) {
			if(saved == null) {
				Map<String, String> phoneInfo = Helper.decipherPhone(string);
				saved = new HashSet<String>();
				saved.add(string);
				Storage.savePhones(saved, context);
				createNotification(phoneInfo.get("device_brand") + " " + phoneInfo.get("device_component"), context);
			} else if(!saved.contains(string)) {
				Map<String, String> phoneInfo = Helper.decipherPhone(string);
				saved.add(string);
				Storage.savePhones(saved, context);
				createNotification(phoneInfo.get("device_brand") + " " + phoneInfo.get("device_component"), context);
			}
		}
	}
	
}
