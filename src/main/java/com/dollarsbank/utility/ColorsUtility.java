package com.dollarsbank.utility;

import java.util.HashMap;
import java.util.Map;

public class ColorsUtility {
	private static final Map<String, String> colors = new HashMap<String, String>();
	static{
		colors.put("black", "\u001b[30m");
		colors.put("red", "\\u001b[31m");
		colors.put("green", "\u001b[32m");
		colors.put("yellow", "\u001b[33m");
		colors.put("blue", "\u001b[34m");
		colors.put("pink", "\u001b[35m");
		colors.put("teal", "\u001b[36m");
		colors.put("white", "\u001b[37m");
		colors.put("reset", "\u001b[0m");
		
	}
	public static String toColor(String color, String text) {
		if (!colors.containsKey(color)) {
			return colors.get("reset") + text;
		}
		return colors.get(color) + text + colors.get("reset");
	}
}
