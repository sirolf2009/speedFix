package com.fok.speedfix;

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
        
        TabSpec photospec = tabHost.newTabSpec("User");
        photospec.setIndicator("User");
        photospec.setContent(new Intent(this, ActivityInfoUser.class));

        TabSpec songspec = tabHost.newTabSpec("Phone");        
        songspec.setIndicator("Phone");
        songspec.setContent(new Intent(this, ActivityInfoPhone.class));

        TabSpec company = tabHost.newTabSpec("Company");        
        company.setIndicator("Company");
        company.setContent(new Intent(this, ActivityInfoCompany.class));
        
        tabHost.addTab(photospec);
        tabHost.addTab(songspec);
        if(!MainActivity.instance.isNormalUser) {
        	tabHost.addTab(company);
        }
    }

}
