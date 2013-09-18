package com.seniorproject.stocksign.database;

import android.provider.BaseColumns;

// android.database.sqlite;

public final class StockDataContract {
	
	/*
	 * To prevent someone from accidentally instantiating the contract class,
     * give it an empty constructor.
	 */
     public StockDataContract() {}
     
     /* Inner class that defines the table contents */
     public static abstract class StockData implements BaseColumns {
    	 private static final String DATABASE_NAME = "stockdata.db";
    	 private static final int DATABASE_VERSION = 1;
    	 
         public static final String TABLE_NAME_STOCKS = "stocks";
         public static final String COLUMN_NAME_STOCK_ID = "stockid";
         public static final String COLUMN_NAME_STOCK_TITLE = "stocktitle";
         public static final String COLUMN_NAME_SECTOR = "sector";
         public static final String COLUMN_NAME_INDUSTRY = "industry";
         public static final String COLUMN_NAME_COUNTRY = "country";
         public static final String COLUMN_NAME_PE = "pe";
         public static final String COLUMN_NAME_FORWARD_PE = "forward_pe";
         
		public static String getDatabaseName() {
			return DATABASE_NAME;
		}
		public static int getDatabaseVersion() {
			return DATABASE_VERSION;
		}
         
     }

}
