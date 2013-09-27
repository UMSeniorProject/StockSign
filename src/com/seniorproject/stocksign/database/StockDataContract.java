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
	 * To prevent accidentally instantiating the contract class,
     * give it an empty constructor.
	 */
     public StockDataContract() {}
     
     /** Inner class that defines the table contents */
     public static abstract class StockData implements BaseColumns {

    	 
         public static final String TABLE_NAME_STOCKS = "stocks";
         public static final String COLUMN_NAME_TICKER = "ticker";
         public static final String COLUMN_NAME_COMPANY = "company";
         public static final String COLUMN_NAME_SECTOR = "sector";
         public static final String COLUMN_NAME_INDUSTRY = "industry";
         public static final String COLUMN_NAME_COUNTRY = "country";
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
         public static final String COLUMN_NAME_salesgPast5Years = "salesgPast5Years";
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
         public static final String COLUMN_NAME_optionShort = "optionShort";
         public static final String COLUMN_NAME_rsi = "rsi";
         
         
         
         
     	// Database creation sql statement
     	//private static final String TAG = "MyMessage";
     	private static final String TEXT_TYPE = " TEXT";
     	private static final String REAL_TYPE = " REAL";
     	private static final String COMMA_SEP = ",";
     	public static final String DATABASE_CREATE = "CREATE TABLE " + StockData.TABLE_NAME_STOCKS 
     			+ " (" 
     			+ StockData._ID + " INTEGER NOT NULL PRIMARY KEY, " 
     			+ StockData.COLUMN_NAME_TICKER + TEXT_TYPE+ " NOT NULL UNIQUE, " 
     			+ StockData.COLUMN_NAME_COMPANY + TEXT_TYPE + COMMA_SEP 
     			+ StockData.COLUMN_NAME_SECTOR + TEXT_TYPE + COMMA_SEP 
     			+ StockData.COLUMN_NAME_INDUSTRY + TEXT_TYPE + COMMA_SEP 
     			+ StockData.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_PE + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_FORWARD_PE + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_PEG + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_PS + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_PB + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_PC + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_priceFreeCashFlow + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_epsgThisYear + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_epsgPast5Years + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_epsgNext5Years + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_salesgPast5Years + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_epsg + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_dividendYield + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_returnOnAssets + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_returnOnEquity + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_returnOnInvestment + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_currentRatio + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_quickRatio + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_ltDebtEquity + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_debtEquity + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_grossMargin + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_operatingMargin + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_netProfitMargin + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_payoutRatio + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_insiderOwnership + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_institutionalTransactions + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_floatShort + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_optionShort + REAL_TYPE + COMMA_SEP
     			+ StockData.COLUMN_NAME_rsi + REAL_TYPE 
     			
     			
     			
     			+") " ;
     	
     	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + StockData.TABLE_NAME_STOCKS;
     	
     	
         

         
     }

}
