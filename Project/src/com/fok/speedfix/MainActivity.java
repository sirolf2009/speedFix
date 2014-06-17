package com.fok.speedfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fok.speedfix.services.ServiceDatabase;
import com.fok.speedfix.util.GooglePlus;

public class MainActivity extends Activity {

	public static MainActivity instance;
	public static GooglePlus plus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		findViewById(R.id.imageView1).postDelayed(new Runnable() {
			@Override
			public void run() {
				instance.setContentView(R.layout.main);
			}
		}, 2000);
		
		plus = new GooglePlus();
		plus.signInWithGplus(instance);
		
		startService(new Intent(this, ServiceDatabase.class));
	}
	
	public void repairPhone(View view) {
		startActivity(new Intent(this, ActivityRepairPhone.class));
	}

	public void openMap(View view) {
		Intent intent = new Intent(this, ActivityMap.class);
		startActivity(intent);
	}
	
	public void onPhoneInfoClicked(View view) {
		startActivity(new Intent(this, ActivityPhoneInfo.class));
	}

	
}