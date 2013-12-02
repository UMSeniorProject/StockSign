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
	private String t;
	@Key("Company")
	private String n;
	@Key("Sector")
	private String s;
	@Key("Industry")
	private String i;
	@Key("Country")
	private String c;
	@Key("P/E")
	private String PE;
	@Key("Forward P/E")
	private String FPE;
	@Key("PEG")
	private String PEG;
	@Key("P/S")
	private String PS;
	@Key("P/B")
	private String PB;
	@Key("P/Cash")
	private String PC;
	@Key("P/Free Cash Flow")
	private String PFCF;
	@Key("Dividend Yield")
	private String d;
	@Key("Payout Ratio")
	private String PO;
	@Key("EPS growth this year")
	private String EPSTHISYR;
	@Key("EPS growth next year")
	private String EPSNEXTYEAR;
	@Key("EPS growth past 5 years")
	private String EPS5;
	@Key("EPS growth next 5 years")
	private String eg;
	@Key("Sales growth past 5 years")
	private String SALES5;
	@Key("Shares Float")
	private String FLOAT;
	@Key("Sales growth next 5 years")
	private String salesgNext5years;
	@Key("EPS growth quarter over quarter")
	private String epsg_qoq;
	@Key("Sales growth quarter over quarter")
	private String salesg_qoq;
	@Key("Insider Ownership")
	private String INSIOWN;
	@Key("Insider Transactions")
	private String INSITRANS;
	@Key("Institutional Ownership")
	private String INSTOWN;
	@Key("Institutional Transactions")
	private String INSTTRANS;
	@Key("Float Short")
	private String SHORT;
	@Key("Short Ratio")
	private String shortRatio;
	@Key("Return on Assets")
	private String ROA;
	@Key("Return on Equity")
	private String ROE;
	@Key("Return on Investment")
	private String returnOnInvestment;
	@Key("Current Ratio")
	private String CR;
	@Key("Quick Ratio")
	private String QR;
	@Key("LT Debt/Equity")
	private String LTDE;
	@Key("Gross Margin")
	private String grossMargin;
	@Key("Profit Margin")
	private String PM;
	@Key("Operating Margin")
	private String operatingMargin;
	@Key("BETA")
	private String BETA;
	@Key("Volatility (Week)")
	private String WVOL;
	@Key("Volatility (Month)")
	private String MVOL;
	@Key("Relative Strength Index")
	private String RSI;
	@Key("Average Volume")
	private String AVGVOL;
	@Key("Relative Volume")
	private String RVOL;
	@Key("Total Score")
	private String dan;
	@Key("Dividend Score")
	private String DIVSCORE;
	@Key("Growth Score")
	private String growth;
	
	

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
		return t;
	}

	/**
	 * @param ticker the ticker to set
	 */
	public void setTicker(String ticker) {
		this.t = ticker;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return n;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.n = company;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return s;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.s = sector;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return i;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.i = industry;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return c;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.c = country;
	}

	/**
	 * @return the pe
	 */
	public String getPe() {
		return PE;
	}

	/**
	 * @param pe the pe to set
	 */
	public void setPe(String pe) {
		this.PE = pe;
	}

	/**
	 * @return the forward_pe
	 */
	public String getForward_pe() {
		return FPE;
	}

	/**
	 * @param forward_pe the forward_pe to set
	 */
	public void setForward_pe(String forward_pe) {
		this.FPE = forward_pe;
	}

	/**
	 * @return the peg
	 */
	public String getPeg() {
		return PEG;
	}

	/**
	 * @param peg the peg to set
	 */
	public void setPeg(String peg) {
		this.PEG = peg;
	}

	/**
	 * @return the ps
	 */
	public String getPs() {
		return PS;
	}

	/**
	 * @param ps the ps to set
	 */
	public void setPs(String ps) {
		this.PS = ps;
	}

	/**
	 * @return the pb
	 */
	public String getPb() {
		return PB;
	}

	/**
	 * @param pb the pb to set
	 */
	public void setPb(String pb) {
		this.PB = pb;
	}

	/**
	 * @return the pc
	 */
	public String getPc() {
		return PC;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(String pc) {
		this.PC = pc;
	}

	/**
	 * @return the priceFreeCashFlow
	 */
	public String getPriceFreeCashFlow() {
		return PFCF;
	}

	/**
	 * @param priceFreeCashFlow the priceFreeCashFlow to set
	 */
	public void setPriceFreeCashFlow(String priceFreeCashFlow) {
		this.PFCF = priceFreeCashFlow;
	}

	/**
	 * @return the epsgThisYear
	 */
	public String getEpsgThisYear() {
		return EPSTHISYR;
	}

	/**
	 * @param epsgThisYear the epsgThisYear to set
	 */
	public void setEpsgThisYear(String epsgThisYear) {
		this.EPSTHISYR = epsgThisYear;
	}

	/**
	 * @return the epsgPast5Years
	 */
	public String getEpsgPast5Years() {
		return EPS5;
	}

	/**
	 * @param epsgPast5Years the epsgPast5Years to set
	 */
	public void setEpsgPast5Years(String epsgPast5Years) {
		this.EPS5 = epsgPast5Years;
	}

	/**
	 * @return the epsgNext5Years
	 */
	public String getEpsgNext5Years() {
		return eg;
	}

	/**
	 * @param epsgNext5Years the epsgNext5Years to set
	 */
	public void setEpsgNext5Years(String epsgNext5Years) {
		this.eg = epsgNext5Years;
	}

	/**
	 * @return the salesgPast5Years
	 */
	public String getSalesgPast5Years() {
		return SALES5;
	}

	/**
	 * @param salesgPast5Years the salesgPast5Years to set
	 */
	public void setSalesgPast5Years(String salesgPast5Years) {
		this.SALES5 = salesgPast5Years;
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
		return d;
	}

	/**
	 * @param dividendYield the dividendYield to set
	 */
	public void setDividendYield(String dividendYield) {
		this.d = dividendYield;
	}

	/**
	 * @return the returnOnAssets
	 */
	public String getReturnOnAssets() {
		return ROA;
	}

	/**
	 * @param returnOnAssets the returnOnAssets to set
	 */
	public void setReturnOnAssets(String returnOnAssets) {
		this.ROA = returnOnAssets;
	}

	/**
	 * @return the returnOnEquity
	 */
	public String getReturnOnEquity() {
		return ROE;
	}

	/**
	 * @param returnOnEquity the returnOnEquity to set
	 */
	public void setReturnOnEquity(String returnOnEquity) {
		this.ROE = returnOnEquity;
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
		return CR;
	}

	/**
	 * @param currentRatio the currentRatio to set
	 */
	public void setCurrentRatio(String currentRatio) {
		this.CR = currentRatio;
	}

	/**
	 * @return the quickRatio
	 */
	public String getQuickRatio() {
		return QR;
	}

	/**
	 * @param quickRatio the quickRatio to set
	 */
	public void setQuickRatio(String quickRatio) {
		this.QR = quickRatio;
	}

	/**
	 * @return the ltDebtEquity
	 */
	public String getLtDebtEquity() {
		return LTDE;
	}

	/**
	 * @param ltDebtEquity the ltDebtEquity to set
	 */
	public void setLtDebtEquity(String ltDebtEquity) {
		this.LTDE = ltDebtEquity;
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
		return PM;
	}

	/**
	 * @param netProfitMargin the netProfitMargin to set
	 */
	public void setNetProfitMargin(String netProfitMargin) {
		this.PM = netProfitMargin;
	}

	/**
	 * @return the payoutRatio
	 */
	public String getPayoutRatio() {
		return PO;
	}

	/**
	 * @param payoutRatio the payoutRatio to set
	 */
	public void setPayoutRatio(String payoutRatio) {
		this.PO = payoutRatio;
	}

	/**
	 * @return the insiderOwnership
	 */
	public String getInsiderOwnership() {
		return INSIOWN;
	}

	/**
	 * @param insiderOwnership the insiderOwnership to set
	 */
	public void setInsiderOwnership(String insiderOwnership) {
		this.INSIOWN = insiderOwnership;
	}

	/**
	 * @return the institutionalTransactions
	 */
	public String getInstitutionalTransactions() {
		return INSITRANS;
	}

	/**
	 * @param institutionalTransactions the institutionalTransactions to set
	 */
	public void setInstitutionalTransactions(String institutionalTransactions) {
		this.INSITRANS = institutionalTransactions;
	}

	/**
	 * @return the floatShort
	 */
	public String getFloatShort() {
		return SHORT;
	}

	/**
	 * @param floaStringt the floatShort to set
	 */
	public void setFloatShort(String floatShort) {
		this.SHORT = floatShort;
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
		return RSI;
	}

	/**
	 * @param rsi the rsi to set
	 */
	public void setRsi(String rsi) {
		this.RSI = rsi;
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
