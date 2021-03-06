package com.fok.speedfix.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;

import com.fok.speedfix.ActivityMap;
import com.fok.speedfix.ActivityPhoneList;
import com.fok.speedfix.MainActivity;
import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.JSONDownloaderHandler;
import com.fok.speedfix.util.JSONUploaderHandler;
import com.fok.speedfix.util.Log;
import com.fok.speedfix.util.Storage;

public class ServiceDatabase extends Service {

	public static ServiceDatabase instance;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		instance = this;
		Log.i("starting service");
		new Thread(new Runnable() {
			@Override
			public synchronized void run() {
				if(android.os.Build.VERSION.SDK_INT >= 16) {
					while(true) {
						Looper.prepare();
						if(MainActivity.instance != null) {
							if(!MainActivity.instance.isNormalUser) {
								new PhoneListUpdate().execute();
							} else {
								new EngineerListUpdate().execute();
							}
						}
						try {
							Thread.sleep(1000); //10 mins
						} catch (InterruptedException e) {} //ne'er gonna happen
						Looper.loop();
					}
				}
			}
		}).start();
		return START_STICKY;
	}

	public void sendPhone(Map<String, String> info) {
		new AddBrokenPhone(info).execute();
		Storage.savePhoneID(info.get("device_id"), this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static class PhoneListUpdate extends JSONDownloaderHandler {

		static final String[] cols = new String[] {
			"device_id",
			"device_board",
			"device_brand",
			"device_device",
			"device_display",
			"device_hardware",
			"device_software",
			"device_manufacturer",
			"device_model",
			"device_product",
			"device_component",
			"device_description",
		};

		public PhoneListUpdate() {
			super("http://www.speedFix.eu/android/get_all_devices.php", "devices", cols);
		}

		@Override
		public void data(List<Map<String, String>> data) {
			if(data.size() == 0) {
				return;
			}
			List<String> phones = new ArrayList<String>();
			for(Map<String, String> row : data) {
				phones.add(Helper.encipherPhone(row));
			}
			ActivityPhoneList.notifyIfNewPhone(instance, phones);
		}

	}

	public static class EngineerListUpdate extends JSONDownloaderHandler {

		static final String[] cols = new String[] {
			"zak_id",
			"zak_bedrijfsnaam",
			"zak_kvk",
			"zak_email",
			"zak_naam",
			"zak_achternaam",
			"zak_geslacht",
			"zak_postcode",
			"zak_huisnummer",
			"zak_toevoeging",
			"zak_straat",
			"zak_plaats",
			"zak_provincie",
			"zak_land",
			"zak_telefoon",
			"zak_mobiel"
		};

		public EngineerListUpdate() {
			super("http://www.speedFix.eu/android/get_all_bizz.php", "zakelijk", cols);
		}

		@Override
		public void data(List<Map<String, String>> data) {
			if(data.size() == 0) {
				return;
			}
			List<String> engineers = new ArrayList<String>();
			for(Map<String, String> row : data) {
				engineers.add(Helper.encipherEngineer(row));
			}
			ActivityMap.notifyIfNewEngineer(instance, engineers);
		}
	}

	public static class AddBrokenPhone extends JSONUploaderHandler {

		public AddBrokenPhone(Map<String, String> rowData) {
			super("http://speedFix.eu/android/create_devices.php", rowData);
		}

	}

}