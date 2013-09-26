package com.seniorproject.stocksign.database;

import java.util.ArrayList;
import java.util.List;

import com.seniorproject.stocksign.database.StockDataContract.StockData;

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
			StockData.COLUMN_NAME_STOCK_ID, 
			StockData.COLUMN_NAME_SECTOR,
			StockData.COLUMN_NAME_INDUSTRY,
			StockData.COLUMN_NAME_COUNTRY,
			StockData.COLUMN_NAME_PE,
			StockData.COLUMN_NAME_FORWARD_PE,
			StockData.COLUMN_NAME_PS};

	public StockDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
		  
	public Stock createStock(String stock) {
		ContentValues values = new ContentValues();
		values.put(StockData.COLUMN_NAME_STOCK_ID, stock);
		long insertId = database.insert(StockData.TABLE_NAME_STOCKS, null,
				values);
		Cursor cursor = database.query(StockData.TABLE_NAME_STOCKS,
				allColumns, StockData._ID + " = " + insertId, null,
				null, null, null);
			    cursor.moveToFirst();
			    Stock newStock = cursorToStock(cursor);
			    cursor.close();
			    
			    return newStock;
	}
	
	public void deleteStock(Stock stock) {
		String id = stock.getStockid();
	    System.out.println("Stock deleted with id: " + id);
	    database.delete(StockData.TABLE_NAME_STOCKS, StockData._ID
	    		+ " = " + id, null);
	}
	
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
	  
	private Stock cursorToStock(Cursor cursor) {
		Stock stock = new Stock();
		stock.setId(cursor.getInt(0));
		stock.setStockid(cursor.getString(1));
		return stock;
			  }
}
