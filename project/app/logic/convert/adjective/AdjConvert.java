package logic.convert.adjective;

import java.util.HashMap;

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

	private final static HashMap<Character, String> GZRTable = new HashMap<>();
	
	static {
		//ア->オうござる
		GZRTable.put('あ', "おうござる");
		GZRTable.put('か', "こうござる");
		GZRTable.put('さ', "そうござる");
		GZRTable.put('た', "とうござる");
		GZRTable.put('な', "のうござる");
		GZRTable.put('は', "ほうござる");
		GZRTable.put('ま', "もうござる");
		GZRTable.put('や', "ようござる");
		GZRTable.put('ら', "ろうござる");
		GZRTable.put('わ', "おうござる");
		GZRTable.put('が', "ごうござる");
		GZRTable.put('ざ', "ぞうござる");
		GZRTable.put('だ', "どうござる");
		GZRTable.put('ば', "ぼうござる");
		GZRTable.put('ぱ', "ぽうござる");
		//イ->イゅうござる
		GZRTable.put('い', "いゅござる");
		GZRTable.put('き', "きゅござる");
		GZRTable.put('し', "しゅござる");
		GZRTable.put('ち', "ちゅござる");
		GZRTable.put('に', "にゅござる");
		GZRTable.put('ひ', "ひゅござる");
		GZRTable.put('み', "みゅござる");
		GZRTable.put('り', "りゅござる");
		GZRTable.put('ぎ', "ぎゅござる");
		GZRTable.put('ぢ', "ぢゅござる");
		GZRTable.put('び', "びゅござる");
		GZRTable.put('ぴ', "ぴゅござる");
		//ウ->ウうござる
		GZRTable.put('う', "ううござる");
		GZRTable.put('く', "くうござる");
		GZRTable.put('す', "すうござる");
		GZRTable.put('つ', "つうござる");
		GZRTable.put('ぬ', "ぬうござる");
		GZRTable.put('ふ', "ふうござる");
		GZRTable.put('む', "むうござる");
		GZRTable.put('ゆ', "ゆうござる");
		GZRTable.put('る', "るうござる");
		GZRTable.put('ぐ', "ぐうござる");
		GZRTable.put('ず', "ずうござる");
		GZRTable.put('づ', "づうござる");
		GZRTable.put('ぶ', "ぶうござる");
		GZRTable.put('ぷ', "ぷうござる");
		//オ->オうござる
		GZRTable.put('お', "おうござる");
		GZRTable.put('こ', "こうござる");
		GZRTable.put('そ', "そうござる");
		GZRTable.put('と', "とうござる");
		GZRTable.put('の', "のうござる");
		GZRTable.put('ほ', "ほうござる");
		GZRTable.put('も', "もうござる");
		GZRTable.put('よ', "ようござる");
		GZRTable.put('ろ', "ろうござる");
		GZRTable.put('ご', "ごうござる");
		GZRTable.put('ぞ', "ぞうござる");
		GZRTable.put('ど', "どうござる");
		GZRTable.put('ぼ', "ぼうござる");
		GZRTable.put('ぽ', "ぽうござる");
	}
	
	
	public static String GZR(String value, String syllable) {
		String preValue = with(value);
		String preSyllable = with(syllable);
		
		if(syllable.equals("いい")) {
			return preValue + "ろしゅうござる";
		}
		
		char lastValue = preValue.charAt(preValue.length()-1);
		char lastSyllable = preSyllable.charAt(preSyllable.length()-1);
		
		String prefixValue = preValue.substring(0, preValue.length() - 1);
		
		String convert = GZRTable.get(lastSyllable);
		if(convert == null) {
			return null;
		}
		
		if(lastValue == lastSyllable) {
			return prefixValue + convert;
		}
		
		return preValue + convert.substring(1);
	}
	
}
