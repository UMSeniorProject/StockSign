package com.seniorproject.stocksign.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.seniorproject.stocksign.database.StockDataContract.StockData;


public class MySQLiteHelper extends SQLiteOpenHelper {




	// Database creation sql statement
	private static final String TAG = "MyMessage";
	private static final String TEXT_TYPE = " TEXT";
	private static final String REAL_TYPE = " REAL";
	private static final String COMMA_SEP = ",";
	private static final String DATABASE_CREATE = "CREATE TABLE " 
			+ StockData.TABLE_NAME_STOCKS 
			+ " (" + StockData._ID + " INTEGER PRIMARY KEY, " 
			+ StockData.COLUMN_NAME_STOCK_ID + TEXT_TYPE ;
	
	//System.out.println(DATABASE_CREATE);
	
	
	//create table stock_data (_id integer primary key autoincrement, comment text not null);
  
	public MySQLiteHelper(Context context) {
		super(context, StockData.getDatabaseName(), null, StockData.getDatabaseVersion());
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + StockData.TABLE_NAME_STOCKS);
		onCreate(db);
	}
	
	public final static class Debugme{
	    private Debugme (){}

	    public static  void out (){
	        Log.i(TAG, DATABASE_CREATE);
	    }
	}

} 