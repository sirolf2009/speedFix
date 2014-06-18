package com.fok.speedfix;

/**
 * @author gokhankacan
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fok.speedfix.util.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ActivityGetUsers extends ListActivity {
	 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> userList;
 
    // url to get all users list
    private static String url_all_users = "http://185.27.141.17/android/get_users_zak.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "users_zak";
    private static final String TAG_ZAK_ID = "zak_id";
    private static final String TAG_BEDRIJF = "zak_bedrijfsnaam";

    
    // products JSONArray
    JSONArray users = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_all);
 
        // Hashmap for ListView
        userList = new ArrayList<HashMap<String, String>>();
 
        // Loading users in Background Thread
        new LoadAllUsers().execute();
 
        // Get listview
        ListView lv = getListView();
 
        // on selecting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
                // getting values from selected ListItem
                String zak_id = ((TextView) view.findViewById(R.id.zak_id)).getText().toString();
 
                // Starting new intent
                Intent in = new Intent(getApplicationContext(), ActivityEditUser.class);
                
                // sending pid to next activity
                in.putExtra(TAG_ZAK_ID, zak_id);
 
                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });
 
    }
 
    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
 
    }
 
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllUsers extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActivityGetUsers.this);
            pDialog.setMessage("Loading Users. Please wait...");
            
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All users from url
         * */
        protected String doInBackground(String... args) {
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_users, "GET", params);
 
            // Check your log cat for JSON reponse
            Log.d("All users: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    users = json.getJSONArray(TAG_USERS);
 
                    // looping through All Products
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_ZAK_ID);
                        String name = c.getString(TAG_BEDRIJF);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_ZAK_ID, id);
                        map.put(TAG_BEDRIJF, name);
 
                        // adding HashList to ArrayList
                        userList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            ActivityNewUser.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                    		ActivityGetUsers.this, userList,
                            R.layout.user_list, new String[] { TAG_ZAK_ID,
                                    TAG_BEDRIJF},
                            new int[] { R.id.zak_id, R.id.zak_bedrijfsnaam });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
}
