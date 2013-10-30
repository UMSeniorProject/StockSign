package com.seniorproject.stocksign.database;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

/**
 * Stock model that contains the data to be 
 * saved in the database and show in the user interface.
 * 
 * @author Sean Wilkinson
 * @since 1.0
 *
 */
public class Stock extends GenericJson{
	
	private int stockSize = 47;	//this is yet to be exactly determined
	
	@Key("_id")
	private String _id;
	@Key("Ticker")
	private String ticker;
	@Key("Company")
	private String company;
	@Key("Sector")
	private String sector;
	@Key("Industry")
	private String industry;
	@Key("Country")
	private String country;
	@Key("P/E")
	private String pe;
	@Key("Forward P/E")
	private String forward_pe;
	@Key("PEG")
	private String peg;
	@Key("P/S")
	private String ps;
	@Key("P/B")
	private String pb;
	@Key("P/Cash")
	private String pc;
	@Key("P/Free Cash Flow")
	private String priceFreeCashFlow;
	@Key("Dividend Yield")
	private String dividendYield;
	@Key("Payout Ratio")
	private String payoutRatio;
	@Key("EPS growth this year")
	private String epsgThisYear;
	@Key("EPS growth next year")
	private String epsgNextYear;
	@Key("EPS growth past 5 years")
	private String epsgPast5Years;
	@Key("EPS growth next 5 years")
	private String epsgNext5Years;
	@Key("Sales growth past 5 years")
	private String salesgPast5Years;
	@Key("Sales growth next 5 years")
	private String salesgNext5years;
	@Key("EPS growth quarter over quarter")
	private String epsg_qoq;
	@Key("Sales growth quarter over quarter")
	private String salesg_qoq;
	@Key("Insider Ownership")
	private String insiderOwnership;
	@Key("Insider Transactions")
	private String insiderTransactions;
	@Key("Institutional Ownership")
	private String institutionalOwnership;
	@Key("Institutional Transactions")
	private String institutionalTransactions;
	@Key("Float Short")
	private String floatShort;
	@Key("Short Ratio")
	private String shortRatio;
	@Key("Return on Assets")
	private String returnOnAssets;
	@Key("Return on Equity")
	private String returnOnEquity;
	@Key("Return on Investment")
	private String returnOnInvestment;
	@Key("Current Ratio")
	private String currentRatio;
	@Key("Quick Ratio")
	private String quickRatio;
	@Key("LT Debt/Equity")
	private String ltDebtEquity;
	@Key("Gross Margin")
	private String grossMargin;
	@Key("Profit Margin")
	private String netProfitMargin;
	@Key("Operating Margin")
	private String operatingMargin;
	@Key("Relative Strength Index")
	private String rsi;	

	 @Key("Date") private String date;
	 @Key("Open") private String open;
	 @Key("High") private String high;
	 @Key("Low") private String low;
	 @Key("Close") private String close;
	 @Key("Volume") private String volume;
	 @Key("Adj Close") private String adjclose;
	 
	 @Key("_kmd")
	 private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
	 @Key("_acl")
	 private KinveyMetaData.AccessControlList acl; // Kinvey access control,
													// OPTIONAL
	public Stock() {
	} // GenericJson classes must have a public empty constructor

	private String epsg;
	private String salesg;

	private String debtEquity;

	/**
	 * @return the stockSize
	 */
	public int getSize() {
		return stockSize;
	}
	
	/**
	 * @param size the stockSize to set
	 */
	public void setSize(int size) {
		this.stockSize = size;
	}
	
	
	/**
	 * @return the _id
	 */
	public String getId() {
		return _id;
	}
	
