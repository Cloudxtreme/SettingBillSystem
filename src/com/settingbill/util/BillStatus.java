package com.settingbill.util;

import java.util.HashMap;


public class BillStatus {
	
	public static HashMap<String, Integer> initStatus() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("edit", 100);		// Starting point. After editing, status goes back to 100
		map.put("check", 150);
		map.put("trash", -500);		// End point (bill trashed)
		map.put("recover", -100);
		map.put("pass", 200);
		map.put("refuse", -200);
		map.put("receive", 300);
		map.put("reject", -300);
		map.put("report", 500);		// End point (job done)
		map.put("finish", 400);
		return map;
	}
}
