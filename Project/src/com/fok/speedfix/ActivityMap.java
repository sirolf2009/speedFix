package com.fok.speedfix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.Storage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

public class ActivityMap extends ActivityBaseMap {

    private Marker yourLocation;
    private ListView lv;

    private final String COUNTRY = "Nederland";
    private String[] locations = {"rotterdam", "rotterdam", "rotterdam", "rotterdam", "rotterdamn", "rotterdam", "rotterdam", "rotterdam", "amsterdam", "Barendrecht"};
    private String[] bizNames = {"PhoneRepair", "PhoneRepairPro", "PhoneRepairSuperPro", "PhoneRepairDoublePro", "PhoneRepairer", "PhoneRepairBuddy", "PhoneRepairBro", "PhoneRepairDad", "PhoneRepairGod", "PhoneRepairKing"};
    private String[] description = {"Apollostraat 11", "Westzeedijk 12", "Vierambachtstraat 13", "Zwartjanstraat 14", "Bloemstraat 15", "Straatweg 16", "Kleiweg 17", "Gordelweg 18", "Kalverstraat 19", "Henry Dunantlaan 20"};
    private double[] ranges = new double[locations.length];
    private Marker[] locMarkers = new Marker[locations.length];
    private List<String> selectedCompanies = new ArrayList<String>();
    private List<LatLng> companyLocations = new ArrayList<LatLng>();

    @Override
    public void onCreate(Bundle saved) {
    	Intent intent = getIntent();
    	if(intent.hasExtra("locations")) {
    		locations = intent.getStringArrayExtra("locations");
    		bizNames = intent.getStringArrayExtra("bizNames");
    		description = intent.getStringArrayExtra("description");
    		ranges = new double[locations.length];
    	    locMarkers = new Marker[locations.length];
    	} else {
    		List<String> names = new ArrayList<String>();
    		List<String> streets = new ArrayList<String>();
    		List<String> locations = new ArrayList<String>();
    		Set<String> engineers = Storage.readEngineers(this);
    		if(engineers == null) {
    			return;
    		}
    		for(String engineer : engineers) {
    			Map<String, String> engineerData = Helper.decipherEngineer(engineer);
    			names.add(engineerData.get("name"));
    			streets.add(engineerData.get("street"));
    			locations.add(engineerData.get("location"));
    		}
    		bizNames = (String[]) names.toArray();
    		description = (String[]) names.toArray();
    		this.locations = (String[]) locations.toArray();
    	}
    	super.onCreate(saved);
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.distance;
    }

    @Override
    protected void startMap() {
    	GooglePlus.updateTitleBar(this);
        Geocoder geocoder = new Geocoder(this);

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.9166667, 4.5), 10));
        getMap().setInfoWindowAdapter(new InfoWinAdapt(getLayoutInflater()));
        
        double loc[] = getlocation();

        if (loc != null ) {
        	yourLocation = getMap().addMarker(new MarkerOptions().position(new LatLng(loc[0], loc[1])).title("Your Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        	getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc[0], loc[1]), 13));
    	}
        
        int distanceToLookFor = 5001;
        boolean hitFound = false;
        while (!hitFound) {
	        for(int i = 0; i < locations.length; i++) {	
	        	LatLng targetXY = getLocation(description[i] + " " + locations[i] + " " + COUNTRY, geocoder);
	        	ranges[i] = getLatLngDistance(yourLocation.getPosition(), targetXY);
	        	if(ranges[i] < distanceToLookFor) {
	        		locMarkers[i] = getMap().addMarker(new MarkerOptions().position(targetXY).title(bizNames[i]).snippet(description[i]));
	        		selectedCompanies.add(bizNames[i]);
	        		companyLocations.add(targetXY);
	        		hitFound = true;
	        	}
	        }
	        distanceToLookFor = distanceToLookFor + 5000;
	        if (distanceToLookFor > 25000) {
	        	break;
	        }
	        lv = (ListView) findViewById(R.id.listViewMap);
	        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
	                 this, 
	                 android.R.layout.simple_list_item_1,
	                 selectedCompanies );
	         lv.setAdapter(arrayAdapter); 
        }
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
              String selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
              int location = 0;
              for (String s : selectedCompanies) {
            	  if (s == selectedFromList) {
            		  getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(companyLocations.get(location), 13));
            	  }
            	  location++;
              }
              location = 0;
            }                 
      });
    }
    
    private double getLatLngDistance(LatLng posOne, LatLng posTwo) {
    	double distance = SphericalUtil.computeDistanceBetween(posOne, posTwo);
    	return distance;
    }
    
    public double[] getlocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;
        for (int i = 0; i < providers.size(); i++) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }
        double[] gps = new double[2];

        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }
    
    private LatLng getLocation(String s, Geocoder geo) {
    	List<Address> addresses = null;
        double latitude;
        double longitude;
        
        while(addresses==null){
            try {
                addresses = geo.getFromLocationName(s, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Address address = addresses.get(0);
        latitude = address.getLatitude();
        longitude = address.getLongitude();
        LatLng City = new LatLng(latitude, longitude);
        return City;
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void createNotification(String companyName, Context context) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.logo)
		        .setContentTitle(companyName)
		        .setContentText("wants to fix your device!")
		        .setAutoCancel(true);
		
		Intent resultIntent = new Intent(context, ActivityMap.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ActivityMap.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	public static void notifyIfNewEngineer(Context context, Set<String> engineers) {
		Set<String> saved = Storage.readEngineers(context);
		if(saved == null) {
			return;
		}
		for(String string : engineers) {
			if(!saved.contains(string)) {
				createNotification(string, context);
				saved.add(string);
			}
		}
		Storage.saveEngineers(saved, context);
	}

}
