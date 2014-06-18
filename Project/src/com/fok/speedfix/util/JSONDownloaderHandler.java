package com.fok.speedfix.util;
/**
 * 
 * @author gokhankacan
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public abstract class JSONDownloaderHandler extends AsyncTask<String, String, String> {

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	List<Map<String, String>> rowDataList;

	private String url;
	private String tag_name;

	private static final String TAG_SUCCESS = "success";

	private String[] cols; 

	public JSONDownloaderHandler (String url, String tag_name, String... cols) {
		this.url = url;
		this.tag_name = tag_name;
		this.cols = cols;
		rowDataList = new ArrayList<Map<String, String>>();
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
//		pDialog = new ProgressDialog(context);
//		pDialog.setMessage("Loading users. Please wait...");
//		pDialog.setIndeterminate(false);
//		pDialog.setCancelable(false);
//		pDialog.show();
	}

	/**
	 * getting All products from url
	 * */
	protected String doInBackground(String... args) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		// getting JSON string from URL
		JSONObject json = jParser.makeHttpRequest(url, "GET", params);


		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {

				// Users found
				// Getting Array of Users
				JSONArray rows = json.getJSONArray(tag_name);


				// looping through All Users
				for (int i = 0; i < rows.length(); i++) {

					JSONObject c = rows.getJSONObject(i);

					Map<String, String> row = new HashMap<String, String>();

					// Storing each json item in variable
					for(String tag : cols) {
						row.put(tag, c.getString(tag));
					}

					/*String zak_id = c.getString(TAG_ID);
					String zak_bedrijfsnaam = c.getString(TAG_BEDRIJFSNAAM);


					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_ID, zak_id);
					map.put(TAG_BEDRIJFSNAAM, zak_bedrijfsnaam);
					########################################### */

					// adding HashList to ArrayList
					rowDataList.add(row);
				}
			} else {
				onEmtpyDatabase();
			}
		} catch (JSONException e) {
			Log.e("speedFix", e.getMessage());
			e.printStackTrace();
			return "Error";
		}
		return "Success";
	}

	public void onEmtpyDatabase() {
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog after getting all products
		//pDialog.dismiss();
		data(rowDataList);
		/*// updating UI from Background Thread
		runOnUiThread(new Runnable() {
			public void run() {
		 *//**
		 * Updating parsed JSON data into ListView
		 * *//*
				ListAdapter adapter = new SimpleAdapter(
						ActivityUserZakelijkGet.this, userList,
						R.layout.list_item, new String[] { TAG_ID,
								TAG_BEDRIJFSNAAM},
								new int[] { R.id.zak_id, R.id.zak_bedrijfsnaam });
				Log.i("speedFix", userList.size()+"");
				// updating listview
				setListAdapter(adapter);
			}
		});*/
	}
	
	public abstract void data(List<Map<String, String>> data);

}
