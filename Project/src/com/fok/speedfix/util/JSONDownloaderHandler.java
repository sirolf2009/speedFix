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
	}

	/**
	 * getting All products from url
	 * */
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		JSONObject json = jParser.makeHttpRequest(url, "GET", params);

		try {
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {

				JSONArray rows = json.getJSONArray(tag_name);

				for (int i = 0; i < rows.length(); i++) {

					JSONObject c = rows.getJSONObject(i);

					Map<String, String> row = new HashMap<String, String>();

					for(String tag : cols) {
						row.put(tag, c.getString(tag));
					}
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
		data(rowDataList);
	}
	
	public abstract void data(List<Map<String, String>> data);

}
