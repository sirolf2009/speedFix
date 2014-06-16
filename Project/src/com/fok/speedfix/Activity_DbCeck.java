package com.fok.speedfix;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Activity_DbCeck extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final WebView webview = new WebView(this);
		setContentView(webview);
		
		webview.getSettings().setJavaScriptEnabled(true);
		
		final Activity activity = this;
		webview.setWebChromeClient(new WebChromeClient() {
			
			public void onProgessChanged(WebView view, int progress) {
				// Activities en WebViews
				
				Toast.makeText(activity, Integer.toString(progress) + "%", Toast.LENGTH_SHORT).show();
						

				} // onProgessChanged-END
			}); // webview.setWebChromeClient-END
		
		// ################
		webview.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Toast.makeText(activity, "http://www.speedfix.eu", Toast.LENGTH_SHORT).show();
			} // onReceivedError-END
		}); // webview.setWebViewClient-END
		
		webview.loadUrl("http://www.speedfix.eu/android/db.php");
	}

}
