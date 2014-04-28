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
		}
		
		if(data == null) {
			Log.e("KinveyCaller", "data is NULL");
		} else if(data.length == 0) {
			Log.e("KinveyCaller", "data is empty");
		}
		
		if(caller instanceof MainActivity) {
			
		} else if(caller instanceof DisplayStockRatioData) {
			if(data instanceof Stock[]) {
				((DisplayStockRatioData) caller).kinveyResponceStockMethod(data[0]);
			} else if(data instanceof PriceData[]) {
				((DisplayStockRatioData) caller).kinveyResponcePriceMethod(data);
			} else if(data == null) {
				((DisplayStockRatioData) caller).kinveyResponceStockMethod(null);
			}
			
		} else if(caller instanceof SearchStockActivity) {
			// POSSIBLY ADD LOADING OF PRICE DATA HERE INSTEAD OF DISPLAYRATIOACTIVITY
		} else if(caller instanceof DividendScore) {
			if(data instanceof Stock[]) {

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
			Stock[] stocks = null;
			if(data instanceof Stock[]) {
				stocks = Arrays.copyOf(data, data.length, Stock[].class);				
			}
			((HotStocksSectionFragment) fragment).kinveyResponceMethod(stocks, view, scoreType);
		}
	}
}
