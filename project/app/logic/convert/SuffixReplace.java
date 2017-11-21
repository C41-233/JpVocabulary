package logic.convert;

import java.util.HashMap;
import java.util.Map.Entry;

public class SuffixReplace {

	private final HashMap<String, String> map = new HashMap<>();
	
	public void add(String from, String to) {
		map.put(from, to);
	}
	
	public String replace(String value) {
		if(value == null) {
			return value;
		}
		for(Entry<String, String> entry : map.entrySet()) {
			String from = entry.getKey();
			String to = entry.getValue();
			if(value.endsWith(from)) {
				String body = value.substring(0, value.length() - from.length());
				return body + to;
			}
		}
		return null;
	}
	
}
