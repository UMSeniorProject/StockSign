package com.seniorproject.stocksign.database;

/**
 * Stock model that contains the data to be 
 * saved in the database and show in the user interface.
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class Stock {
	
	private int _id;
	private String ticker;
	private String company;
	private String sector;
	private String industry;
	private String country;
	private float pe;
	private float forward_pe;
	private float peg;
	private float ps;
	private float pb;
	private float pc;
	private float priceFreeCashFlow;
	private float epsgThisYear;
	private float epsgPast5Years;
	private float epsgNext5Years;
	private float salesgPast5Years;
	private float epsg;
	private float salesg;
	private float dividendYield;
	private float returnOnAssets;
	private float returnOnEquity;
	private float returnOnInvestment;
	private float currentRatio;
	private float quickRatio;
	private float ltDebtEquity;
	private float debtEquity;
	private float grossMargin;
	private float operatingMargin;
	private float netProfitMargin;
	private float payoutRatio;
	private float insiderOwnership;
	private float institutionalTransactions;
	private float floatShort;
	private float optionShort;
	private float rsi;
	
	private String date;
	private float open;
	private float high;
	private float low;
	private float close;
	private float volume;
	private float adjclose;
	
	
	
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
	 * @return the ticker
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * @param ticker the ticker to set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
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
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
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

	/**
	 * @return the ps
	 */
	public float getPs() {
		return ps;
	}

	/**
	 * @param ps the ps to set
	 */
	public void setPs(float ps) {
		this.ps = ps;
	}

	/**
	 * @return the pb
	 */
	public float getPb() {
		return pb;
	}

	/**
	 * @param pb the pb to set
	 */
	public void setPb(float pb) {
		this.pb = pb;
	}

	/**
	 * @return the pc
	 */
	public float getPc() {
		return pc;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(float pc) {
		this.pc = pc;
	}

	/**
	 * @return the priceFreeCashFlow
	 */
	public float getPriceFreeCashFlow() {
		return priceFreeCashFlow;
	}

	/**
	 * @param priceFreeCashFlow the priceFreeCashFlow to set
	 */
	public void setPriceFreeCashFlow(float priceFreeCashFlow) {
		this.priceFreeCashFlow = priceFreeCashFlow;
	}

	/**
	 * @return the epsgThisYear
	 */
	public float getEpsgThisYear() {
		return epsgThisYear;
	}

	/**
	 * @param epsgThisYear the epsgThisYear to set
	 */
	public void setEpsgThisYear(float epsgThisYear) {
		this.epsgThisYear = epsgThisYear;
	}

	/**
	 * @return the epsgPast5Years
	 */
	public float getEpsgPast5Years() {
		return epsgPast5Years;
	}

	/**
	 * @param epsgPast5Years the epsgPast5Years to set
	 */
	public void setEpsgPast5Years(float epsgPast5Years) {
		this.epsgPast5Years = epsgPast5Years;
	}

	/**
	 * @return the epsgNext5Years
	 */
	public float getEpsgNext5Years() {
		return epsgNext5Years;
	}

	/**
	 * @param epsgNext5Years the epsgNext5Years to set
	 */
	public void setEpsgNext5Years(float epsgNext5Years) {
		this.epsgNext5Years = epsgNext5Years;
	}

	/**
	 * @return the salesgPast5Years
	 */
	public float getSalesgPast5Years() {
		return salesgPast5Years;
	}

	/**
	 * @param salesgPast5Years the salesgPast5Years to set
	 */
	public void setSalesgPast5Years(float salesgPast5Years) {
		this.salesgPast5Years = salesgPast5Years;
	}

	/**
	 * @return the epsg
	 */
	public float getEpsg() {
		return epsg;
	}

	/**
	 * @param epsg the epsg to set
	 */
	public void setEpsg(float epsg) {
		this.epsg = epsg;
	}

	/**
	 * @return the salesg
	 */
	public float getSalesg() {
		return salesg;
	}

	/**
	 * @param salesg the salesg to set
	 */
	public void setSalesg(float salesg) {
		this.salesg = salesg;
	}

	/**
	 * @return the dividendYield
	 */
	public float getDividendYield() {
		return dividendYield;
	}

	/**
	 * @param dividendYield the dividendYield to set
	 */
	public void setDividendYield(float dividendYield) {
		this.dividendYield = dividendYield;
	}

	/**
	 * @return the returnOnAssets
	 */
	public float getReturnOnAssets() {
		return returnOnAssets;
	}

	/**
	 * @param returnOnAssets the returnOnAssets to set
	 */
	public void setReturnOnAssets(float returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	/**
	 * @return the returnOnEquity
	 */
	public float getReturnOnEquity() {
		return returnOnEquity;
	}

	/**
	 * @param returnOnEquity the returnOnEquity to set
	 */
	public void setReturnOnEquity(float returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}

	/**
	 * @return the returnOnInvestment
	 */
	public float getReturnOnInvestment() {
		return returnOnInvestment;
	}

	/**
	 * @param returnOnInvestment the returnOnInvestment to set
	 */
	public void setReturnOnInvestment(float returnOnInvestment) {
		this.returnOnInvestment = returnOnInvestment;
	}

	/**
	 * @return the currentRatio
	 */
	public float getCurrentRatio() {
		return currentRatio;
	}

	/**
	 * @param currentRatio the currentRatio to set
	 */
	public void setCurrentRatio(float currentRatio) {
		this.currentRatio = currentRatio;
	}

	/**
	 * @return the quickRatio
	 */
	public float getQuickRatio() {
		return quickRatio;
	}

	/**
	 * @param quickRatio the quickRatio to set
	 */
	public void setQuickRatio(float quickRatio) {
		this.quickRatio = quickRatio;
	}

	/**
	 * @return the ltDebtEquity
	 */
	public float getLtDebtEquity() {
		return ltDebtEquity;
	}

	/**
	 * @param ltDebtEquity the ltDebtEquity to set
	 */
	public void setLtDebtEquity(float ltDebtEquity) {
		this.ltDebtEquity = ltDebtEquity;
	}

	/**
	 * @return the debtEquity
	 */
	public float getDebtEquity() {
		return debtEquity;
	}

	/**
	 * @param debtEquity the debtEquity to set
	 */
	public void setDebtEquity(float debtEquity) {
		this.debtEquity = debtEquity;
	}

	/**
	 * @return the grossMargin
	 */
	public float getGrossMargin() {
		return grossMargin;
	}

	/**
	 * @param grossMargin the grossMargin to set
	 */
	public void setGrossMargin(float grossMargin) {
		this.grossMargin = grossMargin;
	}

	/**
	 * @return the operatingMargin
	 */
	public float getOperatingMargin() {
		return operatingMargin;
	}

	/**
	 * @param operatingMargin the operatingMargin to set
	 */
	public void setOperatingMargin(float operatingMargin) {
		this.operatingMargin = operatingMargin;
	}

	/**
	 * @return the netProfitMargin
	 */
	public float getNetProfitMargin() {
		return netProfitMargin;
	}

	/**
	 * @param netProfitMargin the netProfitMargin to set
	 */
	public void setNetProfitMargin(float netProfitMargin) {
		this.netProfitMargin = netProfitMargin;
	}

	/**
	 * @return the payoutRatio
	 */
	public float getPayoutRatio() {
		return payoutRatio;
	}

	/**
	 * @param payoutRatio the payoutRatio to set
	 */
	public void setPayoutRatio(float payoutRatio) {
		this.payoutRatio = payoutRatio;
	}

	/**
	 * @return the insiderOwnership
	 */
	public float getInsiderOwnership() {
		return insiderOwnership;
	}

	/**
	 * @param insiderOwnership the insiderOwnership to set
	 */
	public void setInsiderOwnership(float insiderOwnership) {
		this.insiderOwnership = insiderOwnership;
	}

	/**
	 * @return the institutionalTransactions
	 */
	public float getInstitutionalTransactions() {
		return institutionalTransactions;
	}

	/**
	 * @param institutionalTransactions the institutionalTransactions to set
	 */
	public void setInstitutionalTransactions(float institutionalTransactions) {
		this.institutionalTransactions = institutionalTransactions;
	}

	/**
	 * @return the floatShort
	 */
	public float getFloatShort() {
		return floatShort;
	}

	/**
	 * @param floatShort the floatShort to set
	 */
	public void setFloatShort(float floatShort) {
		this.floatShort = floatShort;
	}

	/**
	 * @return the optionShort
	 */
	public float getOptionShort() {
		return optionShort;
	}

	/**
	 * @param optionShort the optionShort to set
	 */
	public void setOptionShort(float optionShort) {
		this.optionShort = optionShort;
	}

	/**
	 * @return the rsi
	 */
	public float getRsi() {
		return rsi;
	}

	/**
	 * @param rsi the rsi to set
	 */
	public void setRsi(float rsi) {
		this.rsi = rsi;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the open
	 */
	public float getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(float open) {
		this.open = open;
	}

	/**
	 * @return the low
	 */
	public float getLow() {
		return low;
	}

	/**
	 * @return the high
	 */
	public float getHigh() {
		return high;
	}

	/**
	 * @param high the high to set
	 */
	public void setHigh(float high) {
		this.high = high;
	}

	/**
	 * @param low the low to set
	 */
	public void setLow(float low) {
		this.low = low;
	}

	/**
	 * @return the close
	 */
	public float getClose() {
		return close;
	}

	/**
	 * @param close the close to set
	 */
	public void setClose(float close) {
		this.close = close;
	}

	/**
	 * @return the volume
	 */
	public float getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(float volume) {
		this.volume = volume;
	}

	/**
	 * @return the adjclose
	 */
	public float getAdjclose() {
		return adjclose;
	}

	/**
	 * @param adj the adjclose to set
	 */
	public void setAdjclose(float adjclose) {
		this.adjclose = adjclose;
	}
	

}
