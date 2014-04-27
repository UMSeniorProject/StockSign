package com.seniorproject.stocksign.graphing;

public class IndicatorInfo {
	private String name;
	private float[] positives;
	private float[] negatives;

	// TODO: split values into positive and negative

	private int id;
	private boolean checked;
	private int color;

	public IndicatorInfo(String name, int id, boolean checked, int size) {
		this.name = name;
		this.positives = new float[size];
		this.negatives = new float[size];
		this.id = id;
		this.checked = checked;
	}

	public void addValue(float value, int index) {
		if (index < positives.length) {
			if (value < 0) {
				negatives[index] = value;
				positives[index] = 0;
			} else {
				negatives[index] = 0;
				positives[index] = value;
			}
		}
	}

	public String getName() {
		return name;
	}

	public float[] getPositives() {
		return positives;
	}

	public float[] getNegatives() {
		return negatives;
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
