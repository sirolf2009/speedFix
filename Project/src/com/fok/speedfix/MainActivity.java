package com.fok.speedfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	public static MainActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		startActivity(new Intent(this, ActivityPhoneList.class));
	}

	public void openMap(View view) {
		Intent intent = new Intent(this, ActivityMap.class);
		startActivity(intent);
	}
}
