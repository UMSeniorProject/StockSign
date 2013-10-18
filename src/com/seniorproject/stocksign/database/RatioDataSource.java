package com.seniorproject.stocksign.database;

import java.util.ArrayList;
import java.util.List;

import com.seniorproject.stocksign.database.RatioDataContract.RatioData;
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
public class RatioDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { RatioData._ID,
			RatioData.COLUMN_NAME_TICKER,
			RatioData.COLUMN_NAME_COMPANY,
			RatioData.COLUMN_NAME_SECTOR,
			RatioData.COLUMN_NAME_INDUSTRY,
			RatioData.COLUMN_NAME_COUNTRY,
			RatioData.COLUMN_NAME_PE,
			RatioData.COLUMN_NAME_FORWARD_PE,
			RatioData.COLUMN_NAME_PS,
			RatioData.COLUMN_NAME_PB,
			RatioData.COLUMN_NAME_PC,
			RatioData.COLUMN_NAME_priceFreeCashFlow,
			RatioData.COLUMN_NAME_epsgThisYear,
			RatioData.COLUMN_NAME_epsgPast5Years,
			RatioData.COLUMN_NAME_epsgNext5Years,
			RatioData.COLUMN_NAME_salesgPast5Years,
			RatioData.COLUMN_NAME_epsg,
			RatioData.COLUMN_NAME_dividendYield,
			RatioData.COLUMN_NAME_returnOnAssets,
			RatioData.COLUMN_NAME_returnOnEquity,
			RatioData.COLUMN_NAME_returnOnInvestment,
			RatioData.COLUMN_NAME_currentRatio,
			RatioData.COLUMN_NAME_quickRatio,
			RatioData.COLUMN_NAME_ltDebtEquity,
			RatioData.COLUMN_NAME_debtEquity,
			RatioData.COLUMN_NAME_grossMargin,
			RatioData.COLUMN_NAME_operatingMargin,
			RatioData.COLUMN_NAME_netProfitMargin,
			RatioData.COLUMN_NAME_payoutRatio,
			RatioData.COLUMN_NAME_insiderOwnership,
			RatioData.COLUMN_NAME_institutionalTransactions,
			RatioData.COLUMN_NAME_floatShort,
			RatioData.COLUMN_NAME_shortRatio,
			RatioData.COLUMN_NAME_rsi};

	
	/**
	 * Constructor
	 * @param context
	 */
	public RatioDataSource(Context context) {
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
		values.put(RatioData.COLUMN_NAME_TICKER, ticker);
		long insertId = database.insert(RatioData.TABLE_NAME_RATIODATA, null,
				values);
		
		if(insertId==-1)
		{
			Debugger.error("CreateStock", "DATABASE INSERT FAILED");
		}
		else
			Debugger.info("CreateStock", ticker + " has been added to database");
		
		Cursor cursor = database.query(RatioData.TABLE_NAME_RATIODATA,
				allColumns, RatioData._ID + " = " + insertId, null,
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
		
		long insertId = database.insert(RatioData.TABLE_NAME_RATIODATA, null,
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
		v.put(RatioData.COLUMN_NAME_TICKER, stock.getTicker());
		v.put(RatioData.COLUMN_NAME_COMPANY, stock.getCompany());
		v.put(RatioData.COLUMN_NAME_SECTOR, stock.getSector());
		v.put(RatioData.COLUMN_NAME_INDUSTRY, stock.getIndustry());
		v.put(RatioData.COLUMN_NAME_COUNTRY, stock.getCountry());
		v.put(RatioData.COLUMN_NAME_PE, stock.getPe());
		v.put(RatioData.COLUMN_NAME_FORWARD_PE, stock.getForward_pe());
		v.put(RatioData.COLUMN_NAME_PEG, stock.getPeg());
		v.put(RatioData.COLUMN_NAME_PS, stock.getPs());
		v.put(RatioData.COLUMN_NAME_PB, stock.getPeg());
		v.put(RatioData.COLUMN_NAME_PC, stock.getPc());
		v.put(RatioData.COLUMN_NAME_priceFreeCashFlow, stock.getPriceFreeCashFlow());
		v.put(RatioData.COLUMN_NAME_epsgThisYear, stock.getEpsgThisYear());
		v.put(RatioData.COLUMN_NAME_epsgPast5Years, stock.getEpsgPast5Years());
		v.put(RatioData.COLUMN_NAME_epsgNext5Years, stock.getEpsgNext5Years());
		v.put(RatioData.COLUMN_NAME_epsgNext5Years, stock.getEpsgNext5Years());
		v.put(RatioData.COLUMN_NAME_salesgPast5Years, stock.getSalesgPast5Years());
		v.put(RatioData.COLUMN_NAME_epsg, stock.getEpsg());
		v.put(RatioData.COLUMN_NAME_dividendYield, stock.getDividendYield());
		v.put(RatioData.COLUMN_NAME_returnOnAssets, stock.getReturnOnAssets());
		v.put(RatioData.COLUMN_NAME_returnOnEquity, stock.getReturnOnEquity());
		v.put(RatioData.COLUMN_NAME_returnOnInvestment, stock.getReturnOnInvestment());
		v.put(RatioData.COLUMN_NAME_currentRatio, stock.getCurrentRatio());
		v.put(RatioData.COLUMN_NAME_quickRatio, stock.getQuickRatio());
		v.put(RatioData.COLUMN_NAME_ltDebtEquity, stock.getLtDebtEquity());
		v.put(RatioData.COLUMN_NAME_debtEquity, stock.getDebtEquity());
		v.put(RatioData.COLUMN_NAME_grossMargin, stock.getGrossMargin());
		v.put(RatioData.COLUMN_NAME_operatingMargin, stock.getOperatingMargin());
		v.put(RatioData.COLUMN_NAME_netProfitMargin, stock.getNetProfitMargin());
		v.put(RatioData.COLUMN_NAME_payoutRatio, stock.getPayoutRatio());
		v.put(RatioData.COLUMN_NAME_insiderOwnership, stock.getInsiderOwnership());
		v.put(RatioData.COLUMN_NAME_institutionalTransactions, stock.getInstitutionalTransactions());
		v.put(RatioData.COLUMN_NAME_floatShort, stock.getFloatShort());
		v.put(RatioData.COLUMN_NAME_shortRatio, stock.getShortRatio());
		v.put(RatioData.COLUMN_NAME_rsi, stock.getRsi());
		
		
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
	    Debugger.info("DeleteStock", "Stock deleted with "+RatioData.COLUMN_NAME_TICKER+": " + stock.getTicker());
	    database.delete(RatioData.TABLE_NAME_RATIODATA,RatioData._ID + " = " + id , null);
	    
	}
	/**
	 * Deletes all rows of the Stock table
	 */
	public void deleteAllStocks(){
		database.delete(RatioData.TABLE_NAME_RATIODATA,null , null);
		Debugger.info("DeleteAllStocks","All rows have been deleted");
	}
	/**
	 * Creates a list of stocks in database
	 * @return stocks A list of stocks
	 */
	public List<Stock> getAllStocks() {
		List<Stock> stocks = new ArrayList<Stock>();

		Cursor cursor = database.query(RatioData.TABLE_NAME_RATIODATA,
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
