package com.seniorproject.stocksign.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.content.Context;
import android.util.Log;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.graphing.IndicatorInfo;

public class PriceDataStorage {

	private static String tickerName = null;

	// private static ArrayList<Float> openPrices = null;
	// private static ArrayList<Float> closePrices = null;
	// private static ArrayList<Float> highPrices = null;
	// private static ArrayList<Float> lowPrices = null;
	private static ArrayList<Float> prices = null;
	private static HashMap<String, IndicatorInfo> indicatorMap = null;
	private static TreeMap<String, ?> activeIndicators = null;

	private static Context context = null;
	private static int size = -1;

	public static void setPriceData(PriceData[] priceData, String ticker,
			Context context, Map<String, ?> activeIndicators) {

		// error checks
		if (priceData == null) {
			Log.e("PRICEDATA", " data is null");
			return;
		}

		if (ticker == null) {
			Log.e("PRICEDATA", " ticker is null");
			return;
		}

		if (context == null) {
			Log.e("PRICEDATA", " context is null");
			return;
		}

		if (activeIndicators == null) {
			Log.e("PRICEDATA", " active indicators is null");
			return;
		}

		// initialize variables
		PriceDataStorage.tickerName = ticker;
		PriceDataStorage.context = context;
		PriceDataStorage.size = priceData.length;
		PriceDataStorage.activeIndicators = new TreeMap<String, Object>(
				activeIndicators);

		
		// initialize data lists
		PriceDataStorage.setupLists();
		PriceDataStorage.populateLists(priceData);
	}

	private static void populateLists(PriceData[] priceData) {

		Log.d("PRICE", "first date is " + priceData[0].getDate()
				+ " with price = " + priceData[0].getClose());

		// populate price lists
		for (int i = 0; i < priceData.length; i++) {
			PriceDataStorage.prices.add(priceData[i].getClose());
			// PriceDataStorage.closePrices.add(priceData[i].getClose());
			// PriceDataStorage.highPrices.add(priceData[i].getHigh());
			// PriceDataStorage.lowPrices.add(priceData[i].getLow());
		}

		// get indicator names
		String[] indicators = context.getResources().getStringArray(
				R.array.chart_indicators);

		// populate indicator map
		for (int i = 0; i < indicators.length; i++) {
			String name = indicators[i];
			float[] values = new float[size];
			boolean isActive = activeIndicators.containsKey(name) ? true
					: false;
			indicatorMap
					.put(name, new IndicatorInfo(name, values, i, isActive));
		}

		// TODO: indicator names should be added programatically
		for (int i = 0; i < size; i++) {
			// populate MACDsum
			indicatorMap.get("MACDsum").getValues()[i] = priceData[i]
					.getMacdSum();
			// populate MACDslope
			indicatorMap.get("MACDslope").getValues()[i] = priceData[i]
					.getMacdSlope();
			// populate CMFInd
			indicatorMap.get("CMFInd").getValues()[i] = priceData[i]
					.getCmfInd();
			// populate Vortex
			indicatorMap.get("Vortex").getValues()[i] = priceData[i]
					.getVortex();
		}
	}

	private static void setupLists() {
		if (prices == null) {
			prices = new ArrayList<Float>();
		} else if (!prices.isEmpty()) {
			prices.clear();
		}
		/*
		 * if (closePrices == null) { closePrices = new ArrayList<Float>(); }
		 * else { closePrices.clear(); } if (highPrices == null) { highPrices =
		 * new ArrayList<Float>(); } else { highPrices.clear(); } if (lowPrices
		 * == null) { lowPrices = new ArrayList<Float>(); } else {
		 * lowPrices.clear(); }
		 */
		if (indicatorMap == null) {
			indicatorMap = new HashMap<String, IndicatorInfo>();
		} else if (!indicatorMap.isEmpty()) {
			indicatorMap.clear();
		}
	}

	public static int getSize() {
		return size;
	}

	public static String getTicker() {
		return tickerName;
	}

	public static ArrayList<Float> getPrices() {
		return prices;
	}

	/*
	 * public static ArrayList<Float> getOpenPrices() { return openPrices; }
	 * 
	 * public static ArrayList<Float> getHighPrices() { return highPrices; }
	 * 
	 * public static ArrayList<Float> getLowPrices() { return lowPrices; }
	 */

	public static HashMap<String, IndicatorInfo> getIndicatorMap() {
		return indicatorMap;
	}
	
	public static TreeMap<String, ?> getActiveIndicators() {
		return activeIndicators;
	}

}
