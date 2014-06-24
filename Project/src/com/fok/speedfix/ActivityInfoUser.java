package com.fok.speedfix;

import java.io.InputStream;

import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.Log;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityInfoUser extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_user);
		
		GooglePlus.updateTitleBar(this);
		
		new LoadProfileImage((ImageView) findViewById(R.id.imageAvatar)).execute(MainActivity.plus.getUser().getImage().getUrl());
		
		TextView view = (TextView) findViewById(R.id.companyName);
		if(view != null) {
			view.setText(MainActivity.plus.getUser().getDisplayName());
		}
		
		view = (TextView) findViewById(R.id.url);
		if(view != null) {
			view.setText(MainActivity.plus.getUser().getUrl());
		}
		
		view = (TextView) findViewById(R.id.gender);
		if(view != null) {
			view.setText(getStringFromGender(MainActivity.plus.getUser().getGender()));
		}
		
		view = (TextView) findViewById(R.id.location);
		if(view != null) {
			view.setText(MainActivity.plus.getUser().getCurrentLocation());
		}
		
		view = (TextView) findViewById(R.id.email);
		if(view != null) {
			view.setText(Plus.AccountApi.getAccountName(GooglePlus.getmGoogleApiClient()));
		}
	}
	
	public String getStringFromGender(int gender) {
		if(gender == Person.Gender.MALE) {
			return "male";
		} else if(gender == Person.Gender.FEMALE) {
			return "female";
		}
		return "other";
	}
	
	public class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadProfileImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e(e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
}
