package logic;

import java.util.List;

import base.utility.Chars;
import base.utility.linq.CharPredicates;
import base.utility.linq.Linq;

public final class LogicValidate {

	public static boolean isValidJpValue(String jp) {
		return jp!=null && jp.length() == 1 && Chars.isCJKUnifiedIdeograph(jp.codePointAt(0));
	}

	public static boolean isValidCnValue(String cn) {
		return cn!=null && cn.length() == 1 && Chars.isCJKUnifiedIdeograph(cn.codePointAt(0));
	}
	
	public static boolean isValidSyllable(String syllable) {
		return syllable!=null && Linq.from(syllable).notAll(CharPredicates.or(Chars::isHiragana, Chars::isKatakana));
	}

	public static boolean isValidPinyin(String pinyin) {
		return pinyin!=null && pinyin.matches("^[a-z]+[1-4]$");
	}

}
