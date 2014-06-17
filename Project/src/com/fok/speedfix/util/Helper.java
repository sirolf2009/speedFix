package com.fok.speedfix.util;

import java.util.HashMap;
import java.util.Map;

public class Helper {

	public static Map<String, String> decipherEngineer(String engineerData) {
		Map<String, String> engineer = new HashMap<String, String>();
		int firstDelim = engineerData.indexOf("&");
		int secondDelim = engineerData.indexOf("&", firstDelim+1);
		int thirdDelim = engineerData.indexOf("&", secondDelim+1);
		int fourthDelim = engineerData.indexOf("&", thirdDelim+1);
		String name = engineerData.substring(0, firstDelim);
		String street = engineerData.substring(firstDelim+1, secondDelim);
		String location = engineerData.substring(secondDelim+1, thirdDelim);
		String ID = engineerData.substring(thirdDelim+1, fourthDelim);
		String price = engineerData.substring(fourthDelim+1);
		engineer.put("name", name);
		engineer.put("street", street);
		engineer.put("location", location);
		engineer.put("ID", ID);
		engineer.put("price", price);
		return engineer;
	}
	
	public static String encipherEngineer(Map<String, String> engineerData) {
		return encipherEngineer(engineerData.get("name"), engineerData.get("street"), engineerData.get("location"), engineerData.get("ID"), engineerData.get("price"));
	}
	
	public static String encipherEngineer(String name, String street, String location, String ID, String price) {
		return name+"&"+street+"&"+location+"&"+ID+"&"+price;
	}
	
	public static Map<String, String> decipherPhone(String phoneData) {
		Map<String, String> phone = new HashMap<String, String>();
		int firstDelim = phoneData.indexOf("&");
		int secondDelim = phoneData.indexOf("&", firstDelim+1);
		int thirdDelim = phoneData.indexOf("&", secondDelim+1);
		int fourthDelim = phoneData.indexOf("&", thirdDelim+1);
		int fifthDelim = phoneData.indexOf("&", fourthDelim+1);
		int sixthDelim = phoneData.indexOf("&", fifthDelim+1);
		int seventhDelim = phoneData.indexOf("&", sixthDelim+1);
		int eigthDelim = phoneData.indexOf("&", seventhDelim+1);
		int ninthDelim = phoneData.indexOf(eigthDelim+1);
		int tenthDelim = phoneData.indexOf("&", ninthDelim+1);
		int eleventhDelim = phoneData.indexOf(tenthDelim+1);
		String key = phoneData.substring(0, firstDelim);
		String board = phoneData.substring(firstDelim+1, secondDelim);
		String brand = phoneData.substring(secondDelim+1, thirdDelim);
		String device = phoneData.substring(thirdDelim+1, fourthDelim);
		String display = phoneData.substring(fourthDelim+1, fifthDelim);
		String hardware = phoneData.substring(fifthDelim+1, sixthDelim);
		String software = phoneData.substring(sixthDelim+1, seventhDelim);
		String manufacturer = phoneData.substring(seventhDelim+1, eigthDelim);
		String model = phoneData.substring(eigthDelim+1, ninthDelim);
		String product = phoneData.substring(ninthDelim+1, tenthDelim);
		String component = phoneData.substring(tenthDelim+1, eleventhDelim);
		String description = phoneData.substring(eleventhDelim+1);
		phone.put("key", key);
		phone.put("board", board);
		phone.put("brand", brand);
		phone.put("device", device);
		phone.put("display", display);
		phone.put("hardware", hardware);
		phone.put("software", software);
		phone.put("manufacturer", manufacturer);
		phone.put("model", model);
		phone.put("product", product);
		phone.put("component", component);
		phone.put("description", description);
		return phone;
	}
	
	public static String encipherPhone(Map<String, String> phoneData) {
		return encipherPhone(phoneData.get("key"), phoneData.get("board"), phoneData.get("brand"), phoneData.get("device"), phoneData.get("display"), phoneData.get("hardware"), phoneData.get("software"), phoneData.get("manufacturer"), phoneData.get("model"), phoneData.get("product"), phoneData.get("component"), phoneData.get("description"));
	}
	
	public static String encipherPhone(String key, String board, String brand, String device, String display, String hardware, String software, String manufacturer, String model, String product, String component, String description) {
		return key+"&"+board+"&"+brand+"&"+device+"&"+display+"&"+hardware+"&"+software+"&"+manufacturer+"&"+model+"&"+product+"&"+component+"&"+description;
	}

}
