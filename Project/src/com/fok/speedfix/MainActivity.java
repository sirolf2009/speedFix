package com.fok.speedfix;

import com.fok.speedfix.activities.ActivityCompanyList;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	public static MainActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		startService(new Intent(this, ServiceDatabase.class));
	}
}
