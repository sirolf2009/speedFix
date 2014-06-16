package com.fok.speedfix;

import com.fok.speedfix.util.GooglePlus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
	}

	public void openMap(View view) {
		Intent intent = new Intent(this, ActivityMap.class);
		startActivity(intent);
	}
	
	public void onPhoneInfoClicked(View view) {
		startActivity(new Intent(this, ActivityPhoneInfo.class));
	}

	
}