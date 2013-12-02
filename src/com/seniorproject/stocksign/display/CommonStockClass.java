package com.seniorproject.stocksign.display;

import com.seniorproject.stocksign.database.RatioConstants;
import com.seniorproject.stocksign.database.Stock;

import com.google.gson.Gson;

public class CommonStockClass {
	/*private static CommonStockClass instance = null;
	private static Stock stock = null;
	private static String[] ratioData = null;
	
	private void fillRatioStringArray(Stock s) {
		ratioData = new String[RatioConstants.totalRatios];
		ratioData = s.toString().split(",");
		Gson gson = new Gson();
		Stock currentStock = CommonStockClass.getStock();
		String[] stockData = gson.fromJson(currentStock, DataObject.class);
	}
	
	protected CommonStockClass(Stock s) {
		stock = s;
		fillRatioStringArray(s);
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
	
	public static synchronized String[] getRatioArray() {
		return ratioData;
	}*/
}