	/**
	 * @param _id the _id to set
	 */
	public void setId(String _id) {
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
	public String getPe() {
		return pe;
	}

	/**
	 * @param pe the pe to set
	 */
	public void setPe(String pe) {
		this.pe = pe;
	}

	/**
	 * @return the forward_pe
	 */
	public String getForward_pe() {
		return forward_pe;
	}

	/**
	 * @param forward_pe the forward_pe to set
	 */
	public void setForward_pe(String forward_pe) {
		this.forward_pe = forward_pe;
	}

	/**
	 * @return the peg
	 */
	public String getPeg() {
		return peg;
	}

	/**
	 * @param peg the peg to set
	 */
	public void setPeg(String peg) {
		this.peg = peg;
	}

	/**
	 * @return the ps
	 */
	public String getPs() {
		return ps;
	}

	/**
	 * @param ps the ps to set
	 */
	public void setPs(String ps) {
		this.ps = ps;
	}

	/**
	 * @return the pb
	 */
	public String getPb() {
		return pb;
	}

	/**
	 * @param pb the pb to set
	 */
	public void setPb(String pb) {
		this.pb = pb;
	}

	/**
	 * @return the pc
	 */
	public String getPc() {
		return pc;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(String pc) {
		this.pc = pc;
	}

	/**
	 * @return the priceFreeCashFlow
	 */
	public String getPriceFreeCashFlow() {
		return priceFreeCashFlow;
	}

	/**
	 * @param priceFreeCashFlow the priceFreeCashFlow to set
	 */
	public void setPriceFreeCashFlow(String priceFreeCashFlow) {
		this.priceFreeCashFlow = priceFreeCashFlow;
	}

	/**
	 * @return the epsgThisYear
	 */
	public String getEpsgThisYear() {
		return epsgThisYear;
	}

	/**
	 * @param epsgThisYear the epsgThisYear to set
	 */
	public void setEpsgThisYear(String epsgThisYear) {
		this.epsgThisYear = epsgThisYear;
	}

	/**
	 * @return the epsgPast5Years
	 */
	public String getEpsgPast5Years() {
		return epsgPast5Years;
	}

	/**
	 * @param epsgPast5Years the epsgPast5Years to set
	 */
	public void setEpsgPast5Years(String epsgPast5Years) {
		this.epsgPast5Years = epsgPast5Years;
	}

	/**
	 * @return the epsgNext5Years
	 */
	public String getEpsgNext5Years() {
		return epsgNext5Years;
	}

	/**
	 * @param epsgNext5Years the epsgNext5Years to set
	 */
	public void setEpsgNext5Years(String epsgNext5Years) {
		this.epsgNext5Years = epsgNext5Years;
	}

	/**
	 * @return the salesgPast5Years
	 */
	public String getSalesgPast5Years() {
		return salesgPast5Years;
	}

	/**
	 * @param salesgPast5Years the salesgPast5Years to set
	 */
	public void setSalesgPast5Years(String salesgPast5Years) {
		this.salesgPast5Years = salesgPast5Years;
	}

	/**
	 * @return the epsg
	 */
	public String getEpsg() {
		return epsg;
	}

	/**
	 * @param epsg the epsg to set
	 */
	public void setEpsg(String epsg) {
		this.epsg = epsg;
	}

	/**
	 * @return the salesg
	 */
	public String getSalesg() {
		return salesg;
	}

	/**
	 * @param salesg the salesg to set
	 */
	public void setSalesg(String salesg) {
		this.salesg = salesg;
	}

	/**
	 * @return the dividendYield
	 */
	public String getDividendYield() {
		return dividendYield;
	}

	/**
	 * @param dividendYield the dividendYield to set
	 */
	public void setDividendYield(String dividendYield) {
		this.dividendYield = dividendYield;
	}

	/**
	 * @return the returnOnAssets
	 */
	public String getReturnOnAssets() {
		return returnOnAssets;
	}

	/**
	 * @param returnOnAssets the returnOnAssets to set
	 */
	public void setReturnOnAssets(String returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	/**
	 * @return the returnOnEquity
	 */
	public String getReturnOnEquity() {
		return returnOnEquity;
	}

	/**
	 * @param returnOnEquity the returnOnEquity to set
	 */
	public void setReturnOnEquity(String returnOnEquity) {
		this.returnOnEquity = returnOnEquity;
	}

	/**
	 * @return the returnOnInvestment
	 */
	public String getReturnOnInvestment() {
		return returnOnInvestment;
	}

	/**
	 * @param returnOnInvestment the returnOnInvestment to set
	 */
	public void setReturnOnInvestment(String returnOnInvestment) {
		this.returnOnInvestment = returnOnInvestment;
	}

	/**
	 * @return the currentRatio
	 */
	public String getCurrentRatio() {
		return currentRatio;
	}

	/**
	 * @param currentRatio the currentRatio to set
	 */
	public void setCurrentRatio(String currentRatio) {
		this.currentRatio = currentRatio;
	}

	/**
	 * @return the quickRatio
	 */
	public String getQuickRatio() {
		return quickRatio;
	}

	/**
	 * @param quickRatio the quickRatio to set
	 */
	public void setQuickRatio(String quickRatio) {
		this.quickRatio = quickRatio;
	}

	/**
	 * @return the ltDebtEquity
	 */
	public String getLtDebtEquity() {
		return ltDebtEquity;
	}

	/**
	 * @param ltDebtEquity the ltDebtEquity to set
	 */
	public void setLtDebtEquity(String ltDebtEquity) {
		this.ltDebtEquity = ltDebtEquity;
	}

	/**
	 * @return the debtEquity
	 */
	public String getDebtEquity() {
		return debtEquity;
	}

	/**
	 * @param debtEquity the debtEquity to set
	 */
	public void setDebtEquity(String debtEquity) {
		this.debtEquity = debtEquity;
	}

	/**
	 * @return the grossMargin
	 */
	public String getGrossMargin() {
		return grossMargin;
	}

	/**
	 * @param grossMargin the grossMargin to set
	 */
	public void setGrossMargin(String grossMargin) {
		this.grossMargin = grossMargin;
	}

	/**
	 * @return the operatingMargin
	 */
	public String getOperatingMargin() {
		return operatingMargin;
	}

	/**
	 * @param operatingMargin the operatingMargin to set
	 */
	public void setOperatingMargin(String operatingMargin) {
		this.operatingMargin = operatingMargin;
	}

	/**
	 * @return the netProfitMargin
	 */
	public String getNetProfitMargin() {
		return netProfitMargin;
	}

	/**
	 * @param netProfitMargin the netProfitMargin to set
	 */
	public void setNetProfitMargin(String netProfitMargin) {
		this.netProfitMargin = netProfitMargin;
	}

	/**
	 * @return the payoutRatio
	 */
	public String getPayoutRatio() {
		return payoutRatio;
	}

	/**
	 * @param payoutRatio the payoutRatio to set
	 */
	public void setPayoutRatio(String payoutRatio) {
		this.payoutRatio = payoutRatio;
	}

	/**
	 * @return the insiderOwnership
	 */
	public String getInsiderOwnership() {
		return insiderOwnership;
	}

	/**
	 * @param insiderOwnership the insiderOwnership to set
	 */
	public void setInsiderOwnership(String insiderOwnership) {
		this.insiderOwnership = insiderOwnership;
	}

	/**
	 * @return the institutionalTransactions
	 */
	public String getInstitutionalTransactions() {
		return institutionalTransactions;
	}

	/**
	 * @param institutionalTransactions the institutionalTransactions to set
	 */
	public void setInstitutionalTransactions(String institutionalTransactions) {
		this.institutionalTransactions = institutionalTransactions;
	}

	/**
	 * @return the floatShort
	 */
	public String getFloatShort() {
		return floatShort;
	}

	/**
	 * @param floaStringt the floatShort to set
	 */
	public void setFloatShort(String floatShort) {
		this.floatShort = floatShort;
	}

	/**
	 * @return the shortRatio
	 */
	public String getShortRatio() {
		return shortRatio;
	}

	/**
	 * @param shortRatio the shortRatio to set
	 */
	public void setShortRatio(String shortRatio) {
		this.shortRatio = shortRatio;
	}

	/**
	 * @return the rsi
	 */
	public String getRsi() {
		return rsi;
	}

	/**
	 * @param rsi the rsi to set
	 */
	public void setRsi(String rsi) {
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
	public String getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(String open) {
		this.open = open;
	}

	/**
	 * @return the low
	 */
	public String getLow() {
		return low;
	}

	/**
	 * @return the high
	 */
	public String getHigh() {
		return high;
	}

	/**
	 * @param high the high to set
	 */
	public void setHigh(String high) {
		this.high = high;
	}

	/**
	 * @param low the low to set
	 */
	public void setLow(String low) {
		this.low = low;
	}

	/**
	 * @return the close
	 */
	public String getClose() {
		return close;
	}

	/**
	 * @param close the close to set
	 */
	public void setClose(String close) {
		this.close = close;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the adjclose
	 */
	public String getAdjclose() {
		return adjclose;
	}

	/**
	 * @param adj the adjclose to set
	 */
	public void setAdjclose(String adjclose) {
		this.adjclose = adjclose;
	}
	

}
