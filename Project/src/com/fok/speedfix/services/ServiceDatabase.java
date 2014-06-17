package com.fok.speedfix.services;

import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.fok.speedfix.ActivityMap;
import com.fok.speedfix.util.Log;

public class ServiceDatabase extends Service {
	
	public static ServiceDatabase instance;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		instance = this;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					retrieveAvailabeEngineersFromDatabase();
					retrieveBrokenPhonesFromDatabase();
					try {
						Thread.sleep(1000*60); //60 seconds
					} catch (InterruptedException e) {} //ne'er gonna happen
				}
			}
		}).start();
		return START_STICKY;
    }

	public void retrieveBrokenPhonesFromDatabase() {
		//Collect
	}

	public void retrieveAvailabeEngineersFromDatabase() {
		//Collect
		ActivityMap.notifyIfNewEngineer(getApplicationContext(), null);
	}

	public void sendPhone(Map<String, String> info) {
		// TODO fill database
		Log.i(info);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}