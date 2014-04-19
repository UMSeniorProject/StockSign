package com.seniorproject.stocksign.database;

import java.util.ArrayList;

public class PriceDataStorage {

	public static PriceData[] priceData = null;
	public static String currentStock = null;
	
	private static ArrayList<Float> openPrices = null;
	private static ArrayList<Float> closePrices = null;
	private static ArrayList<Float> highPrices = null;
	private static ArrayList<Float> lowPrices = null;
	
	public static void addOpenPrice(float price) {
		if(openPrices == null) {
			openPrices = new ArrayList<Float>();
		}
		openPrices.add(price);
	}
	
	public static void addClosePrice(float price) {
		if(closePrices == null) {
			closePrices = new ArrayList<Float>();
		}
		closePrices.add(price);
	}
	
	public static void addHighPrice(float price) {
		if(highPrices == null) {
			highPrices = new ArrayList<Float>();
		}
		highPrices.add(price);
	}
	
	public static void addLowPrice(float price) {
		if(lowPrices == null) {
			lowPrices = new ArrayList<Float>();
		}
		lowPrices.add(price);
	}

	public static ArrayList<Float> getOpenPrices() {
		return openPrices;
	}

	public static ArrayList<Float> getClosePrices() {
		return closePrices;
	}

	public static ArrayList<Float> getHighPrices() {
		return highPrices;
	}

	public static ArrayList<Float> getLowPrices() {
		return lowPrices;
	}
	
	
}
