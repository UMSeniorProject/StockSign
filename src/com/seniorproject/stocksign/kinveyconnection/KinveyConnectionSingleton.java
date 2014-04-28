package com.seniorproject.stocksign.kinveyconnection;

import android.app.Activity;
import android.content.Context;

import com.kinvey.android.Client;
import com.seniorproject.stocksign.activity.ActivityConstants;

//Singleton class to represent a single connection to Kinvey throughout the application
public class KinveyConnectionSingleton implements ActivityConstants {

	private static KinveyConnectionSingleton instance = null;
	private static Client mKinveyClient = null;
	private static boolean connected = false;
	
	protected KinveyConnectionSingleton(Context applicationContext) {
		mKinveyClient = new Client.Builder(applicationContext).build();
	}
	
	//only the MainActivity can set the instance
	public static boolean setInstance(Activity connectingActivity, int activityID) {
		synchronized(KinveyConnectionSingleton.class) {
			if(instance == null && activityID == ActivityConstants.MainActivity) {
				instance = new KinveyConnectionSingleton(connectingActivity.getApplicationContext());
				return true;
			}
			else return false;
		}
	}
	
	//thread safe
	public static synchronized KinveyConnectionSingleton getInstance() {
		return instance;
	}
	
	//thread safe
	public static synchronized Client getKinveyClient() {
		return mKinveyClient;
	}
	
	public static boolean isConnected() {
		return connected;
	}
	
	public static void setConnected() {
		connected = true;
	}
	
	public static void setDisconnected() {
		connected = false;
	}
	
}
