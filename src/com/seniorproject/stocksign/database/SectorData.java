package com.seniorproject.stocksign.database;

import java.util.ArrayList;

import com.seniorproject.stocksign.activity.ApplicationConstants;

public class SectorData {

	private static Stock[] sectorAvgs = null;
	public static ArrayList<String> goodSectorData = null;
	public static ArrayList<String> badSectorData = null;
	
	private static void setSectorData() {
		
		/*
		 * Green if Sector > Stock:
		 * 
		 * Price_to_Earnings
		 * Forward_Price_to_Earnings
		 * Price_to_Earnings_Growth
		 * Price_to_Sales
		 * Price_to_Book
		 * Price_to_Cash
		 * Price_to_Free_Cash_Flow
		 * Float_Short
		 * Beta
		 * Longterm_Debt_to_Equity
		 */
		goodSectorData = new ArrayList<String>();
		goodSectorData.add(ApplicationConstants.NAME_PE);
		goodSectorData.add(ApplicationConstants.NAME_FPE);
		goodSectorData.add(ApplicationConstants.NAME_PEG);
		goodSectorData.add(ApplicationConstants.NAME_PS);
		goodSectorData.add(ApplicationConstants.NAME_PB);
		goodSectorData.add(ApplicationConstants.NAME_PC);
		goodSectorData.add(ApplicationConstants.NAME_PFCF);
		goodSectorData.add(ApplicationConstants.NAME_FS);
		goodSectorData.add(ApplicationConstants.NAME_BETA);
		goodSectorData.add(ApplicationConstants.NAME_LONGTERM_DE);
		
		/*
		 * Green if Stock > Sector:
		 * 
		 * Dividend_Yield
		 * Return_On_Equity
		 * Return_On_Assets
		 * Payout_Ratio
		 * EPS_Growth_This_Year
		 * EPS_Growth_Next_Year
		 * EPS_Growth_Past_5_Years
		 * EPS_Growth_Next_5_Years
		 * Sales_Growth_Past_5_Years
		 * Insider_Ownership
		 * Insider_Transactions
		 * Institutional_Ownership
		 * Institutional_Transactions
		 * Profit_Margin
		 */
		badSectorData = new ArrayList<String>();
		badSectorData.add(ApplicationConstants.NAME_DIV_YIELD);
		badSectorData.add(ApplicationConstants.NAME_ROE);
		badSectorData.add(ApplicationConstants.NAME_ROA);
		badSectorData.add(ApplicationConstants.NAME_PR);
		badSectorData.add(ApplicationConstants.NAME_EPSG_NX_5_YR);
		badSectorData.add(ApplicationConstants.NAME_EPSG_NX_YR);
		badSectorData.add(ApplicationConstants.NAME_EPSG_PS_5_YR);
		badSectorData.add(ApplicationConstants.NAME_EPSG_TH_YR);
		badSectorData.add(ApplicationConstants.NAME_SG_PS_5_YR);
		badSectorData.add(ApplicationConstants.NAME_INSI_OWNER);
		badSectorData.add(ApplicationConstants.NAME_INSI_TRANS);
		badSectorData.add(ApplicationConstants.NAME_INST_OWNER);
		badSectorData.add(ApplicationConstants.NAME_INST_TRANS);
		badSectorData.add(ApplicationConstants.NAME_PM);
	}
	
	public static void setData(Stock[] sectorData) {
		if(sectorData != null) {
			sectorAvgs = sectorData;
			setSectorData();
		} else {
			//ERROR
		}
	}
	
	public static Stock[] getAllSectorAvgs() {
		return sectorAvgs;
	}
	
	public static Stock getSectorAvgs(String sectorName) {
		if(sectorAvgs != null && sectorName != null) {
			for(Stock stock : sectorAvgs) {
				if(stock.getTicker().equals(sectorName)) {
					return stock;
				}
			}
		}
		return null;
	}
}
