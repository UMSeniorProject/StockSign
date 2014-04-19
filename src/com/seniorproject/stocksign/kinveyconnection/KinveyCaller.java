package com.seniorproject.stocksign.kinveyconnection;

import java.util.Arrays;

import com.seniorproject.stocksign.activity.MainActivity;
import com.seniorproject.stocksign.database.PriceData;
import com.seniorproject.stocksign.database.PriceDataStorage;
import com.seniorproject.stocksign.database.Stock;
import com.seniorproject.stocksign.display.DisplayStockRatioData;
import com.seniorproject.stocksign.display.DividendScore;
import com.seniorproject.stocksign.display.GrowthScore;
import com.seniorproject.stocksign.display.TotalScore;
import com.seniorproject.stocksign.fragment.HotStocksSectionFragment;
import com.seniorproject.stocksign.searching.SearchStockActivity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class KinveyCaller {
	
	public static void callAppropriateActivityMethod(Activity caller, Object[] data) {
		if(caller == null) {
			Log.e("KinveyCaller", "Caller is NULL");
			return;
		}
		
		if(data == null) {
			Log.e("KinveyCaller", "data is NULL");
			return;
		} else if(data.length == 0) {
			Log.e("KinveyCaller", "data is empty");
			return;
		}
		
		if(caller instanceof MainActivity) {
			
		} else if(caller instanceof DisplayStockRatioData) {
			if(data instanceof Stock[]) {
				((DisplayStockRatioData) caller).kinveyResponceStockMethod(data[0]);
			} else if(data instanceof PriceData[]) {
				((DisplayStockRatioData) caller).kinveyResponcePriceMethod(data);
			}
			
		} else if(caller instanceof SearchStockActivity) {
			PriceData[] priceData = Arrays.copyOf(data, data.length, PriceData[].class);
			for(PriceData p : priceData) {
				if(!PriceDataStorage.getOpenPrices().isEmpty()) {
					PriceDataStorage.getOpenPrices().clear();
				}
				PriceDataStorage.addOpenPrice(p.getOpen());
				
				if(!PriceDataStorage.getClosePrices().isEmpty()) {
					PriceDataStorage.getClosePrices().clear();
				}
				PriceDataStorage.addClosePrice(p.getClose());
				
				if(!PriceDataStorage.getHighPrices().isEmpty()) {
					PriceDataStorage.getHighPrices().clear();
				}
				PriceDataStorage.addHighPrice(p.getHigh());
				
				if(!PriceDataStorage.getLowPrices().isEmpty()) {
					PriceDataStorage.getLowPrices().clear();
				}
				PriceDataStorage.addLowPrice(p.getLow());
			}
		} else if(caller instanceof DividendScore) {
			if(data instanceof Stock[]) {
				((DividendScore) caller).kinveyResponceMethod(data);
			} else if(data instanceof PriceData[]) {
				
			}
		} else if(caller instanceof GrowthScore) {
			
		} else if(caller instanceof TotalScore) {
			
		}
	}
	
	public synchronized static void callAppropriateFragmentMethod(Fragment fragment, 
			Object[] data, View view, String scoreType) {
		//error checks
		if(scoreType == null) {
			Log.e("KinveyCaller", "scoreType is NULL");
			return;
		}
		
		if(fragment instanceof HotStocksSectionFragment) {
			if(data instanceof Stock[]) {
				Stock[] stocks = Arrays.copyOf(data, data.length, Stock[].class);
				((HotStocksSectionFragment) fragment).kinveyResponceMethod(stocks, view, scoreType);
			}
		}
	}
}
