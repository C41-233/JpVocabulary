package logic.words;

import java.util.List;

import base.utility.Chars;
import base.utility.collection.Iterables;
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

	public static IVerbWord getVerbWordAndUpdate(long id) {
		VerbWord word = VerbWord.findById(id);
		if(word == null){
			return null;
		}
		
		//更新单词索引
		for(VerbWordValue value : word.getValues()) {
			List<String> indexes = WordQueryIndex.getWordQueryIndex(value.getValue());
			if(Iterables.equals(value.getIndexes(), indexes) == false) {
				value.setIndexes(indexes);
				value.save();
			}
		}
		
		return word;
	}
	
	public static IVerbWord create(List<String> values, List<String> meanings, List<VerbWordType> types) {
		//必须至少有一个注音
		if(Linq.from(values).notExist(LogicValidate::isValidSyllable)) {
			raise("动词必须至少有一个读音");
		}
		
		for(String value : values) {
			raiseIfNotExistQueryIndex(value);
			raiseIfDuplicateVerbWordValue(value);
			raiseIfNotValidVerbWordValue(value);
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

	public static void delete(long id) {
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		for(VerbWordValue value : word.getValues()) {
			value.delete();
		}
		word.delete();
	}

	public static void addValue(long id, String value) {
		raiseIfNotValidVerbWordValue(value);
		raiseIfDuplicateVerbWordValue(value);
		raiseIfNotExistQueryIndex(value);
		
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getValues()).isExist(v->v.getValue().equals(value))) {
			raise("重复添加动词：%s", value);
		}
		
		VerbWordValue verbWordValue = new VerbWordValue();
		verbWordValue.setValue(value);
		verbWordValue.setVerbWordId(word.getId());
		verbWordValue.setIndexes(WordQueryIndex.getWordQueryIndex(value));
		verbWordValue.save();
	}

	public static void deleteValue(long id) {
		VerbWordValue value = getVerbWordValueOrRaiseIfNotExist(id);
		if(LogicValidate.isValidSyllable(value.getValue()) && Linq.from(value.getWord().getSyllables()).count() == 1) {
			raise("不能删除全部注音");
		}
		value.delete();
	}

	public static void updateMeanings(long id, List<String> meanings) {
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		word.setMeanings(meanings);
		word.save();
	}

	private static VerbWord getVerbWordOrRaiseIfNotExist(long id) {
		VerbWord word = VerbWord.findById(id);
		if(word == null) {
			raise("不存在动词：id=%d", id);
		}
		return word;
	}
	
	private static VerbWordValue getVerbWordValueOrRaiseIfNotExist(long id) {
		VerbWordValue value = VerbWordValue.findById(id);
		if(value == null) {
			raise("不存在动词：id=%d", id);
		}
		return value;
	}
	
	private static void raiseIfNotValidVerbWordValue(String value) {
		if(
			value == null || value.length() == 0
			|| Linq.from(value).isAll(Predicates.or(Chars::isCJKUnifiedIdeograph, Chars::isHiragana)) == false
			|| Linq.from(value).isAll(Chars::isCJKUnifiedIdeograph)
		) {
			raise("不是合法的动词：%s", value);
		}
	}

	private static void raiseIfDuplicateVerbWordValue(String value) {
		//检查非读音的单词是否重复
		if(LogicValidate.isValidSyllable(value)==false && hasVerbWordValue(value)) {
			raise("动词已存在：%s", value);
		}
	}
	
}
