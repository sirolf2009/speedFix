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
		int fifthDelim = engineerData.indexOf("&", fourthDelim+1);
		int sixthDelim = engineerData.indexOf("&", fifthDelim+1);
		int seventhDelim = engineerData.indexOf("&", sixthDelim+1);
		int eigthDelim = engineerData.indexOf("&", seventhDelim+1);
		int ninthDelim = engineerData.indexOf("&", eigthDelim+1);
		int tenthDelim = engineerData.indexOf("&", ninthDelim+1);
		int eleventhDelim = engineerData.indexOf("&", tenthDelim+1);
		int twelfthDelim = engineerData.indexOf("&", eleventhDelim+1);
		int thirteenthDelim = engineerData.indexOf("&", twelfthDelim+1);
		int fourteenthDelim = engineerData.indexOf("&", thirteenthDelim+1);
		//int fifteenthDelim = engineerData.indexOf("&", fourteenthDelim+1);
		String key = engineerData.substring(0, firstDelim);
		String companyName = engineerData.substring(firstDelim+1, secondDelim);
		String kvk = engineerData.substring(secondDelim+1, thirdDelim);
		String email = engineerData.substring(thirdDelim+1, fourthDelim);
		String firstName = engineerData.substring(fourthDelim+1, fifthDelim);
		String surName = engineerData.substring(fifthDelim+1, sixthDelim);
		String gender = engineerData.substring(sixthDelim+1, seventhDelim);
		String postalCode = engineerData.substring(seventhDelim+1, eigthDelim);
		String streetNumber = engineerData.substring(eigthDelim+1, ninthDelim);
		String streetAddition = engineerData.substring(ninthDelim+1, tenthDelim);
		String street = engineerData.substring(tenthDelim+1, eleventhDelim);
		String province = engineerData.substring(eleventhDelim+1, twelfthDelim);
		String land = engineerData.substring(twelfthDelim+1, thirteenthDelim);
		String telephone = engineerData.substring(thirteenthDelim+1, fourteenthDelim);
		String mobile = engineerData.substring(fourteenthDelim+1);
		engineer.put("zak_id", key);
		engineer.put("zak_bedrijfsnaam", companyName);
		engineer.put("zak_kvk", kvk);
		engineer.put("zak_email", email);
		engineer.put("zak_naam", firstName);
		engineer.put("zak_achternaam", surName);
		engineer.put("zak_geslacht", gender);
		engineer.put("zak_postcode", postalCode);
		engineer.put("zak_huisnummer", streetNumber);
		engineer.put("zak_toevoeging", streetAddition);
		engineer.put("zak_straat", street);
		engineer.put("zak_plaats", land);
		engineer.put("zak_provincie", province);
		engineer.put("zak_land", land);
		engineer.put("zak_telefoon", telephone);
		engineer.put("zak_mobiel", mobile);
		return engineer;
	}
	
	public static String encipherEngineer(Map<String, String> engineerData) {
		return encipherEngineer(engineerData.get("zak_id"), 
				engineerData.get("zak_bedrijfsnaam"), 
				engineerData.get("zak_kvk"), 
				engineerData.get("zak_email"), 
				engineerData.get("zak_naam"),
				engineerData.get("zak_achternaam"), 
				engineerData.get("zak_geslacht"), 
				engineerData.get("zak_postcode"), 
				engineerData.get("zak_huisnummer"),
				engineerData.get("zak_toevoeging"), 
				engineerData.get("zak_straat"), 
				engineerData.get("zak_plaats"), 
				engineerData.get("zak_provincie"),
				engineerData.get("zak_land"), 
				engineerData.get("zak_telefoon"), 
				engineerData.get("zak_mobiel"));
	}
	
	public static String encipherEngineer(String name, String street, String location, String ID, String price, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11) {
		return name+"&"+street+"&"+location+"&"+ID+"&"+price+"&"+string+"&"+string2+"&"+string3+"&"+string4+"&"+string5+"&"+string6+"&"+string7+"&"+string8+"&"+string9+"&"+string10+"&"+string11;
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
		int ninthDelim = phoneData.indexOf("&", eigthDelim+1);
		int tenthDelim = phoneData.indexOf("&", ninthDelim+1);
		int eleventhDelim = phoneData.indexOf("&", tenthDelim+1);
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
		phone.put("device_id", key);
		phone.put("device_board", board);
		phone.put("device_brand", brand);
		phone.put("device_device", device);
		phone.put("device_display", display);
		phone.put("device_hardware", hardware);
		phone.put("device_software", software);
		phone.put("device_manufacturer", manufacturer);
		phone.put("device_model", model);
		phone.put("device_product", product);
		phone.put("device_component", component);
		phone.put("device_description", description);
		return phone;
	}
	
	public static String encipherPhone(Map<String, String> phoneData) {
		return encipherPhone(phoneData.get("device_id"), 
				phoneData.get("device_board"), 
				phoneData.get("device_brand"), 
				phoneData.get("device_device"), 
				phoneData.get("device_display"), 
				phoneData.get("device_hardware"), 
				phoneData.get("device_software"), 
				phoneData.get("device_manufacturer"), 
				phoneData.get("device_model"), 
				phoneData.get("device_product"), 
				phoneData.get("device_component"), 
				phoneData.get("device_description"));
	}
	
	public static String encipherPhone(String key, String board, String brand, String device, String display, String hardware, String software, String manufacturer, String model, String product, String component, String description) {
		return key+"&"+board+"&"+brand+"&"+device+"&"+display+"&"+hardware+"&"+software+"&"+manufacturer+"&"+model+"&"+product+"&"+component+"&"+description;
	}

}
