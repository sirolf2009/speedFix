package com.fok.speedfix.util;

import java.util.List;
import java.util.Map;

public class Log {

	public Log() {
	}
	
	public static void i(String msg) {
		android.util.Log.i("speedFix", msg);
	}
	
	public static void i(Map<?, ?> map) {
		String msg = "";
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
				msg += list.get(i);
			}
			i(msg);
		}
	}

	public static void e(String msg) {
		android.util.Log.e("speedFix", msg);
	}

}
