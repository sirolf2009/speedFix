package com.fok.speedfix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_DbRead extends Activity {
	
	/** onCreate() is called at start of activity */
	private EditText name;
	private EditText user;
	private EditText password;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		final WebView webview = new WebView(this);
		final Activity activity = this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db);
		
		user = (EditText) findViewById(R.id.editUser);
		password = (EditText) findViewById(R.id.editPassword);
		name = (EditText) findViewById(R.id.editName);
		
		Button queryButton = (Button) findViewById(R.id.buttonQuery);
		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient() {
			
			public void onProgressChanged(WebView view, int progress) {
				Toast.makeText(activity, Integer.toString(progress) + "%", Toast.LENGTH_SHORT).show();
			}
		}); // webview.setWebChromeClient-END
		
		
		webview.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode, String description, String FalingUrl) {
				Toast.makeText(activity, "Error! " + description, Toast.LENGTH_SHORT).show();
			}
			
			public void onPageFinished(WebView view, String url) {
				Toast.makeText(activity, url, Toast.LENGTH_SHORT).show();
			}
		}); // webview.setWebViewClient-END
		
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String adress;
				adress = "http://185.27.141.17/android/db.php?user=";
				adress = adress + user.getText().toString() + "&password=";
				adress = adress + name.getText().toString();
				
					setContentView(R.layout.query);
					setContentView(webview);
					
					webview.loadUrl(adress);
				
			}
		});
		
	}
	

}
