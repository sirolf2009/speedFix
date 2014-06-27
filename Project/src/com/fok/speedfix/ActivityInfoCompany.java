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
		setContentView(R.layout.info_engineer);
		
		GooglePlus.updateTitleBar(this);
		
		Map<String, String> engineer = MainActivity.instance.companyInfo;
		
		TextView itemView = (TextView) findViewById(R.id.companyDetailName);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_bedrijfsnaam"));
		}

		itemView = (TextView) findViewById(R.id.companyDetailStreet);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_straat")+" "+engineer.get("zak_huisnummer")+engineer.get("zak_toevoeging"));
		}
		
		itemView = (TextView) findViewById(R.id.companyDetailPostal);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_postcode")+" "+engineer.get("zak_plaats"));
		}

		itemView = (TextView) findViewById(R.id.companyDetailTelephone);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_telefoon"));
		}
		itemView = (TextView) findViewById(R.id.companyDetailMobile);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_mobiel"));
		}
		itemView = (TextView) findViewById(R.id.companyDetailEmail);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_email"));
		}
		itemView = (TextView) findViewById(R.id.companyDetailKVK);
		if (itemView != null) {
			itemView.setText(engineer.get("zak_kvk"));
		}
	}
}
