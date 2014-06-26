package com.fok.speedfix;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
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

        TabSpec company = tabHost.newTabSpec("Bedrijf");        
        company.setIndicator("Bedrijf");
        company.setContent(new Intent(this, ActivityInfoCompany.class));
        
        tabHost.addTab(photospec);
        tabHost.addTab(songspec);
        if(!MainActivity.instance.isNormalUser) {
        	tabHost.addTab(company);
        }
    }

}
