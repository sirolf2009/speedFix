package com.fok.speedfix;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

public class ActivityMap extends BaseMapActivity {
    private Marker yourLocation;
    private String[] locations = {"Apollostraat rotterdam nederland", "Westzeedijk rotterdam nederland", "vierambachtstraat rotterdam nederland", "Zwartjanstraat rotterdam nederland", "Bloemstraat rotterdamn nederland", "Straatweg rotterdam nederland", "Kleiweg rotterdam nederland", "Gordelweg rotterdam nederland", "Kalverstraat amsterdam nederland", "Henry Dunantlaan Barendrecht Nederland"};
    private String[] bizNames = {"PhoneRepair", "PhoneRepairPro", "PhoneRepairSuperPro", "PhoneRepairDoublePro", "PhoneRepairer", "PhoneRepairBuddy", "PhoneRepairBro", "PhoneRepairDad", "PhoneRepairGod", "PhoneRepairKing"};
    private String[] description = {"Apollostraat 11", "Westzeedijk 12", "Vierambachtstraat 13", "Zwartjanstraat 14", "Bloemstraat 15", "Straatweg 16", "Kleiweg 17", "Gordelweg 18", "Kalverstraat 19", "Henry Dunantlaan 20"};
    private double[] ranges = new double[locations.length];
    private Marker[] locMarkers = new Marker[locations.length];

    @Override
    protected int getLayoutId() {
        return R.layout.distance;
    }

    @Override
    protected void startMap() {
        Geocoder geocoder = new Geocoder(this);

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.9166667, 4.5), 10));
        
        double loc[] = getlocation();

        if (loc != null ) {
        	yourLocation = getMap().addMarker(new MarkerOptions().position(new LatLng(loc[0], loc[1])).title("Your Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        	getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc[0], loc[1]), 13));
    	}
        
        int distanceToLookFor = 5001;
        boolean hitFound = false;
        while (!hitFound) {
	        for(int i = 1; i < locations.length; i++) {	
	        	LatLng targetXY = getLocation(locations[i], geocoder);
	        	ranges[i] = getLatLngDistance(yourLocation.getPosition(), targetXY);
	        	if(ranges[i] < distanceToLookFor) {
	        		locMarkers[i] = getMap().addMarker(new MarkerOptions().position(targetXY).title(bizNames[i]).snippet(description[i]));
	        		hitFound = true;
	        	}
	        }
	        distanceToLookFor = distanceToLookFor + 5000;
	        if (distanceToLookFor > 25000) {
	        	break;
	        }
        }
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
}
