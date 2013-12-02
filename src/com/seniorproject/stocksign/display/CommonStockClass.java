package com.seniorproject.stocksign.display;

import com.seniorproject.stocksign.database.Stock;

public class CommonStockClass {
	private static CommonStockClass instance = null;
	private static Stock stock = null;
	
	protected CommonStockClass(Stock s) {
		stock = s;
	}
	
	public static void setInstance(Stock s) {
		synchronized(CommonStockClass.class) {
			if(instance == null) {
				instance = new CommonStockClass(s);
			}
			else stock = s;
		}
	}
	
	//thread safe
	public static synchronized CommonStockClass getInstance() {
		return instance;
	}
	
	//thread safe
	public static synchronized Stock getStock() {
		return stock;
	}
}
