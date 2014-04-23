package com.seniorproject.stocksign.graphing;

public class IndicatorInfo {
	private String name;
	private float[] values;
	
	//TODO: split values into positive and negative
	
	private int id;
	private boolean checked;
	private int color;

	public IndicatorInfo(String name, float[] values, int id, boolean checked) {
		this.name = name;
		this.values = values;
		this.id = id;
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public float[] getValues() {
		return values;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return name;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked() {
		this.checked = true;
	}
	
	public void setUnchecked() {
		this.checked = false;
	}
	
	public void setCheckedValue(boolean checked) {
		this.checked = checked;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
}
