package com.fok.speedfix;

import java.util.List;
import java.util.Map;

import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.IJsonResponse;
import com.fok.speedfix.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityRegisterEngie extends Activity implements IJsonResponse {
	
	private String code;

	@Override
	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.engie_register);
	}
	
	public void validate(View v) {
		EditText codeView = (EditText) findViewById(R.id.editText1);
		code = codeView.getText().toString();
		new Helper.GetAllBusinesses(this).execute();
	}

	@Override
	public void getResponse(List<Map<String, String>> data, String tag) {
		for(Map<String, String> bizz : data) {
			if(code.equals(bizz.get("zak_activate"))) {
				Log.i("company found");
				Map<String, String> userInfo = MainActivity.instance.userInfo;
				userInfo.put("zak_id", bizz.get("zak_id"));
				((EditText) findViewById(R.id.editText1)).setText("");
				new Helper.UserUpdate(userInfo).execute();
				
				bizz.put("zak_activate", "");
				new Helper.EngineerUpdate(bizz).execute();

				((EditText) findViewById(R.id.editText1)).setText("");
				
				MainActivity.instance.switchUser(false);
				MainActivity.instance.userInfo = userInfo;
				MainActivity.instance.companyInfo = bizz;
				startActivity(new Intent(this, MainActivity.class));
				finish();
				return;
			}
		}
		Log.i("no company found");
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Error processing code");
		helpBuilder.setMessage("Invalid activation code");
		helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}

}
