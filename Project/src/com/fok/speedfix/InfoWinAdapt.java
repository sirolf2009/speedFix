package com.fok.speedfix;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

class InfoWinAdapt implements InfoWindowAdapter {
	LayoutInflater inflater=null;

	InfoWinAdapt(LayoutInflater inflater) {
		this.inflater=inflater;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return(null);
	}

	@Override
	public View getInfoContents(Marker marker) {
		View popup=inflater.inflate(R.layout.custominfowindow, null);

		TextView tv=(TextView)popup.findViewById(R.id.maptitle);

		tv.setText(marker.getTitle());
		tv=(TextView)popup.findViewById(R.id.mapsnippet);
		tv.setText(marker.getSnippet());

		return(popup);
	}
	
	public void showDetail(View v) {
		
	}
}