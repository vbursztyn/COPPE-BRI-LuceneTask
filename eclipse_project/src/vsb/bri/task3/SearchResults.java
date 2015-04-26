package vsb.bri.task3;

import java.util.Map;
import java.util.TreeMap;

public class SearchResults {
	
	private String mQueryId;
	private Map<Integer,String> mResults = new TreeMap<Integer,String>(); // Sorted by key, so our output remains correct.
	
	public SearchResults(String queryNum) {
		mQueryId = queryNum;
	}
	
	public void add(int key, String recordNum, Float score) {
		String value = recordNum.trim() + ", " + Float.toString(score);
		mResults.put(key, value);
	}
	
	public String toString() {
		String line = mQueryId + "; [ ";
		for (Map.Entry<Integer, String> result : mResults.entrySet()) {
			line += "( ";
			line += result.getKey() + ", " + result.getValue();
			line += " ), ";
		}
		line = line.substring(0, line.length()-2);
		line += " ]";
		return line;
	}
}
