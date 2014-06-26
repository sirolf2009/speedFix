package com.fok.speedfix.util;

import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Storage {

	private static final String engineersFile = "com.fok.speedfix.engineers";
	private static final String engineersKey = "engineers";

	private static final String phonesFile = "com.fok.speedfix.phones";
	private static final String phonesKey = "phones";
	
	private static final String phoneIDFile = "com.fok.speedfix.phoneID";
	private static final String phoneIDKey = "phoneID";

	public static void saveEngineers(Set<String> engineers, Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(engineersFile, Context.MODE_PRIVATE);
		Editor edit = sharedPref.edit();
		edit.clear();
		edit.putStringSet(engineersKey, engineers);
		if(!edit.commit()) {
			Log.e("Could not save "+engineers+" to "+engineersFile+" with key "+engineersKey);
		}
	}

	public static Set<String> readEngineers(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(engineersFile, Context.MODE_PRIVATE);
		return sharedPref.getStringSet(engineersKey, null);
	}
	
	public static void savePhones(Set<String> phones, Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(phonesFile, Context.MODE_PRIVATE);
		Editor edit = sharedPref.edit();
		edit.clear();
		edit.putStringSet(phonesKey, phones);
		if(!edit.commit()) {
			Log.e("Could not save "+phones+" to "+phonesFile+" with key "+phonesKey);
		}
	}

	public static Set<String> readPhones(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(phonesFile, Context.MODE_PRIVATE);
		return sharedPref.getStringSet(phonesKey, null);
	}
	
	public static void savePhoneID(String ID, Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(phoneIDFile, Context.MODE_PRIVATE);
		Editor edit = sharedPref.edit();
		edit.clear();
		edit.putString(phoneIDKey, ID);
		if(!edit.commit()) {
			Log.e("Could not save "+ID+" to "+phoneIDFile+" with key "+phoneIDKey);
		}
	}
	
	public static String readPhoneID(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(phoneIDFile, Context.MODE_PRIVATE);
		return sharedPref.getString(phoneIDKey, null);
	}
}
