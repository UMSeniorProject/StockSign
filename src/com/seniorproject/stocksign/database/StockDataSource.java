package com.seniorproject.stocksign.database;

import java.util.ArrayList;
import java.util.List;

import com.seniorproject.stocksign.database.StockDataContract.StockData;
import com.seniorproject.stocksign.debugging.Debugger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object that maintains the database connection 
 * and supports adding new stocks and fetching all stocks.
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class StockDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { StockData._ID,
			StockData.COLUMN_NAME_TICKER, 
			StockData.COLUMN_NAME_SECTOR,
			StockData.COLUMN_NAME_INDUSTRY,
			StockData.COLUMN_NAME_COUNTRY,
			StockData.COLUMN_NAME_PE,
			StockData.COLUMN_NAME_FORWARD_PE,
			StockData.COLUMN_NAME_PS,
			StockData.COLUMN_NAME_PB,
			StockData.COLUMN_NAME_PC,
			StockData.COLUMN_NAME_priceFreeCashFlow,
			StockData.COLUMN_NAME_epsgThisYear,
			StockData.COLUMN_NAME_epsgPast5Years,
			StockData.COLUMN_NAME_epsgNext5Years,
			StockData.COLUMN_NAME_salesgPast5Years,
			StockData.COLUMN_NAME_epsg,
			StockData.COLUMN_NAME_dividendYield,
			StockData.COLUMN_NAME_returnOnAssets,
			StockData.COLUMN_NAME_returnOnEquity,
			StockData.COLUMN_NAME_returnOnInvestment,
			StockData.COLUMN_NAME_currentRatio,
			StockData.COLUMN_NAME_quickRatio,
			StockData.COLUMN_NAME_ltDebtEquity,
			StockData.COLUMN_NAME_debtEquity,
			StockData.COLUMN_NAME_grossMargin,
			StockData.COLUMN_NAME_operatingMargin,
			StockData.COLUMN_NAME_netProfitMargin,
			StockData.COLUMN_NAME_payoutRatio,
			StockData.COLUMN_NAME_insiderOwnership,
			StockData.COLUMN_NAME_institutionalTransactions,
			StockData.COLUMN_NAME_floatShort,
			StockData.COLUMN_NAME_optionShort,
			StockData.COLUMN_NAME_rsi};

	
	/**
	 * Constructor
	 * @param context
	 */
	public StockDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	/**
	 * Gets a writable database
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Closes database connection
	 */
	public void close() {
		dbHelper.close();
	}
	
	public Stock createStock(String ticker){
		ContentValues values = new ContentValues();
		values.put(StockData.COLUMN_NAME_TICKER, ticker);
		long insertId = database.insert(StockData.TABLE_NAME_STOCKS, null,
				values);
		
		if(insertId==-1)
		{
			Debugger.error("CreateStock", "DATABASE INSERT FAILED");
		}
		else
			Debugger.info("CreateStock", ticker + " has been added to database");
		
		Cursor cursor = database.query(StockData.TABLE_NAME_STOCKS,
				allColumns, StockData._ID + " = " + insertId, null,
				null, null, null);
			    cursor.moveToFirst();
			    Stock newStock = cursorToStock(cursor);
			    cursor.close();
			    
			    return newStock;
	}
	
	/**
	 * Create a new row in database with a new stock	  
	 * @param stock
	 * 
	 */
	public void createStock(Stock stock){
		ContentValues values = new ContentValues();
		
		values = loadValues(values, stock);
		
		long insertId = database.insert(StockData.TABLE_NAME_STOCKS, null,
				values);
		//If insert fails it returns a -1
		if(insertId==-1)
		{
			Debugger.error("CreateStock", "DATABASE INSERT FAILED: " + stock.getTicker());
		}
		else
			Debugger.info("CreateStock", StockData.COLUMN_NAME_TICKER + " has been added to database");
		
		
	}
	
	/**
	 *Load values of new stock into table variables
	 * @param v
	 * @param stock
	 * @return v
	 */
	public ContentValues loadValues(ContentValues v, Stock stock){
		v.put(StockData.COLUMN_NAME_TICKER, stock.getTicker());
		v.put(StockData.COLUMN_NAME_COMPANY, stock.getCompany());
		v.put(StockData.COLUMN_NAME_SECTOR, stock.getSector());
		v.put(StockData.COLUMN_NAME_INDUSTRY, stock.getIndustry());
		v.put(StockData.COLUMN_NAME_COUNTRY, stock.getCountry());
		v.put(StockData.COLUMN_NAME_PE, stock.getPe());
		v.put(StockData.COLUMN_NAME_FORWARD_PE, stock.getForward_pe());
		v.put(StockData.COLUMN_NAME_PEG, stock.getPeg());
		
		return v;
	}
	public void modifyStock(String stockid){
		
	}
	
	/**
	 * Deletes the specified stock
	 * @param stock
	 */
	public void deleteStock(Stock stock) {
		int id = stock.getId();
	    Debugger.info("DeleteStock", "Stock deleted with "+StockData.COLUMN_NAME_TICKER+": " + stock.getTicker());
	    database.delete(StockData.TABLE_NAME_STOCKS,StockData._ID + " = " + id , null);
	    
	}
	/**
	 * Deletes all rows of the Stock table
	 */
	public void deleteAllStocks(){
		database.delete(StockData.TABLE_NAME_STOCKS,null , null);
		Debugger.info("DeleteAllStocks","All rows have been deleted");
	}
	/**
	 * Creates a list of stocks in database
	 * @return stocks A list of stocks
	 */
	public List<Stock> getAllStocks() {
		List<Stock> stocks = new ArrayList<Stock>();

		Cursor cursor = database.query(StockData.TABLE_NAME_STOCKS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Stock stock = cursorToStock(cursor);
			stocks.add(stock);
			cursor.moveToNext();
		}
		    // Make sure to close the cursor
		cursor.close();
		return stocks;
	}
	/**
	 * Move cursor to a stock  
	 * @param cursor
	 * @return stock
	 */
	private Stock cursorToStock(Cursor cursor) {
		Stock stock = new Stock();
		stock.setId(cursor.getInt(0));
		stock.setTicker(cursor.getString(1));
		return stock;
	}
}
