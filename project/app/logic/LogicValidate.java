package logic;

import base.utility.Chars;
import base.utility.function.Predicates;
import base.utility.linq.Linq;

public final class LogicValidate {

	public static boolean isValidJpValue(String jp) {
		if(jp == null || jp.length() != 1) {
			return false;
		}
		
		int ch = jp.codePointAt(0);
		return Chars.isCJKUnifiedIdeograph(ch) || ch=='〇' || ch=='ヶ';
	}

	public static boolean isValidCnValue(String cn) {
		return cn!=null && cn.length() == 1 
				&& (Chars.isCJKUnifiedIdeograph(cn.codePointAt(0)) || cn.charAt(0)=='〇');
	}
	
	public static boolean isValidSyllable(String syllable) {
		return syllable!=null && syllable.length() > 0 && Linq.from(syllable).isAll(
			Predicates.or(Chars::isHiragana, c->c=='ー')
		);
	}

	public static boolean isValidPinyin(String pinyin) {
		return pinyin!=null && pinyin.matches("^[a-z]+[1-4]{0,1}$");
	}

	public static boolean isCharacterWord(String word) {
		return word != null && word.length() >0 && Linq.from(word).isAll(
			Predicates.or(
				Chars::isCJKUnifiedIdeograph, 
				c->c=='々',
				c->c=='〇'
			)
		);
	}
	
	public static boolean isValidJpBasicWord(String word) {
		return word != null 
				&& word.isEmpty() == false 
				&& Linq.from(word).isAll(Predicates.or(Chars::isCJKUnifiedIdeograph, Chars::isHiragana, c->c=='々'));
	}
}
