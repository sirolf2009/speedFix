package com.fok.speedfix.util;

import java.util.List;
import java.util.Map;

public class Log {

	public Log() {
	}

	public static void i(String msg) {
		android.util.Log.i("speedFix", msg);
	}
	

	public static void i(Object msg) {
		android.util.Log.i("speedFix", msg+"");
	}
	
	public static void i(Map<?, ?> map) {
		String msg = "";
		if(map == null) {
			i("null");
		}
		for(Map.Entry<?, ?> entry : map.entrySet()) {
			msg += entry.getKey()+" : "+entry.getValue() + "\n";
		}
		i(msg);
	}
	
	public static void i(List<?> list) {
		if(list.size() == 0) {
			i("Empty List");
		} else {
			String msg = "";
			for(int i = 0; i < list.size(); i++) {
				msg += "#"+i+" "+list.get(i)+", ";
			}
			i(msg);
		}
	}
	
	public static void i(String[] array) {
		if(array.length == 0) {
			i("Empty array");
		} else {
			String msg = "";
			for(int i = 0; i < array.length; i++) {
				msg += "#"+i+" "+array[i]+", ";
			}
			i(msg);
		}
	}

	public static void e(String msg) {
		android.util.Log.e("speedFix", msg);
	}
	
	public static void wtf(String msg) {
		android.util.Log.wtf("speedFix", msg);
	}

}
