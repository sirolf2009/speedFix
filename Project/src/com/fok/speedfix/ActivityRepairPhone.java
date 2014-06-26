package com.fok.speedfix;

import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.fok.speedfix.services.ServiceDatabase;

public class ActivityRepairPhone extends Activity {

	public void onCreate(Bundle saved) {
		super.onCreate(saved);
		setContentView(R.layout.repair_phone);
	}
	
	@TargetApi(Build.VERSION_CODES.FROYO)
	public void onOkPressed(View view) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("device_board", Build.BOARD);
		//info.put("bootloader", Build.BOOTLOADER);
		info.put("device_brand", Build.BRAND);
		info.put("device_device", Build.DEVICE);
		info.put("device_display", Build.DISPLAY);
		info.put("device_hardware", Build.HARDWARE);
		info.put("device_software", Build.ID);
		info.put("device_manufacturer", Build.MANUFACTURER);
		info.put("device_model", Build.MODEL);
		info.put("device_product", Build.PRODUCT);
		//info.put("user", Build.USER);
		info.put("device_component", ((Spinner)findViewById(R.id.spinner1)).getSelectedItem().toString());
		info.put("device_description", ((EditText)findViewById(R.id.editText1)).getText().toString());
		ServiceDatabase.instance.sendPhone(info);
		final ActivityRepairPhone instance = this;
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Succes");
		helpBuilder.setMessage("Your phone details have been send to phone repair businesses");
		helpBuilder.setPositiveButton("Ok",	new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				instance.finish();
			}
		});
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}

}
