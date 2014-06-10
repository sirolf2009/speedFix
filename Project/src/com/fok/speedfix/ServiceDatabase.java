package com.fok.speedfix;

import com.fok.speedfix.activities.ActivityCompanyList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceDatabase extends Service {

	public ServiceDatabase() {
	}
	
	public void onCreate() {
    }
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		while(true) {
			retrieveAvailabeEngineersFromDatabase();
			retrieveBrokenPhonesFromDatabase();
			try {
				Thread.sleep(1000*60); //60 seconds
			} catch (InterruptedException e) {} //ne'er gonna happen
		}
    }
	
	public void retrieveBrokenPhonesFromDatabase() {
		//Collect
	}
	
	public void retrieveAvailabeEngineersFromDatabase() {
		//Collect
		ActivityCompanyList.notifyIfNewEngineer(getApplicationContext());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
