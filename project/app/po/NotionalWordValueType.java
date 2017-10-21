package po;

import base.utility.Chars;
import base.utility.function.Predicates;
import base.utility.linq.Linq;
import logic.LogicValidate;

public enum NotionalWordValueType {

	Syllable(0), //纯假名的注音
	
	Character(1), //纯汉字词
	
	Mixed(2); //汉字与假名的混合词
	
	private final int value;
	
	NotionalWordValueType(int value) {
		this.value = value;
	}
	
	public static NotionalWordValueType getWordValueType(String value) {
		if(LogicValidate.isValidSyllable(value)) {
			return Syllable;
		}
		if(LogicValidate.isCharacterWord(value)) {
			return Character;
		}
		if(value.length() > 0 && Linq.from(value).isAll(Predicates.or(Chars::isCJKUnifiedIdeograph, Chars::isHiragana))) {
			return Mixed;
		}
		return null;
	}

	public int value() {
		return this.value;
	}
	
}
