package com.seniorproject.stocksign.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.seniorproject.stocksign.database.StockDataContract.StockData;

/**
 * This class is responsible for creating the database. 
 * The {@link onUpgrade()} method will simply delete all existing 
 * data and re-create the table. It also defines several 
 * constants for the table name and the table columns.
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	 private static final String DATABASE_NAME = "stockdata.db";
	 private static final int DATABASE_VERSION = 1;



  
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(StockData.DATABASE_CREATE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL(StockData.SQL_DELETE_ENTRIES + StockData.TABLE_NAME_STOCKS);
		onCreate(db);
	}
	
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
	
	//MySQLiteHelper mDbHelper = new MySQLiteHelper(getContext());


} 