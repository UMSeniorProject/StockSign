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
	@Key("Price_to_Earnings")
	private float pe;
	@Key("Forward_Price_to_Earnings")
	private float fpe;
	@Key("Price_to_Earnings_Growth")
	private float peg;
	@Key("Price_to_Sales")
	private float ps;
	@Key("Price_to_Book")
	private float pb;
	@Key("Price_to_Cash")
	private float pc;
	@Key("Price_to_Free_Cash_Flow")
	private float pfcf;
	@Key("Dividend_Yield")
	private float dyield;
	@Key("Payout_Ratio")
	private float po;
	@Key("EPS_Growth_This_Year")
	private float epsthisyr;
	@Key("EPS_Growth_Next_Year")
	private float epsnextyr;
	@Key("EPS_Growth_Past_5_Years")
	private float epspast5yr;
	@Key("EPS_Growth_Next_5_Years")
	private float epsnext5yr;
	@Key("Sales_Growth_Past_5_Years")
	private float salespast5yr;
	@Key("Shares Float")
	private float sfloat;
	@Key("Sales growth next 5 years")
	private float salesnext5yr;
	@Key("EPS growth quarter over quarter")
	private float epsg_qoq;
	@Key("Sales growth quarter over quarter")
	private float salesg_qoq;
	@Key("Insider_Ownership")
	private float insiown;
	@Key("Insider_Transactions")
	private float insitrans;
	@Key("Institutional_Ownership")
	private float instown;
	@Key("Institutional_Transactions")
	private float insttrans;
	@Key("Float_Short")
	private float fshort;
	@Key("Short Ratio")
	private float sr;
	@Key("Return_On_Assets")
	private float roa;
	@Key("Return_On_Equity")
	private float roe;
	@Key("Return On Investment")
	private float roi;
	@Key("Current Ratio")
	private float cr;
	@Key("Quick Ratio")
	private float qr;
	@Key("Longterm_Debt_to_Equity")
	private float ltde;
	@Key("Gross Margin")
	private float grossMargin;
	@Key("Profit_Margin")
	private float profitMargin;
	@Key("Operating Margin")
	private float operatingMargin;
	@Key("Beta")
	private float beta;
	@Key("Volatility (Week)")
	private float wvol;
	@Key("Volatility (Month)")
	private float mvol;
	@Key("Relative_Strength_Index")
	private float rsi;
	@Key("Average Volume")
	private float avgvol;
	@Key("Relative Volume")
	private float rvol;
	@Key("Total_Score")
	private float totalscore;
	@Key("Dividend_Score")
	private float divscore;
	@Key("Growth_Score")
	private float growthscore;
	@Key("Total_Rank")
	private int totalrank;
	@Key("Dividend_Rank")
	private int divrank;
	@Key("Growth_Rank")
	private int growthrank;
	
	
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
	public int getStockSize() {
		return stockSize;
	}
	public void setStockSize(int stockSize) {
		this.stockSize = stockSize;
	}
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public float getPe() {
		return pe;
	}
	public void setPe(float pe) {
		this.pe = pe;
	}
	public float getFpe() {
		return fpe;
	}
	public void setFpe(float fpe) {
		this.fpe = fpe;
	}
	public float getPeg() {
		return peg;
	}
	public void setPeg(float peg) {
		this.peg = peg;
	}
	public float getPs() {
		return ps;
	}
	public void setPs(float ps) {
		this.ps = ps;
	}
	public float getPb() {
		return pb;
	}
	public void setPb(float pb) {
		this.pb = pb;
	}
	public float getPc() {
		return pc;
	}
	public void setPc(float pc) {
		this.pc = pc;
	}
	public float getPfcf() {
		return pfcf;
	}
	public void setPfcf(float pfcf) {
		this.pfcf = pfcf;
	}
	public float getDyield() {
		return dyield;
	}
	public void setDyield(float dyield) {
		this.dyield = dyield;
	}
	public float getPo() {
		return po;
	}
	public void setPo(float po) {
		this.po = po;
	}
	public float getEpsthisyr() {
		return epsthisyr;
	}
	public void setEpsthisyr(float epsthisyr) {
		this.epsthisyr = epsthisyr;
	}
	public float getEpsnextyr() {
		return epsnextyr;
	}
	public void setEpsnextyr(float epsnextyr) {
		this.epsnextyr = epsnextyr;
	}
	public float getEpspast5yr() {
		return epspast5yr;
	}
	public void setEpspast5yr(float epspast5yr) {
		this.epspast5yr = epspast5yr;
	}
	public float getEpsnext5yr() {
		return epsnext5yr;
	}
	public void setEpsnext5yr(float epsnext5yr) {
		this.epsnext5yr = epsnext5yr;
	}
	public float getSalespast5yr() {
		return salespast5yr;
	}
	public void setSalespast5yr(float salespast5yr) {
		this.salespast5yr = salespast5yr;
	}
	public float getSfloat() {
		return sfloat;
	}
	public void setSfloat(float sfloat) {
		this.sfloat = sfloat;
	}
	public float getSalesnext5yr() {
		return salesnext5yr;
	}
	public void setSalesnext5yr(float salesnext5yr) {
		this.salesnext5yr = salesnext5yr;
	}
	public float getEpsg_qoq() {
		return epsg_qoq;
	}
	public void setEpsg_qoq(float epsg_qoq) {
		this.epsg_qoq = epsg_qoq;
	}
	public float getSalesg_qoq() {
		return salesg_qoq;
	}
	public void setSalesg_qoq(float salesg_qoq) {
		this.salesg_qoq = salesg_qoq;
	}
	public float getInsiown() {
		return insiown;
	}
	public void setInsiown(float insiown) {
		this.insiown = insiown;
	}
	public float getInsitrans() {
		return insitrans;
	}
	public void setInsitrans(float insitrans) {
		this.insitrans = insitrans;
	}
	public float getInstown() {
		return instown;
	}
	public void setInstown(float instown) {
		this.instown = instown;
	}
	public float getInsttrans() {
		return insttrans;
	}
	public void setInsttrans(float insttrans) {
		this.insttrans = insttrans;
	}
	public float getFshort() {
		return fshort;
	}
	public void setFshort(float fshort) {
		this.fshort = fshort;
	}
	public float getSr() {
		return sr;
	}
	public void setSr(float sr) {
		this.sr = sr;
	}
	public float getRoa() {
		return roa;
	}
	public void setRoa(float roa) {
		this.roa = roa;
	}
	public float getRoe() {
		return roe;
	}
	public void setRoe(float roe) {
		this.roe = roe;
	}
	public float getRoi() {
		return roi;
	}
	public void setRoi(float roi) {
		this.roi = roi;
	}
	public float getCr() {
		return cr;
	}
	public void setCr(float cr) {
		this.cr = cr;
	}
	public float getQr() {
		return qr;
	}
	public void setQr(float qr) {
		this.qr = qr;
	}
	public float getLtde() {
		return ltde;
	}
	public void setLtde(float ltde) {
		this.ltde = ltde;
	}
	public float getGrossMargin() {
		return grossMargin;
	}
	public void setGrossMargin(float grossMargin) {
		this.grossMargin = grossMargin;
	}
	public float getProfitMargin() {
		return profitMargin;
	}
	public void setProfitMargin(float profitMargin) {
		this.profitMargin = profitMargin;
	}
	public float getOperatingMargin() {
		return operatingMargin;
	}
	public void setOperatingMargin(float operatingMargin) {
		this.operatingMargin = operatingMargin;
	}
	public float getBeta() {
		return beta;
	}
	public void setBeta(float beta) {
		this.beta = beta;
	}
	public float getWvol() {
		return wvol;
	}
	public void setWvol(float wvol) {
		this.wvol = wvol;
	}
	public float getMvol() {
		return mvol;
	}
	public void setMvol(float mvol) {
		this.mvol = mvol;
	}
	public float getRsi() {
		return rsi;
	}
	public void setRsi(float rsi) {
		this.rsi = rsi;
	}
	public float getAvgvol() {
		return avgvol;
	}
	public void setAvgvol(float avgvol) {
		this.avgvol = avgvol;
	}
	public float getRvol() {
		return rvol;
	}
	public void setRvol(float rvol) {
		this.rvol = rvol;
	}
	public float getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(float totalscore) {
		this.totalscore = totalscore;
	}
	public float getDivscore() {
		return divscore;
	}
	public void setDivscore(float divscore) {
		this.divscore = divscore;
	}
	public float getGrowthscore() {
		return growthscore;
	}
	public void setGrowthscore(float growthscore) {
		this.growthscore = growthscore;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getAdjclose() {
		return adjclose;
	}
	public void setAdjclose(String adjclose) {
		this.adjclose = adjclose;
	}
	public KinveyMetaData getMeta() {
		return meta;
	}
	public void setMeta(KinveyMetaData meta) {
		this.meta = meta;
	}
	public KinveyMetaData.AccessControlList getAcl() {
		return acl;
	}
	public void setAcl(KinveyMetaData.AccessControlList acl) {
		this.acl = acl;
	}
	public int getTotalRank() {
		return totalrank;
	}
	public int getDividendRank() {
		return divrank;
	}
	public int getGrowthRank() {
		return growthrank;
	}
	public void setTotalRank(int totalrank) {
		this.totalrank = totalrank;
	}
	public void setDividendRank(int divrank) {
		this.divrank = divrank;
	}
	public void setGrowthRank(int growthrank) {
		this.growthrank = growthrank;
	}
}
