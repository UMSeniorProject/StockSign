package com.seniorproject.stocksign.database;

import java.util.List;

public class TickerTrie {

	private static SetTrie tickerTrie;
	
	public static void setTrie(SetTrie trie) {
		tickerTrie = trie;
	}
	
	public static List<String> getMatches(String prefix, int limit) {
		if(tickerTrie != null) {
			List<String> completions = tickerTrie.findCompletions(prefix);
			if(limit > completions.size()) {
				return completions;
			}
			List<String> subCompletions = completions.subList(0, limit);
			return subCompletions;
		}
		return null;

	}
}
