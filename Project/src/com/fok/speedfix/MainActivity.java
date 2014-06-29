package com.fok.speedfix;

import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.fok.speedfix.services.ServiceDatabase;
import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.IJsonResponse;
import com.fok.speedfix.util.JSONDownloaderHandler;
import com.fok.speedfix.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {

	public static MainActivity instance;
	public static GooglePlus plus;

	public boolean isNormalUser = true;
	public Map<String, String> companyInfo;
	public Map<String, String> userInfo;

	public boolean isMenuVisible;
	public boolean isConnected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		if(status != ConnectionResult.SUCCESS) {
			AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
			helpBuilder.setTitle("Couldn't find Google Play Services");
			helpBuilder.setMessage("Please install Google Play Services");
			helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			AlertDialog helpDialog = helpBuilder.create();
			helpDialog.show();
		} else {

			findViewById(R.id.imageview1).postDelayed(new Runnable() {
				@Override
				public void run() {
					if(!isConnected) {
						AlertDialog.Builder helpBuilder = new AlertDialog.Builder(instance);
						helpBuilder.setTitle("Timout");
						helpBuilder.setMessage("Timeout during Google+ Sign In");
						helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						});
						AlertDialog helpDialog = helpBuilder.create();
						helpDialog.show();
					} else {
						instance.setContentView(R.layout.main);
						isMenuVisible = true;
					}
				}
			}, 5000);

			plus = new GooglePlus();
			plus.signInWithGplus(instance);

			startService(new Intent(this, ServiceDatabase.class));
		}
	}

	public void switchUser(final boolean isNormalUser) {
		this.isNormalUser = isNormalUser;
		Log.i("setting user to "+isNormalUser);
		if(!isMenuVisible) {
			findViewById(R.id.imageview1).postDelayed(new Runnable() {
				@Override
				public void run() {
					switchUser(isNormalUser);
				}
			}, 100);
			return;
		}
		TableLayout user = (TableLayout) findViewById(R.id.optionsUsers);
		TableLayout engie = (TableLayout) findViewById(R.id.optionsEngies);
		ImageView img = (ImageView) findViewById(R.id.imageView2);
		ImageView imgEngie = (ImageView) findViewById(R.id.backgroundEngineer);
		user.setVisibility(isNormalUser ? View.VISIBLE : View.GONE);
		img.setVisibility(isNormalUser ? View.VISIBLE : View.GONE);
		engie.setVisibility(isNormalUser ? View.GONE : View.VISIBLE);
		imgEngie.setVisibility(isNormalUser ? View.GONE : View.VISIBLE);
		findViewById(R.id.relativeLayout1).invalidate();
	}

	public void signOut(View v) {
		plus.signOutFromGplus();
		plus.signInWithGplus(instance);
	}

	public void googleConnect() {
		isConnected = true;
		new GetCompanyInfo(MainActivity.instance).execute();
	}

	public void repairPhone(View view) {
		startActivity(new Intent(this, ActivityRepairPhone.class));
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void openMap(View view) {
		Intent intent = new Intent(this, ActivityMap.class);
		ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
				0, view.getWidth(), view.getHeight());
		startActivity(intent, options.toBundle());
	}

	public void onPhoneInfoClicked(View view) {
		startActivity(new Intent(this, ActivityInfo.class));
	}

	public void onPhoneListClicked(View v) {
		startActivity(new Intent(this, ActivityPhoneList.class));
	}

	public void onRegisterEngiePressed(View v) {
		startActivity(new Intent(this, ActivityRegisterEngie.class));
	}

	public static class GetUserType extends JSONDownloaderHandler implements IJsonResponse {

		static final String[] cols = new String[] {
			"zak_id"
		};

		private List<Map<String, String>> businesses;
		private List<Map<String, String>> users;
		private MainActivity activity;

		public GetUserType(MainActivity activity) {
			super("http://www.speedFix.eu/android/get_all_bizz.php", "zakelijk", cols);
			new Helper.GetAllUsers(this).execute();
			this.activity = activity;
		}

		@Override
		public void data(List<Map<String, String>> data) {
			businesses = data;
			checkForCompany(); 
		}

		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		public void checkForCompany() {
			if(businesses == null || users == null) {
				return;
			}
			for(Map<String, String> bizz : businesses) {
				for(Map<String, String> user : users) {
					Log.i(user.get("zak_id")+" == "+bizz.get("zak_id"));
					if(!user.get("zak_id").isEmpty() && user.get("zak_id").equals(bizz.get("zak_id")) && user.get("user_id_google").equals(MainActivity.plus.getUser().getId())) {
						activity.switchUser(false);
						Log.i("setting user info");
						activity.userInfo = user;
						return;
					} else if(user.get("user_id_google").equals(MainActivity.plus.getUser().getId())) {
						activity.userInfo = user;
					}
				}
			}
			Log.i("couldn't set user info");
			activity.switchUser(true);
		}

		@Override
		public void getResponse(List<Map<String, String>> data, String tag) {
			users = data;
			checkForCompany();
		}
	}

	public static class GetCompanyInfo extends JSONDownloaderHandler implements IJsonResponse {

		public static final String[] cols = new String[] {
			"zak_id",
			"zak_bedrijfsnaam",
			"zak_kvk",
			"zak_email",
			"zak_naam",
			"zak_achternaam",
			"zak_geslacht",
			"zak_postcode",
			"zak_huisnummer",
			"zak_toevoeging",
			"zak_straat",
			"zak_plaats",
			"zak_provincie",
			"zak_land",
			"zak_telefoon",
			"zak_mobiel"
		};

		private MainActivity mainActivity;
		public static final String tag = "zakelijk";
		private List<Map<String, String>> users;
		private List<Map<String, String>> businesses;

		public GetCompanyInfo(MainActivity activity) {
			super("http://www.speedFix.eu/android/get_all_bizz.php", tag, cols);
			new Helper.GetAllUsers(this).execute();
			this.mainActivity = activity;
		}

		@Override
		public void data(List<Map<String, String>> data) {
			businesses = data;
			findBusiness();
		}

		@Override
		public void getResponse(List<Map<String, String>> data, String tag) {
			users = data;
			findBusiness();
		}

		public void findBusiness() {
			if(businesses == null || users == null) {
				return;
			}
			for(Map<String, String> user : users) {
				if(user.get("user_id_google").equals(MainActivity.plus.getUser().getId())) {
					mainActivity.userInfo = user;
					for(Map<String, String> bizz : businesses) {
						if(user.get("zak_id").equals(bizz.get("zak_id"))) {
							Log.i("user is doing bizzes");
							mainActivity.companyInfo = bizz;
							mainActivity.switchUser(false);
							return;
						}
					}
				}
			}
			mainActivity.switchUser(true);
		}
	}

}