package com.fok.speedfix;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActivityVibration extends Activity {

	public Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onClickVibratorLong(View v) {
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// Op basis van Miliseconden
		vibrator.vibrate(3000);
	}
	
	public void onClickVibrator(View v) {
		// methode Pattern Vibration
		long pattern[] = {0, 300, 200, 300, 500};
		
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		// Roep pattern op
		vibrator.vibrate(pattern, 0);
	}
	
	
	public void onClickCancel(View v) {
		vibrator.cancel();
	}
	
}
