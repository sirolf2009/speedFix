package com.fok.speedfix.util;

import java.util.Set;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Storage {

	private static final String engineersFile = "com.fok.speedfix.engineers";
	private static final String engineersKey = "engineers";

	private static final String phonesFile = "com.fok.speedfix.phones";
	private static final String phonesKey = "phones";

	public static void saveEngineers(Set<String> engineers, Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(engineersFile, Context.MODE_PRIVATE);
		sharedPref.edit().clear();
		sharedPref.edit().putStringSet(engineersKey, engineers);
		if(!sharedPref.edit().commit()) {
			Log.e("Could not save "+engineers+" to "+engineersFile+" with key "+engineersKey);
		}
	}

	public static Set<String> readEngineers(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(engineersFile, Context.MODE_PRIVATE);
		return sharedPref.getStringSet(engineersKey, null);
	}
	
	public static void savePhones(Set<String> engineers, Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(phonesFile, Context.MODE_PRIVATE);
		sharedPref.edit().clear();
		sharedPref.edit().putStringSet(phonesKey, engineers);
		if(!sharedPref.edit().commit()) {
			Log.e("Could not save "+engineers+" to "+phonesFile+" with key "+phonesKey);
		}
	}

	public static Set<String> readPhones(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(phonesFile, Context.MODE_PRIVATE);
		return sharedPref.getStringSet(phonesKey, null);
	}
}
