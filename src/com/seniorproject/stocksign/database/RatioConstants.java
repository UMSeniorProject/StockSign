package com.seniorproject.stocksign.database;

public interface RatioConstants {

	public final int totalRatios = 40;
	
	public static final int  ticker = 0;//Ticker

	public static final int  company = 1; //Company name
	
	public static final int  sector = 2;//Sector

	public static final int  industry = 3; //Industry

	public static final int  country = 4; //Country

	public static final int  PE = 5; //P/E

	public static final int  FPE = 6;//Forward P/E

	public static final int  PEG = 7; //PEG

	public static final int PS = 8; //P/S

	public static final int PB = 9; //P/B

	public static final int PC = 10; //P/Cash

	public static final int PFCF = 11; //P/Free Cash Flow

	public static final int dYield = 12; //Dividend Yield

	public static final int PO = 13; //Payout Ratio

	public static final int EPSTHISYR  = 14; //EPS growth this year

	public static final int EPSNEXTYEAR = 15;//EPS growth next year

	public static final int EPS5 = 16;//EPS growth past 5 years

	public static final int EG = 17; //EPS growth next 5 years

	public static final int SALES5 = 18; //Sales growth past 5 years

	public static final int FLOAT = 19;//Shares Float

	public static final int INSIOWN = 20; //Insider Ownership

	public static final int INSITRANS = 21; //Insider Transactions

	public static final int INSTOWN = 22; //Institutional Ownership

	public static final int INSTTRANS = 23; //Institutional Transactions 

	public static final int SHORT = 24; //Float Short

	public static final int ROA = 25; //Return on Assets

	public static final int ROE = 26;//Return on Equity

	public static final int CR = 27;//Current Ratio

	public static final int QR = 28; //Quick Ratio

	public static final int LTDE = 29;//LT Debt/Equity

	public static final int PM = 30;//Profit Margin

	public static final int BETA = 31;

	public static final int WVOL = 32;//Volatility (Week)

	public static final int MVOL = 33; //Volatility (Month) 

	public static final int RSI = 34;//Relative Strength Index

	public static final int AVGVOL = 35;//Average Volume

	public static final int RVOL = 36;//Relative Volume

	public static final int TOTALSCORE = 37; //total score

	public static final int DIVSCORE = 38; //dividend score

	public static final int GROWTHSCORE = 39; //growth score

}
