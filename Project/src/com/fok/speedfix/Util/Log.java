package com.fok.speedfix.util;

public class Log {

	public Log() {
	}
	
	public static void i(String msg) {
		android.util.Log.i("speedFix", msg);
	}

	public static void e(String msg) {
		android.util.Log.e("speedFix", msg);
	}

}
