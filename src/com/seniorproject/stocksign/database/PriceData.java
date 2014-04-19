package com.seniorproject.stocksign.database;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

public class PriceData extends GenericJson {

	@Key("_id")
	private String _id;
	@Key("Date")
	private int date;
	
	@Key("Open")
	private float open;
	@Key("Close")
	private float close;
	@Key("High")
	private float high;
	@Key("Low")
	private float low;
	@Key("Vortex")
	private float vortex;
	@Key("CMFInd")
	private float cmfInd;
	@Key("MasterInd")
	private float masterInd;
	@Key("MACDslope")
	private float macdSlope;
	@Key("MACDsum")
	private float macdSum;
	
	@Key("_kmd")
	private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
	@Key("_acl")
	private KinveyMetaData.AccessControlList acl; // Kinvey access control,
													// OPTIONAL
	
	public PriceData() { 
	}

	public String get_id() {
		return _id;
	}

	public int getDate() {
		return date;
	}

	public float getOpen() {
		return open;
	}

	public float getClose() {
		return close;
	}

	public float getHigh() {
		return high;
	}

	public float getLow() {
		return low;
	}

	public float getVortex() {
		return vortex;
	}

	public float getCmfInd() {
		return cmfInd;
	}

	public float getMasterInd() {
		return masterInd;
	}

	public float getMacdSlope() {
		return macdSlope;
	}

	public float getMacdSum() {
		return macdSum;
	}

	public KinveyMetaData getMeta() {
		return meta;
	}

	public KinveyMetaData.AccessControlList getAcl() {
		return acl;
	}
	 

}
