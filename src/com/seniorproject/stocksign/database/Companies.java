package com.seniorproject.stocksign.database;

import java.util.HashMap;

public class Companies {

	private static HashMap<String, String> companies = null;
	
	public static HashMap<String, String> getNames() {
		return companies;
	}
	
	public static void fillNames(String ticker, String name) {
		if(companies == null) {
			companies = new HashMap<String, String>();
		}
		companies.put(ticker, name);
	}
	
}
