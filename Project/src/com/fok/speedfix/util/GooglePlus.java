package com.fok.speedfix.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.fok.speedfix.MainActivity;
import com.fok.speedfix.MainActivity.GetUserType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.PlusOptions;
import com.google.android.gms.plus.model.people.Person;

public class GooglePlus implements ConnectionCallbacks, OnConnectionFailedListener {
	
	private static final int RC_SIGN_IN = 0;

	// Google client to interact with Google API
	private static GoogleApiClient mGoogleApiClient;

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private static boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;
	
	private Activity activity;

	public void signInWithGplus(Activity activity) {
		this.activity = activity;
		setmGoogleApiClient(new GoogleApiClient.Builder(activity)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API, PlusOptions.builder().build())
		.addScope(Plus.SCOPE_PLUS_LOGIN).build());
		if (!getmGoogleApiClient().isConnecting()) {
			mSignInClicked = true;
			getmGoogleApiClient().connect();
		}
	}

	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(activity, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				getmGoogleApiClient().connect();
			}
		}
	}


	/**
	 * Fetching user's information name, email, profile pic
	 * */
	public void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(getmGoogleApiClient()) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(getmGoogleApiClient());
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(getmGoogleApiClient());
				

				Log.e("Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Background Async task to load user profile picture from url
	 * */
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


	/**
	 * Sign-out from google
	 * */
	public void signOutFromGplus() {
		if (getmGoogleApiClient().isConnected()) {
			Plus.AccountApi.clearDefaultAccount(getmGoogleApiClient());
			getmGoogleApiClient().disconnect();
			getmGoogleApiClient().connect();
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		activity.setTitle(activity.getTitle()+" - "+getUser().getDisplayName());
		new CreateGoogleUser(getUser()).execute();
		MainActivity.instance.googleConnect();
	}
	
	public Person getUser() {
		return Plus.PeopleApi.getCurrentPerson(getmGoogleApiClient());
	}

	@Override
	public void onConnectionSuspended(int cause) {
		getmGoogleApiClient().connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity, 0).show();
			return;
		}
		if (!mIntentInProgress) {
			mConnectionResult = result;
			if (mSignInClicked) {
				resolveSignInError();
			}
		}
	}
	
	public static void updateTitleBar(Activity activity) {
		if (Plus.PeopleApi.getCurrentPerson(getmGoogleApiClient()) != null) {
			activity.setTitle(activity.getTitle()+" - "+MainActivity.plus.getUser().getDisplayName());
		}
	}
	
	public static GoogleApiClient getmGoogleApiClient() {
		return mGoogleApiClient;
	}

	public static void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
		GooglePlus.mGoogleApiClient = mGoogleApiClient;
	}

	public static class CreateGoogleUser extends JSONUploaderHandler {

		public CreateGoogleUser(Map<String, String> rowData) {
			super("http://www.speedFix.eu/android/create_users.php", rowData);
		}
		
		public CreateGoogleUser(Person plus) {
			this(createMapFromPlus(plus));
		}

		private static Map<String, String> createMapFromPlus(Person plus) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("user_id_google", plus.getId());
			map.put("user_username", plus.getDisplayName());
			map.put("user_name", plus.getName().getGivenName());
			map.put("user_surname", plus.getName().getFamilyName());
			map.put("user_email", Plus.AccountApi.getAccountName(getmGoogleApiClient()));
			map.put("user_gender", plus.getGender()+"");
			map.put("user_language", plus.getLanguage());
			return map;
		}
		
		@Override
		public void onSucces() {
			Log.i("succes");
		}
		
		@Override
		public void onFailure(String error) {
			Log.e("failure");
			Log.e(error);
		}
		
	}

}
