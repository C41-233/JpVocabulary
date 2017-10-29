package core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import base.utility.linq.Linq;

public final class ConcatSplit{

	public static final String Split = "|";

	private ConcatSplit() {}
	
	public static List<String> splitAsTokens(String s) {
		String[] tokens = s.split("\\|");
		List<String> rst = new ArrayList<>();
		for(String token : tokens) {
			token = token.trim();
			if(token.isEmpty()==false) {
				rst.add(token);
			}
		}
		return rst;
	}

	public static String concatTokens(Iterable<String> s) {
		StringBuilder sb = new StringBuilder();
		for(String token : s) {
			token = token.trim();
			if(token.isEmpty()==false) {
				sb.append("|").append(token);
			}
		}
		sb.append("|");
		return sb.toString();
	}

	public static String getToken(String value) {
		return "|"+value+"|";
	}
	
	public static List<String> splitAsLines(String s){
		if(s.isEmpty()) {
			return Collections.emptyList();
		}
		return Linq.from(s.split("\n")).toList();
	}
	
	public static String concatLines(Iterable<String> s) {
		return String.join("\n", s);
	}
	
}
