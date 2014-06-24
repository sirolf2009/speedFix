package com.fok.speedfix.util;

import java.util.List;
import java.util.Map;

public class Log {

	public Log() {
	}

	public static void i(String msg) {
		android.util.Log.i("speedFix", Thread.currentThread().getStackTrace()[3].getFileName().replace(".java", "")+"("+Thread.currentThread().getStackTrace()[3].getLineNumber()+") "+msg);
	}
	
	public static void i(Object msg) {
		iSpecial(msg+"");
	}
	
	public static void i(Map<?, ?> map) {
		String msg = "";
		if(map == null) {
			iSpecial("null");
		}
		for(Map.Entry<?, ?> entry : map.entrySet()) {
			msg += entry.getKey()+" : "+entry.getValue() + "\n";
		}
		iSpecial(msg);
	}
	
	public static void i(List<?> list) {
		if(list == null) {
			iSpecial("null list");
		}else if(list.size() == 0) {
			iSpecial("Empty List");
		} else {
			String msg = "";
			for(int i = 0; i < list.size(); i++) {
				msg += "#"+i+" "+list.get(i)+", ";
			}
			iSpecial(msg);
		}
	}
	
	public static void i(String[] array) {
		if(array.length == 0) {
			iSpecial("Empty array");
		} else {
			String msg = "";
			for(int i = 0; i < array.length; i++) {
				msg += "#"+i+" "+array[i]+", ";
			}
			iSpecial(msg);
		}
	}
	
	private static void iSpecial(String msg) {
		android.util.Log.i("speedFix", Thread.currentThread().getStackTrace()[4].getFileName().replace(".java", "")+"("+Thread.currentThread().getStackTrace()[4].getLineNumber()+") "+msg);
	}

	public static void e(String msg) {
		android.util.Log.e("speedFix", msg);
	}
	
	public static void wtf(String msg) {
		android.util.Log.wtf("speedFix", msg);
	}

}
