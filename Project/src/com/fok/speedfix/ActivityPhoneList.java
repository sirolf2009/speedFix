package com.fok.speedfix;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fok.speedfix.util.GooglePlus;
import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.IJsonResponse;
import com.fok.speedfix.util.JSONDownloaderHandler;
import com.fok.speedfix.util.JSONUploaderHandler;
import com.fok.speedfix.util.Log;
import com.fok.speedfix.util.PhoneListAdapter;
import com.fok.speedfix.util.Storage;

public class ActivityPhoneList extends Activity {

	protected static List<Map<String, String>> phones;
	protected static Map<String, String> company;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.phone_list);
		GooglePlus.updateTitleBar(this);

		Set<String> phonesRaw = Storage.readPhones(this);
		phones = new ArrayList<Map<String, String>>();
		if(phonesRaw == null) {
			phonesRaw = new HashSet<String>();
		}
		for(String phone : phonesRaw) {
			phones.add(Helper.decipherPhone(phone));
		}
		List<String> brands = new ArrayList<String>();
		for(Map<String, String> phone : phones) {
			brands.add(phone.get("device_id"));
		}
		((ListView)findViewById(R.id.listViewPhones)).setAdapter(new PhoneListAdapter(this, R.id.companyDetailNameDescriptor, brands, phones));

		new GetCompanyInfo().execute();
		new UpdateLowestBids(this).execute();
	}

	public void test(View v) {
		View info = v.findViewById(R.id.moar_info);
		info.setVisibility(info.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
	}

	public void onBidPressed(View v) {
		RelativeLayout layout = (RelativeLayout) v.getParent();

		EditText bid = (EditText) layout.findViewById(R.id.editText1);
		TextView ID = (TextView) layout.findViewById(R.id.phoneID);
		try {
			new SendBid(ID.getText().toString(), Double.parseDouble(bid.getText().toString()), this);
		} catch(NumberFormatException e) {
			AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
			helpBuilder.setTitle("Error processing bid");
			helpBuilder.setMessage("\""+bid.getText().toString()+ "\" is not a valid number");
			helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			AlertDialog helpDialog = helpBuilder.create();
			helpDialog.show();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void createNotification(String phoneIssue, Context context) {
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.logo)
		.setContentTitle(phoneIssue)
		.setContentText("Tap for details")
		.setAutoCancel(true);

		Intent resultIntent = new Intent(context, ActivityPhoneList.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ActivityPhoneList.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}

	public static void notifyIfNewPhone(Context context, Iterable<String> phones) {
		Set<String> saved = Storage.readPhones(context);
		for(String string : phones) {
			if(saved == null) {
				Map<String, String> phoneInfo = Helper.decipherPhone(string);
				saved = new HashSet<String>();
				saved.add(string);
				Storage.savePhones(saved, context);
				createNotification(phoneInfo.get("device_brand") + " " + phoneInfo.get("device_component"), context);
			} else if(!saved.contains(string)) {
				Map<String, String> phoneInfo = Helper.decipherPhone(string);
				saved.add(string);
				Storage.savePhones(saved, context);
				createNotification(phoneInfo.get("device_brand") + " " + phoneInfo.get("device_component"), context);
			}
		}
	}
	
	public static class SendBid implements IJsonResponse {

		private String phoneID;
		private double price;
		private Context context;
		
		
		public SendBid(String phoneID, double price, Context context) {
			this.phoneID = phoneID;
			this.price = price;
			this.context = context;
			new Helper.GetAllProposals(this).execute();
		}
		
		@Override
		public void getResponse(List<Map<String, String>> data, String tag) {
			Log.i("searching for zak id "+ActivityPhoneList.company.get("zak_id"));
			Log.i(data);
			for(Map<String, String> row : data) {
				Log.i("found zak id "+row.get("zak_id"));
				if(row.get("device_id").equals(phoneID) && row.get("zak_id").equals(ActivityPhoneList.company.get("zak_id"))) {
					if(price >= Double.parseDouble(row.get("prop_bod"))) {
						AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
						helpBuilder.setTitle("Error processing bid");
						helpBuilder.setMessage("This bid is higher than your previous bid. Please bid below €"+row.get("prop_bod"));
						helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						AlertDialog helpDialog = helpBuilder.create();
						helpDialog.show();
						return;
					} else {
						Log.i("updating bid");
						new UpdateBid(phoneID, price, row.get("prop_id")).execute();
						AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
						helpBuilder.setTitle("Succes");
						helpBuilder.setMessage("Bid placed");
						helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						AlertDialog helpDialog = helpBuilder.create();
						helpDialog.show();
						new UpdateLowestBids((ActivityPhoneList) context).execute();
						return;
					}
				}
			}
			Log.i("creating a new bid");
			new NewBid(phoneID, price).execute();
			new UpdateLowestBids((ActivityPhoneList) context).execute();
		}
		
	}

	public static class NewBid extends JSONUploaderHandler {

		public NewBid(Map<String, String> rowData) {
			super("http://www.speedFix.eu/android/create_proposals.php", rowData);
		}

		public NewBid(String phoneID, double price) {
			this(createMap(phoneID, price));
		}

		public static Map<String, String> createMap(String phoneID, double price) {
			Map<String, String> map = new HashMap<String, String>();
			DecimalFormat format = new DecimalFormat("########.##");
			map.put("prop_bod", format.format(price));
			map.put("device_id", phoneID);
			map.put("zak_id", ActivityPhoneList.company.get("zak_id"));
			map.put("prop_time", SimpleDateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
			map.put("prop_duration", -1+"");
			map.put("prop_duration_time", "UNDEFINED");
			return map;
		}

	}

	public static class UpdateBid extends JSONUploaderHandler {

		public UpdateBid(String phoneID, double price, String propID) {
			super("http://www.speedFix.eu/android/update_proposal.php", getData(phoneID, price, propID));
		}

		public static Map<String, String> getData(String phoneID, double price, String propID) {
			Map<String, String> map = new HashMap<String, String>();
			DecimalFormat format = new DecimalFormat("########.##");
			map.put("prop_id", propID);
			map.put("prop_bod", format.format(price));
			map.put("device_id", phoneID);
			map.put("zak_id", ActivityPhoneList.company.get("zak_id"));
			map.put("prop_time", SimpleDateFormat.getDateInstance().format(Calendar.getInstance().getTime()));
			map.put("prop_duration", -1+"");
			map.put("prop_duration_time", "UNDEFINED");
			return map;
		}

	}

	public static class GetCompanyInfo extends JSONDownloaderHandler implements IJsonResponse {

		private List<Map<String, String>> businesses;
		private List<Map<String, String>> users;

		public GetCompanyInfo() {
			super("http://www.speedFix.eu/android/get_all_bizz.php", "zakelijk", Helper.GetAllBusinesses.cols);
			new Helper.GetAllUsers(this).execute();
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
					if(user.get("zak_id").isEmpty()) {
						continue;
					}
					if(user.get("zak_id").equals(bizz.get("zak_id")) && user.get("user_username").equals(MainActivity.plus.getUser().getDisplayName())) {
						ActivityPhoneList.company = bizz;
						return;
					}
				}
			}
			Log.e("no company info found");
		}

		@Override
		public void getResponse(List<Map<String, String>> data, String tag) {
			users = data;
			checkForCompany();
		}
	}

	public static class UpdateLowestBids extends JSONDownloaderHandler {

		static final String[] cols = {
			"prop_id",
			"zak_id",
			"device_id",
			"prop_bod"
		};

		private List<Map<String, String>> proposals;
		private ActivityPhoneList phoneList;

		public UpdateLowestBids(ActivityPhoneList phoneList) {
			super("http://speedFix.eu/android/get_all_proposals.php", "proposals", cols);
			this.phoneList = phoneList;
		}

		@Override
		public void data(List<Map<String, String>> data) {
			proposals = data;
			tryUpdate();
		}

		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		public void tryUpdate() {
			if(proposals == null) {
				return;
			}
			for(Map<String, String> phone : phones) {
				for(Map<String, String> proposal : proposals) {
					if(phone.get("device_id").equals(proposal.get("device_id"))) {
						if(phone.containsKey("lowestBid")) {
							double oldBid;
							try {
								oldBid = Double.parseDouble(phone.get("lowestBid"));
							} catch (NumberFormatException e) {
								continue;
							}
							double newBid = Double.parseDouble(proposal.get("prop_bod"));
							if(oldBid < newBid) {
								continue;
							}
						}
						phone.put("lowestBid", proposal.get("prop_bod"));
					}
				}
				if(phone.get("lowestBid") == null) {
					phone.put("lowestBid", "no bids");
				}
			}
			ListView list = (ListView) phoneList.findViewById(R.id.listViewPhones);
			((View)((View)list.getParent()).getParent()).postInvalidate();
		}

	}

}

