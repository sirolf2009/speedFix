package com.fok.speedfix;

import java.util.HashMap;
import java.util.Map;

import com.fok.speedfix.services.ServiceDatabase;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityRepairPhone extends Activity {

	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.repair_phone);
	}
	
	@TargetApi(Build.VERSION_CODES.FROYO)
	public void onOkPressed(View view) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("board", Build.BOARD);
		info.put("bootloader", Build.BOOTLOADER);
		info.put("brand", Build.BRAND);
		info.put("device", Build.DEVICE);
		info.put("display", Build.DISPLAY);
		info.put("hardware", Build.HARDWARE);
		info.put("software", Build.ID);
		info.put("manufacturer", Build.MANUFACTURER);
		info.put("model", Build.MODEL);
		info.put("product", Build.PRODUCT);
		info.put("user", Build.USER);
		info.put("component", ((Spinner)findViewById(R.id.spinner1)).getSelectedItem().toString());
		info.put("description", ((EditText)findViewById(R.id.editText1)).getText().toString());
		ServiceDatabase.instance.sendPhone(info);
	}

}
