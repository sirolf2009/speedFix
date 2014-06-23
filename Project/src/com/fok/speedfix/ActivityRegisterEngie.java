package com.fok.speedfix;

import java.util.List;
import java.util.Map;

import com.fok.speedfix.util.Helper;
import com.fok.speedfix.util.IJsonResponse;

import android.app.Activity;
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
			if(code.equals(bizz.get("zak_code"))) {
				//TODO
				//activate
				return;
			}
		}
		((EditText) findViewById(R.id.editText1)).setText("");
	}

}
