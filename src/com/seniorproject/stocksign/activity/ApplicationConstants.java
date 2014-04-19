package com.seniorproject.stocksign.activity;

public interface ApplicationConstants {

	int RATIO_DATA_TEXT_SIZE = 15;
	int SCORE_DATA_TEXT_SIZE = 17;
	
	static String SCORES_ARRAY = "scores";
	static String TICKER_ARRAY = "tickers";
	static String SCORES_BUNDLE = "scoreData";
	
	static String RATIO_BUNDLE = "ratioData";
	static String TICKER_SINGLE = "ticker";
	
	static String USER_PORTFOLIO_TITLE = "My Portfolio";
	
	//Kinvey Constants
	static String RATIO_TABLE = "StockDataTable";
	static String SECTOR_TABLE = "SectorDataTable";
	
	static String TICKER_COLUMN = "Ticker";
	static String DIVSCORE_COLUMN = "Dividend_Score";
	static String GROWTHSCORE_COLUMN = "Growth_Score";
	static String TOTALSCORE_COLUMN = "Total_Score";
	
	static String TOP_RATED_STOCKS_TITLE = "Top Rated Stocks";
	static String TOTAL_SCORE_STOCKS_TITLE = "Total Score";
	static String DIV_SCORE_STOCKS_TITLE = "Dividend Score";
	static String GROWTH_SCORE_STOCKS_TITLE = "Growth Score";
	
	int AUTOCOMPLETE_ROW_LIMIT = 5;
	int PRICE_DATA_SECTION_LIMIT = 1800;
	int SCORES_SECTION_LIMIT = 20;
	
	float DIVSCORE_LIMIT = 90f;
	float GROWTHSCORE_LIMIT = 70f;
	float TOTALSCORE_LIMIT = 50f;
	
	//Ratio names
	static String NAME_BETA = "Beta";
	static String NAME_DIV_SCORE = "Dividend Score";
	static String NAME_DIV_YIELD = "Dividend Yield";
	static String NAME_EPSG_NX_5_YR = "EPS Growth Next 5 Years";
	static String NAME_EPSG_NX_YR = "EPS Growth Next Year";
	static String NAME_EPSG_PS_5_YR = "EPS Growth Past 5 Years";
	static String NAME_EPSG_TH_YR = "EPS Growth This Year";
	static String NAME_FS = "Float Short";
	static String NAME_FPE = "Forward Price to Earnings";
	static String NAME_GROWTH_SCORE = "Growth Score";
	static String NAME_INSI_OWNER = "Insider Ownership";
	static String NAME_INSI_TRANS = "Insider Transactions";
	static String NAME_INST_OWNER = "Institutional Ownership";
	static String NAME_INST_TRANS = "Institutional Transactions";
	static String NAME_LONGTERM_DE = "Longterm Debt to Equity";
	static String NAME_PR = "Payout Ratio";
	static String NAME_PB = "Price to Book";
	static String NAME_PC = "Price to Cash";
	static String NAME_PE = "Price to Earnings";
	static String NAME_PEG = "Price to Earnings Growth";
	static String NAME_PFCF = "Price to Free Cash Flow";
	static String NAME_PS = "Price to Sales";
	static String NAME_PM = "Profit Margin";
	static String NAME_RSI = "Relative Strength Index";
	static String NAME_ROA = "Return On Assets";
	static String NAME_ROE = "Return On Equity";
	static String NAME_SG_PS_5_YR = "Sales Growth Past 5 Years";
	
	//Errors
	static String SECTOR_MISSING = "missing";
	
	//Colors
	static String COLOR_GREEN = "#009933";
	static String COLOR_RED = "#CC0000";
	
	//Portfolio
	static String ADD_PORTFOLIO = "Add To Portfolio";
	static String REM_PORTFOLIO = "Remove From Portfolio";
	static String PF_ADD = "Added";
	static String PF_REM = "Removed";
}
