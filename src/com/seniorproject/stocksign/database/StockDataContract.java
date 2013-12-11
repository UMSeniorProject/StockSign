package com.seniorproject.stocksign.database;

import android.provider.BaseColumns;

/**
 * Defines the database schema
 * 
 * @author Sean Wilkinson
 * @since 1.0
 * 
 */
public final class StockDataContract {

	/**
	 * To prevent accidentally instantiating the contract class, give it an
	 * empty constructor.
	 */
	public StockDataContract() {
	}

	/** Inner class that defines the table contents */
	public static abstract class StockData implements BaseColumns {

		public static final String TABLE_NAME_STOCKS = "stocks";
		public static final String COLUMN_NAME_TICKER = "ticker";
		public static final String COLUMN_NAME_COMPANY = "company";
		public static final String COLUMN_NAME_SECTOR = "sector";
		public static final String COLUMN_NAME_INDUSTRY = "industry";
		public static final String COLUMN_NAME_COUNTRY = "country";

		// back when we had the price data in the database
		/*
		 * public static final String COLUMN_NAME_DATE = "date"; public static
		 * final String COLUMN_NAME_OPEN = "open"; public static final String
		 * COLUMN_NAME_HIGH = "high"; public static final String COLUMN_NAME_LOW
		 * = "low"; public static final String COLUMN_NAME_CLOSE = "close";
		 * public static final String COLUMN_NAME_VOLUME = "volume"; public
		 * static final String COLUMN_NAME_ADJCLOSE = "adjclose";
		 */

		public static final String COLUMN_NAME_PE = "pe";
		public static final String COLUMN_NAME_FORWARD_PE = "forward_pe";
		public static final String COLUMN_NAME_PEG = "peg";
		public static final String COLUMN_NAME_PS = "ps";
		public static final String COLUMN_NAME_PB = "pb";
		public static final String COLUMN_NAME_PC = "pc";
		public static final String COLUMN_NAME_priceFreeCashFlow = "priceFreeCashFlow";
		public static final String COLUMN_NAME_epsgThisYear = "epsgThisYear";
		public static final String COLUMN_NAME_epsgPast5Years = "epsgPast5Years";
		public static final String COLUMN_NAME_epsgNext5Years = "epsgNext5Years";
		public static final String COLUMN_NAME_epsgNextYear = "epsgNextYear";
		public static final String COLUMN_NAME_salesgPast5Years = "salesgPast5Years";
		public static final String COLUMN_NAME_sharesFloat = "sharesfloat";//
		public static final String COLUMN_NAME_epsg = "epsg";
		public static final String COLUMN_NAME_salesg = "salesg";
		public static final String COLUMN_NAME_dividendYield = "dividendYield";
		public static final String COLUMN_NAME_returnOnAssets = "returnOnAssets";
		public static final String COLUMN_NAME_returnOnEquity = "returnOnEquity";
		public static final String COLUMN_NAME_returnOnInvestment = "returnOnInvestment";
		public static final String COLUMN_NAME_currentRatio = "currentRatio";
		public static final String COLUMN_NAME_quickRatio = "quickRatio";
		public static final String COLUMN_NAME_ltDebtEquity = "ltDebtEquity";
		public static final String COLUMN_NAME_debtEquity = "debtEquity";
		public static final String COLUMN_NAME_grossMargin = "grossMargin";
		public static final String COLUMN_NAME_operatingMargin = "operatingMargin";
		public static final String COLUMN_NAME_netProfitMargin = "netProfitMargin";
		public static final String COLUMN_NAME_payoutRatio = "payoutRatio";
		public static final String COLUMN_NAME_insiderOwnership = "insiderOwnership";
		public static final String COLUMN_NAME_institutionalTransactions = "institutionalTransactions";
		public static final String COLUMN_NAME_floatShort = "floatShort";
		public static final String COLUMN_NAME_shortRatio = "shortRatio";
		public static final String COLUMN_NAME_rsi = "rsi";
		public static final String COLUMN_NAME_beta = "beta";
		public static final String COLUMN_NAME_volatilityWeek = "volatilityWeek";//
		public static final String COLUMN_NAME_volatilityMonth = "volatilityMonth";//
		public static final String COLUMN_NAME_averageVolume = "averageVolume";//
		public static final String COLUMN_NAME_relativeVolume = "relativeVolume";//
		public static final String COLUMN_NAME_totalScore = "totalScore";//
		public static final String COLUMN_NAME_dividendScore = "dividendScore";//
		public static final String COLUMN_NAME_growthScore = "growthScore";//

		// Database creation sql statement
		// private static final String TAG = "MyMessage";
		private static final String TEXT_TYPE = " TEXT";
		private static final String REAL_TYPE = " REAL";
		private static final String COMMA_SEP = ",";
		public static final String DATABASE_CREATE = "CREATE TABLE "
				+ StockData.TABLE_NAME_STOCKS + " (" + StockData._ID
				+ " INTEGER NOT NULL PRIMARY KEY, "
				+ StockData.COLUMN_NAME_TICKER + TEXT_TYPE
				+ " NOT NULL UNIQUE, " + StockData.COLUMN_NAME_COMPANY
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_SECTOR
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_INDUSTRY
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_COUNTRY
				+ TEXT_TYPE + COMMA_SEP
				/*
				 * + StockData.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
				 * StockData.COLUMN_NAME_OPEN + TEXT_TYPE + COMMA_SEP +
				 * StockData.COLUMN_NAME_HIGH + TEXT_TYPE + COMMA_SEP +
				 * StockData.COLUMN_NAME_LOW + TEXT_TYPE + COMMA_SEP +
				 * StockData.COLUMN_NAME_CLOSE + TEXT_TYPE + COMMA_SEP +
				 * StockData.COLUMN_NAME_VOLUME + TEXT_TYPE + COMMA_SEP +
				 * StockData.COLUMN_NAME_ADJCLOSE + TEXT_TYPE + COMMA_SEP
				 */
				+ StockData.COLUMN_NAME_PE + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_FORWARD_PE + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_PEG + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_PS + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_PB + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_PC + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_priceFreeCashFlow + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_epsgThisYear + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_epsgPast5Years + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_epsgNext5Years + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_salesgPast5Years
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_sharesFloat
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_epsg
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_dividendYield
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_returnOnAssets
				+ TEXT_TYPE + COMMA_SEP + StockData.COLUMN_NAME_returnOnEquity
				+ TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_returnOnInvestment + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_currentRatio + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_quickRatio + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_ltDebtEquity + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_debtEquity + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_grossMargin + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_operatingMargin + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_netProfitMargin + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_payoutRatio + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_insiderOwnership
				+ TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_institutionalTransactions + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_floatShort + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_shortRatio + TEXT_TYPE
				+ COMMA_SEP + StockData.COLUMN_NAME_rsi + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_beta + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_volatilityWeek + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_volatilityMonth + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_averageVolume + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_relativeVolume + TEXT_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_totalScore + REAL_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_dividendScore + REAL_TYPE + COMMA_SEP
				+ StockData.COLUMN_NAME_growthScore + REAL_TYPE

				+ ") ";

		public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
				+ StockData.TABLE_NAME_STOCKS;

	}

	public static void DropTables() {

	}

}
