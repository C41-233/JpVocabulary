package core.model;

import java.util.ArrayList;
import java.util.List;

import base.core.StaticClass;

public final class ConcatSplit extends StaticClass{

	public static List<String> split(String s) {
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

	public static String concat(List<String> s) {
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

}
