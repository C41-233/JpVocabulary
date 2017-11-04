package logic;

import java.util.HashSet;
import java.util.List;

import base.utility.Strings;
import logic.pinyins.WordQueryIndex;

public abstract class LogicBase{

	protected static void raise(String format, Object...args) {
		throw new LogicException(Strings.format(format, args));
	}
	
	protected static void raiseIfNotValidSyllable(String syllable) {
		if(LogicValidate.isValidSyllable(syllable) == false) {
			raise("不符合读音规则: %s", syllable);
		}
	}

	protected static void raiseIfNotValidJpValue(String jp) {
		if(LogicValidate.isValidJpValue(jp) == false) {
			raise("不是汉字：%s", jp);
		}
	}

	protected static void raiseIfNotValidCnValue(String cn) {
		if(LogicValidate.isValidCnValue(cn) == false) {
			raise("不是汉字：%s", cn);
		}
	}
	
	protected static void raiseIfNotValidPinyins(List<String> pinyins) {
		HashSet<String> values = new HashSet<>();
		for (String pinyin : pinyins) {
			if(LogicValidate.isValidPinyin(pinyin) == false) {
				raise("不符合拼音规则: %s", pinyin);
			}
			if(values.contains(pinyin)) {
				raise("重复的数据: %s", pinyin);
			}
			values.add(pinyin);
		}
	}
		
	protected static void raiseIfNotExistQueryIndex(String value) {
		if(WordQueryIndex.getWordQueryIndex(value).size() == 0) {
			raise("汉字无法生成索引，需要先添加汉字：%s", value);
		}
	}
	
}
