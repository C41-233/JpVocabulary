package logic.words;

import java.util.List;

import base.utility.Chars;
import base.utility.function.Predicates;
import base.utility.linq.Linq;
import logic.LogicBase;
import logic.LogicValidate;
import logic.pinyins.WordQueryIndex;
import models.VerbWord;
import models.VerbWordValue;
import po.IVerbWord;
import po.VerbWordType;

public final class VerbWordsLogic extends LogicBase{

	public static IVerbWord create(List<String> values, List<String> meanings, List<VerbWordType> types) {
		//必须至少有一个注音
		if(Linq.from(values).notExist(LogicValidate::isValidSyllable)) {
			raise("动词必须至少有一个读音");
		}
		
		for(String value : values) {
			//检查每个单词都存在索引
			if(WordQueryIndex.getWordQueryIndex(value).size() == 0) {
				raise("动词无法生成索引，需要先添加汉字：%s", value);
			}
			//检查非读音的单词是否重复
			if(LogicValidate.isValidSyllable(value)==false && hasVerbWordValue(value)) {
				raise("动词已存在：%s", value);
			}
			
			//合法的动词
			raiseIfNotValidVerbValue(value);
		}
		
		if(Linq.from(types).notExist(t->t==VerbWordType.一类动词 || t==VerbWordType.二类动词 || t==VerbWordType.カ变动词 || t==VerbWordType.ラ变动词)) {
			raise("至少包含一个词性", types);
		}
		
		VerbWord word = new VerbWord();
		word.setMeanings(meanings);
		word.setTypes(types);
		word.save();
		
		for(String value : values) {
			VerbWordValue verbValue = new VerbWordValue();
			verbValue.setValue(value);
			verbValue.setVerbWordId(word.getId());
			verbValue.setIndexes(WordQueryIndex.getWordQueryIndex(value));
			
			verbValue.save();
		}
		
		return word;
	}

	public static boolean hasVerbWordValue(String value) {
		return VerbWordValue.find("value=?1", value).first() != null;
	}

	private static void raiseIfNotValidVerbValue(String value) {
		if(
			value == null || value.length() == 0
			|| Linq.from(value).isAll(Predicates.or(Chars::isCJKUnifiedIdeograph, Chars::isHiragana)) == false
			|| Linq.from(value).isAll(Chars::isCJKUnifiedIdeograph)
		) {
			raise("不是合法的动词：%s", value);
		}
	}
	
}
