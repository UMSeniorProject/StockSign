package com.seniorproject.stocksign.database;

import java.util.ArrayList;
import java.util.List;

import com.seniorproject.stocksign.database.PriceDataContract.PriceData;

import com.seniorproject.stocksign.debugging.Debugger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data Access Object that maintains the database connection 
 * and supports adding new ratiodata and fetching all ratiodata.
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class PriceDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { };

	
	/**
	 * Constructor
	 * @param context
	 */
	public PriceDataSource(Context context) {
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
	/**
	 * Create stock with specified ticker 
	 * @deprecated
	 * 
	 * @param ticker
	 *
	 * @return stock
	 * 			The stock that was created
	 */
	public Stock createStock(String ticker){
		ContentValues values = new ContentValues();
		values.put(PriceData.COLUMN_NAME_TICKER, ticker);
		long insertId = database.insert(PriceData.TABLE_NAME_PRICEDATA, null,
				values);
		
		if(insertId==-1)
		{
			Debugger.error("CreateStock", "DATABASE INSERT FAILED");
		}
		else
			Debugger.info("CreateStock", ticker + " has been added to database");
		
		Cursor cursor = database.query(PriceData.TABLE_NAME_PRICEDATA,
				allColumns, PriceData._ID + " = " + insertId, null,
				null, null, null);
			    cursor.moveToFirst();
			    Stock newStock = cursorToStock(cursor);
			    cursor.close();
			    
			    return newStock;
	}
	
	/**
	 * Create a new row in database with a new stock	  
	 * @param stock
	 * 			The stock to be added into the db
	 * 
	 */
	public void createStock(Stock stock){
		ContentValues values = new ContentValues();
		
		values = loadValues(values, stock);
		
		long insertId = database.insert(PriceData.TABLE_NAME_PRICEDATA, null,
				values);
		//If insert fails it returns a -1
		if(insertId==-1)
		{
			Debugger.error("CreateStock", "DATABASE INSERT FAILED: " + stock.getTicker());
		}
		//else
			//Debugger.info("CreateStock", stock.getTicker() + " has been added to database");
		
		
	}
	
	/**
	 *Load values of new stock into table variables
	 * @param v The ContentValues object the values will be added to
	 * @param stock The stock that contains the data to be added
	 * @return v The ContentValues loaded with the stocks data
	 */
	public ContentValues loadValues(ContentValues v, Stock stock){
		v.put(PriceData.COLUMN_NAME_TICKER, stock.getTicker());
		v.put(PriceData.COLUMN_NAME_COMPANY, stock.getCompany());
		v.put(PriceData.COLUMN_NAME_SECTOR, stock.getSector());
		v.put(PriceData.COLUMN_NAME_INDUSTRY, stock.getIndustry());
		v.put(PriceData.COLUMN_NAME_COUNTRY, stock.getCountry());
		//fix
		v.put(PriceData.COLUMN_NAME_DATE, stock.getDate());
		v.put(PriceData.COLUMN_NAME_OPEN, stock.getOpen());
		v.put(PriceData.COLUMN_NAME_HIGH, stock.getHigh());
		v.put(PriceData.COLUMN_NAME_LOW, stock.getLow());
		v.put(PriceData.COLUMN_NAME_CLOSE, stock.getClose());
		v.put(PriceData.COLUMN_NAME_VOLUME, stock.getVolume());
		v.put(PriceData.COLUMN_NAME_ADJCLOSE, stock.getAdjclose());
		
		
		
		
		return v;
	}
	public void modifyStock(String stockid){
		
	}
	
	/**
	 * Deletes the specified stock
	 * @param stock The stock to be deleted
	 */
	public void deleteStock(Stock stock) {
		int id = stock.getId();
	    Debugger.info("DeleteStock", "Stock deleted with "+PriceData.COLUMN_NAME_TICKER+": " + stock.getTicker());
	    database.delete(PriceData.TABLE_NAME_PRICEDATA,PriceData._ID + " = " + id , null);
	    
	}
	/**
	 * Deletes all rows of the Stock table
	 */
	public void deleteAllStocks(){
		database.delete(PriceData.TABLE_NAME_PRICEDATA,null , null);
		Debugger.info("DeleteAllStocks","All rows have been deleted");
	}
	/**
	 * Creates a list of stocks in database
	 * @return stocks A list of stocks
	 */
	public List<Stock> getAllStocks() {
		List<Stock> stocks = new ArrayList<Stock>();

		Cursor cursor = database.query(PriceData.TABLE_NAME_PRICEDATA,
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
