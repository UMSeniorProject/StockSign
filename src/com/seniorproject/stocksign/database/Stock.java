package com.seniorproject.stocksign.database;

public class Stock {
	
	private int _id;
	private String stockid;
	private String stocktitle;
	private String sector;
	private String indutry;
	private String country;
	private float pe;
	private float forward_pe;
	private float peg;
	
	/**
	 * @return the _id
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * @param _id the _id to set
	 */
	public void setId(int _id) {
		this._id = _id;
	}

	/**
	 * @return the stockid
	 */
	public String getStockid() {
		return stockid;
	}

	/**
	 * @param string the stockid to set
	 */
	public void setStockid(String string) {
		this.stockid = string;
	}

	/**
	 * @return the stocktitle
	 */
	public String getStocktitle() {
		return stocktitle;
	}

	/**
	 * @param stocktitle the stocktitle to set
	 */
	public void setStocktitle(String stocktitle) {
		this.stocktitle = stocktitle;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the indutry
	 */
	public String getIndutry() {
		return indutry;
	}

	/**
	 * @param indutry the indutry to set
	 */
	public void setIndutry(String indutry) {
		this.indutry = indutry;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the pe
	 */
	public float getPe() {
		return pe;
	}

	/**
	 * @param pe the pe to set
	 */
	public void setPe(float pe) {
		this.pe = pe;
	}

	/**
	 * @return the forward_pe
	 */
	public float getForward_pe() {
		return forward_pe;
	}

	/**
	 * @param forward_pe the forward_pe to set
	 */
	public void setForward_pe(float forward_pe) {
		this.forward_pe = forward_pe;
	}

	/**
	 * @return the peg
	 */
	public float getPeg() {
		return peg;
	}

	/**
	 * @param peg the peg to set
	 */
	public void setPeg(float peg) {
		this.peg = peg;
	}
	

}
