package po;

import base.utility.Chars;
import base.utility.lambda.predicate.Predicates;
import base.utility.linq.Linq;
import logic.LogicValidate;

public enum NotionalWordValueType {

	Syllable(0, "注音"), //纯假名的注音，只能是平假名
	
	Character(1, "汉字词"), //纯汉字词
	
	Mixed(2, "平假名词"); //汉字与假名的混合词，假名可以是平假名也可以是片假名，但不可以是片假名开头
	
	private final int value;
	private final String name;
	
	NotionalWordValueType(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static NotionalWordValueType valueOf(int value) {
		return Linq.from(NotionalWordValueType.values()).findFirst(e->e.value == value);
	}
	
	public static NotionalWordValueType getWordValueType(String value) {
		if(value.length() == 0) {
			return null;
		}
		
		//纯平假名
		if(LogicValidate.isValidSyllable(value)) {
			return Syllable;
		}
		
		//纯汉字
		if(LogicValidate.isCharacterWord(value)) {
			return Character;
		}
		
		//排除片假名开头的词
		if(Chars.isKatakana(value.codePointAt(0))) {
			return null;
		}
		
		//汉字与假名混合
		if(Linq.from(value).isAll(Predicates.or(Chars::isCJKUnifiedIdeograph, Chars::isHiragana, Chars::isKatakana, c->c=='々'))) {
			return Mixed;
		}
		
		return null;
	}

	public int value() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
}
