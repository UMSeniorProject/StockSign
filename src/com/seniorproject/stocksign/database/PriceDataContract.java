package com.seniorproject.stocksign.database;

import com.seniorproject.stocksign.database.RatioDataContract.RatioData;

import android.provider.BaseColumns;

/**
 * Defines the ratio data table 
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public final class PriceDataContract {
	
	/**
	 * To prevent accidentally instantiating the contract class,
     * give it an empty constructor.
	 */
     public PriceDataContract() {}
     
     /** Inner class that defines the table contents */
     public static abstract class PriceData implements BaseColumns {

    	 
    	 public static  String TABLE_NAME_PRICEDATA = "PriceData";
         public static final String COLUMN_NAME_TICKER = "ticker";
         public static final String COLUMN_NAME_COMPANY = "company";
         public static final String COLUMN_NAME_SECTOR = "sector";
         public static final String COLUMN_NAME_INDUSTRY = "industry";
         public static final String COLUMN_NAME_COUNTRY = "country";
      
         public static final String COLUMN_NAME_DATE = "date";
         public static final String COLUMN_NAME_OPEN = "open";
         public static final String COLUMN_NAME_HIGH = "high";
         public static final String COLUMN_NAME_LOW = "low";
         public static final String COLUMN_NAME_CLOSE = "close";
         public static final String COLUMN_NAME_VOLUME = "volume";
         public static final String COLUMN_NAME_ADJCLOSE = "adjclose";
         
        
         
         
         
         
     	// Database creation sql statement
     	//private static final String TAG = "MyMessage";
     	private static final String TEXT_TYPE = " TEXT";
     	//private static final String REAL_TYPE = " REAL";
     	private static final String COMMA_SEP = ",";
     	public static final String DATABASE_CREATE = "CREATE TABLE " + PriceData.TABLE_NAME_PRICEDATA 
     			+ " ("  
     			+ PriceData.COLUMN_NAME_DATE + TEXT_TYPE + " NOT NULL, "
     			+ PriceData.COLUMN_NAME_TICKER + TEXT_TYPE+ COMMA_SEP
     			+ PriceData.COLUMN_NAME_COMPANY + TEXT_TYPE + COMMA_SEP 
     			+ PriceData.COLUMN_NAME_SECTOR + TEXT_TYPE + COMMA_SEP 
     			+ PriceData.COLUMN_NAME_INDUSTRY + TEXT_TYPE + COMMA_SEP 
     			+ PriceData.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP
     			+ PriceData.COLUMN_NAME_OPEN + TEXT_TYPE + COMMA_SEP
     			+ PriceData.COLUMN_NAME_HIGH + TEXT_TYPE + COMMA_SEP
     			+ PriceData.COLUMN_NAME_LOW + TEXT_TYPE + COMMA_SEP
     			+ PriceData.COLUMN_NAME_CLOSE + TEXT_TYPE + COMMA_SEP
     			+ PriceData.COLUMN_NAME_VOLUME + TEXT_TYPE + COMMA_SEP
     			+ PriceData.COLUMN_NAME_ADJCLOSE + TEXT_TYPE 
     			
     			+") " ;
     	
     	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PriceData.TABLE_NAME_PRICEDATA;
     	
     	
         

         
     }

}
