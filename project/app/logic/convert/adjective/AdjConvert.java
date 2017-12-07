package logic.convert.adjective;

import logic.convert.SuffixReplace;

public final class AdjConvert {

	private static String with(String value) {
		if(value.equals("いい")) {
			return "よ";
		}
		SuffixReplace replace = new SuffixReplace();
		replace.add("い", "");
		return replace.replace(value);
	}
	
	public static String 基本型(String value) {
		return value;
	}
	
	public static String 终止型(String value) {
		return value;
	}
	
	public static String 连体型(String value) {
		return value;
	}
	
	public static String 未然型1(String value) {
		return with(value) + "かろ";
	}
	
	public static String 未然型2(String value) {
		return with(value) + "から";
	}

	public static String 连用型1(String value) {
		return with(value) + "く";
	}

	public static String 连用型2(String value) {
		return with(value) + "かっ";
	}

	public static String 假定型(String value) {
		return with(value) + "けれ";
	}

	public static String 意志型(String value) {
		return 未然型1(value) + "う";
	}

	public static String 推量型(String value) {
		return 未然型1(value) + "う";
	}

	public static String 连用型T(String value) {
		return 连用型1(value) + "て";
	}

	public static String 连用型CJ(String value) {
		return 连用型1(value) + "ちゃ";
	}

	public static String 连用型D(String value) {
		return 连用型2(value) + "た";
	}

	public static String 假定型B(String value) {
		return 假定型(value) + "ば";
	}

	public static String S(String value) {
		return with(value) + "さ";
	}

}
