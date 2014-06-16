package com.fok.speedfix;

import com.fok.speedfix.util.GooglePlus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Build;

public class ActivityPhoneInfo extends Activity {

	TextView Board;
	TextView Bootloader;
	TextView Brand;
	TextView Device;
	TextView Display;
	TextView Hardware;
	TextView Id;
	TextView Manufacturer;
	TextView Model;
	TextView Product;
	TextView User;
	
	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_info);
		
		GooglePlus.updateTitleBar(this);
		
		// Reference
		Board = (TextView) findViewById(R.id.id_board);
		Bootloader = (TextView) findViewById(R.id.id_bootloader);
		Brand = (TextView) findViewById(R.id.id_brand);
		Device = (TextView) findViewById(R.id.id_device);
		Display = (TextView) findViewById(R.id.id_display);
		Hardware = (TextView) findViewById(R.id.id_hardware);
		Id = (TextView) findViewById(R.id.id_id);
		Manufacturer = (TextView) findViewById(R.id.id_manufacturer);
		Model = (TextView) findViewById(R.id.id_model);
		Product = (TextView) findViewById(R.id.id_product);
		User = (TextView) findViewById(R.id.id_user);

		
		// Get API info
		String deviceBoard = Build.BOARD;
		String deviceBootloader = Build.BOOTLOADER;
		String deviceBrand = Build.BRAND;
		String deviceDevice = Build.DEVICE;
		String deviceDisplay = Build.DISPLAY;
		String deviceHardware = Build.HARDWARE;
		String deviceId = Build.ID;
		String deviceManufacturer = Build.MANUFACTURER;
		String deviceModel = Build.MODEL;
		String deviceProduct = Build.PRODUCT;
		// String deviceSerial = Build.SERIAL;
		String deviceUser = Build.USER;
		
		
		// SetText
		Board.setText("Board:  " + deviceBoard);
		Bootloader.setText("Bootloader:  " + deviceBootloader);
		Brand.setText("Brand:  " + deviceBrand);
		Device.setText("Device:  " + deviceDevice);
		Display.setText("Display:  " + deviceDisplay);
		Hardware.setText("Hardware:  " + deviceHardware);
		Id.setText("Id:  " + deviceId);
		Manufacturer.setText("Manufacturer:  " + deviceManufacturer);
		Model.setText("Model:  " + deviceModel);
		Product.setText("Product:  " + deviceProduct);
		User.setText("User:  " + deviceUser);
	}
	
}
