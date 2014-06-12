package nl.gokhankacan.androidsql;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author gokhankacan
 *
 */
public class NewClient extends Activity {
	
	// 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);
	
        // Input Gegevens
        inputName = (EditText) findViewById(R.id.inputName);
        inputBrand = (EditText) findViewById(R.id.inputBrand);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        
	}

}
