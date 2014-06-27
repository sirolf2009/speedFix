package com.fok.speedfix;

import java.util.Map;

import com.fok.speedfix.util.Helper;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ActivityInfo extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);

		TabHost tabHost = getTabHost();

		TabSpec photospec = tabHost.newTabSpec("Gebruiker");
		photospec.setIndicator("Gebruiker");
		photospec.setContent(new Intent(this, ActivityInfoUser.class));

		TabSpec songspec = tabHost.newTabSpec("Telefoon");        
		songspec.setIndicator("Telefoon");
		songspec.setContent(new Intent(this, ActivityInfoPhone.class));


		tabHost.addTab(photospec);
		tabHost.addTab(songspec);
		if(!MainActivity.instance.isNormalUser) {
			TabSpec company = tabHost.newTabSpec("Bedrijf");        
			company.setIndicator("Bedrijf");
			Intent intent = new Intent(this, ActivityCompanyDetail.class);
			Map<String, String> companyDetails = MainActivity.instance.companyInfo;
			intent.putExtra("company", Helper.encipherEngineer(companyDetails));
			company.setContent(intent);
			tabHost.addTab(company);
		}
	}

}
