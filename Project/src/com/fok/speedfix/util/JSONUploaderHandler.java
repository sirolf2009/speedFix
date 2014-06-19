package com.fok.speedfix.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public abstract class JSONUploaderHandler extends AsyncTask<String, String, String> {

	private String url;
	private Map<String, String> rowData;
	private JSONParser jsonParser;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_FAILURE = "failure";

	public JSONUploaderHandler (String url, Map<String, String> rowData) {
		this.url = url;
		this.rowData = rowData;
		jsonParser = new JSONParser();
	}
	
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Creating product
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for(Entry<String, String> entry : rowData.entrySet()) {
        	params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        // getting JSON Object
        // Note that create product url accepts POST method
        Log.i("starting upload");
        JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
        Log.i("uploaded");

        // check for success tag
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
            	onSucces();
                return TAG_SUCCESS;
            }
        } catch (JSONException e) {
        	e.printStackTrace();
        	onFailure(e.getMessage());
        }
        return TAG_FAILURE;
    }
    
    public void onSucces() {
    }
    
    public void onFailure(String error) {
    }

}