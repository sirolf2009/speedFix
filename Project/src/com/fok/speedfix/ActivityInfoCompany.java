package com.fok.speedfix;

import java.util.Map;

import com.fok.speedfix.util.GooglePlus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityInfoCompany extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_company);
		
		GooglePlus.updateTitleBar(this);
		
		Map<String, String> company = MainActivity.instance.companyInfo;
		
		TextView view = (TextView) findViewById(R.id.companyName);
		if(view != null) {
			view.setText(company.get("zak_bedrijfsnaam"));
		}
		
		view = (TextView) findViewById(R.id.companyStreet);
		if(view != null) {
			view.setText(company.get("zak_straat")+" "+company.get("zak_huisnummer")+" "+company.get("zak_toevoeging"));
		}
		
		view = (TextView) findViewById(R.id.companyEmail);
		if(view != null) {
			view.setText(company.get("zak_email"));
		}
		
		view = (TextView) findViewById(R.id.companyTelephone);
		if(view != null) {
			view.setText(company.get("zak_telefoon"));
		}
	}
}
