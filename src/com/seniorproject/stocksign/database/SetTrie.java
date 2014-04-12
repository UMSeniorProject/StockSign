package com.seniorproject.stocksign.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.util.Log;

public class SetTrie {
	
	private TreeSet<String> lines;

	public SetTrie() {
		lines = new TreeSet<String>();
	}

	public void load(String ticker) {
		lines.add(ticker);
	}

	/*public boolean matchPrefix(String prefix) {
		Set<String> tailSet = lines.tailSet(prefix);
		for (String tail : tailSet) {
			if (tail.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}*/

	public List<String> findCompletions(String prefix) {
		List<String> completions = new ArrayList<String>();
		Set<String> tailSet = lines.tailSet(prefix);
		for (String tail : tailSet) {
			if (tail.startsWith(prefix)) {
				completions.add(tail);
			} else {
				break;
			}
		}
		return completions;
	}
}