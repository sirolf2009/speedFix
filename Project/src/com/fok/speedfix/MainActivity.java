package com.fok.speedfix;

import java.util.List;
import java.util.Map;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import com.fok.speedfix.services.ServiceDatabase;
import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.JSONDownloaderHandler;

public class MainActivity extends Activity {

	public static MainActivity instance;
	public static GooglePlus plus;
	
	public boolean isNormalUser = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		findViewById(R.id.imageView1).postDelayed(new Runnable() {
			@Override
			public void run() {
				instance.setContentView(R.layout.main);
				switchUser(true);
			}
		}, 2000);

		plus = new GooglePlus();
		plus.signInWithGplus(instance);

		startService(new Intent(this, ServiceDatabase.class));
	}

	public void switchUser(boolean isNormalUser) {
		this.isNormalUser = isNormalUser;
		TableLayout user = (TableLayout) findViewById(R.id.optionsUser);
		TableLayout engie = (TableLayout) findViewById(R.id.optionsEngie);
		user.setVisibility(isNormalUser ? View.VISIBLE : View.GONE);
		engie.setVisibility(isNormalUser ? View.VISIBLE : View.VISIBLE);
		findViewById(R.id.relativeLayout1).invalidate();
	}

	public void repairPhone(View view) {
		startActivity(new Intent(this, ActivityRepairPhone.class));
	}

	public void openMap(View view) {
		Intent intent = new Intent(this, ActivityMap.class);
		startActivity(intent);
	}

	public void onPhoneInfoClicked(View view) {
		startActivity(new Intent(this, ActivityPhoneInfo.class));
	}

	public void onPhoneListClicked(View v) {
		startActivity(new Intent(this, ActivityPhoneList.class));
	}

	public void onRegisterEngiePressed(View v) {
		startActivity(new Intent(this, ActivityRegisterEngie.class));
	}

	public static class GetUserType extends JSONDownloaderHandler {

		static final String[] cols = new String[] {
			"zak_id"
		};

		private List<Map<String, String>> businesses;
		private List<Map<String, String>> users;
		private MainActivity activity;

		public GetUserType(MainActivity activity) {
			super("http://www.speedFix.eu/android/get_all_bizz.php", "zakelijk", cols);
			new GetAllUsers(this);
			this.activity = activity;
		}

		@Override
		public void data(List<Map<String, String>> data) {
			businesses = data;
			checkForCompany();
		}

		public void postUsers(List<Map<String, String>> data) {
			users = data;
			checkForCompany();
		}

		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		public void checkForCompany() {
			if(businesses == null || users == null) {
				return;
			}
			for(Map<String, String> bizz : businesses) {
				for(Map<String, String> user : users) {
					if(!user.get("zak_id").isEmpty() && user.get("zak_id") == bizz.get("zak_id") && user.get("user_id_google").equals(MainActivity.plus.getUser().getId())) {
						activity.switchUser(false);
						return;
					}
				}
			}
			activity.switchUser(true);
		}
	}

	public static class GetAllUsers extends JSONDownloaderHandler {

		static final String[] cols = new String[] {
			"user_id",
			"user_id_google"
		};

		private GetUserType getUserType;

		public GetAllUsers(GetUserType getUserType) {
			super("http://www.speedFix.eu/android/get_all_users.php", "users", cols);
			this.getUserType = getUserType;
		}

		@Override
		public void data(List<Map<String, String>> data) {
			getUserType.postUsers(data);
		}

	}



}