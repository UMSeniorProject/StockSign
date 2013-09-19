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

    	 
         public static final String TABLE_NAME_STOCKS = "stocks";
         public static final String COLUMN_NAME_STOCK_ID = "stockid";
         public static final String COLUMN_NAME_STOCK_TITLE = "stocktitle";
         public static final String COLUMN_NAME_SECTOR = "sector";
         public static final String COLUMN_NAME_INDUSTRY = "industry";
         public static final String COLUMN_NAME_COUNTRY = "country";
         public static final String COLUMN_NAME_PE = "pe";
         public static final String COLUMN_NAME_FORWARD_PE = "forward_pe";
         
         
     	// Database creation sql statement
     	//private static final String TAG = "MyMessage";
     	private static final String TEXT_TYPE = " TEXT";
     	//private static final String REAL_TYPE = " REAL";
     	//private static final String COMMA_SEP = ",";
     	public static final String DATABASE_CREATE = "CREATE TABLE " + StockData.TABLE_NAME_STOCKS 
     			+ " (" + StockData._ID + " INTEGER PRIMARY KEY, " 
     			+ StockData.COLUMN_NAME_STOCK_ID + TEXT_TYPE + ") " ;
     	
     	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + StockData.TABLE_NAME_STOCKS;
     	
     	
         

         
     }

}
