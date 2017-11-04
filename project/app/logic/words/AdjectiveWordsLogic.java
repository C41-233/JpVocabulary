package logic.words;

import java.util.List;
import java.util.Set;

import base.utility.collection.Iterables;
import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import logic.LogicValidate;
import logic.pinyins.WordQueryIndex;
import models.AdjectiveWord;
import models.AdjectiveWordValue;
import po.AdjectiveWordType;
import po.IAdjectiveWord;
import po.IAdjectiveWordValue;
import po.WordFixword;

public final class AdjectiveWordsLogic extends LogicBase{

	public static IAdjectiveWord getAdjectiveWordAndUpdate(long id) {
		AdjectiveWord word = AdjectiveWord.findById(id);
		if(word == null) {
			return null;
		}

		//更新单词索引
		for(AdjectiveWordValue value : word.getValues()) {
			List<String> indexes = WordQueryIndex.getWordQueryIndex(value.getValue());
			if(Iterables.equals(value.getIndexes(), indexes) == false) {
				value.setIndexes(indexes);
				value.save();
			}
		}
		
		return word;
	}

	public static List<IAdjectiveWordValue> findAdjectiveWordValuesByIndex(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", ConcatSplit.getToken(index)));
		hql.orderBy("value");
		HQLResult result = hql.end();
		
		return AdjectiveWordValue.find(result.select, result.params).fetch();
	}

	public static List<IAdjectiveWordValue> findAdjectiveWordValuesByAlpha(char ch) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", ConcatSplit.Split+Character.toLowerCase(ch)));
		hql.orderBy("value");
		HQLResult result = hql.end();
		
		return AdjectiveWordValue.find(result.select, result.params).fetch();
	}

	public static boolean hasAdjectiveWordValue(String value) {
		return AdjectiveWordValue.find("value=?1", value).first() != null;
	}

	public static IAdjectiveWord create(List<String> values, List<String> meanings, List<AdjectiveWordType> types) {
		//必须至少有一个注音
		if(Linq.from(values).notExist(LogicValidate::isValidSyllable)) {
			raise("动词必须至少有一个读音");
		}
		
		for(String value : values) {
			raiseIfNotExistQueryIndex(value);
			raiseIfDuplicateAdjectiveWordValue(value);
			raiseIfNotValidAdjectiveWordValue(value);
		}
		
		AdjectiveWord word = new AdjectiveWord();
		word.setMeanings(meanings);
		word.setTypes(types);
		word.save();
		
		for(String value : values) {
			AdjectiveWordValue adjValue = new AdjectiveWordValue();
			adjValue.setValue(value);
			adjValue.setAdjectiveWordId(word.getId());
			adjValue.setIndexes(WordQueryIndex.getWordQueryIndex(value));
			
			adjValue.save();
		}
		
		return word;
	}

	public static void delete(long id) {
		AdjectiveWord word = getAdjectiveWordOrRaiseIfNotExist(id);
		for(AdjectiveWordValue value : word.getValues()) {
			value.delete();
		}
		word.delete();
	}
	
	public static void addValue(long id, String value) {
		raiseIfNotValidAdjectiveWordValue(value);
		raiseIfDuplicateAdjectiveWordValue(value);
		raiseIfNotExistQueryIndex(value);
		
		AdjectiveWord word = getAdjectiveWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getValues()).isExist(v->v.getValue().equals(value))) {
			raise("重复添加形容词：%s", value);
		}
		
		AdjectiveWordValue wordValue = new AdjectiveWordValue();
		wordValue.setValue(value);
		wordValue.setAdjectiveWordId(word.getId());
		wordValue.setIndexes(WordQueryIndex.getWordQueryIndex(value));
		wordValue.save();
	}
	
	public static void deleteValue(long id) {
		AdjectiveWordValue value = getAdjectiveWordValueOrRaiseIfNotExist(id);
		if(LogicValidate.isValidSyllable(value.getValue()) && Linq.from(value.getWord().getSyllables()).count() == 1) {
			raise("不能删除全部注音");
		}
		value.delete();
	}

	public static void updateMeanings(long id, List<String> meanings) {
		AdjectiveWord word = getAdjectiveWordOrRaiseIfNotExist(id);
		word.setMeanings(meanings);
		word.save();
	}

	public static void updateType(long id, AdjectiveWordType type, boolean value) {
		AdjectiveWord word = getAdjectiveWordOrRaiseIfNotExist(id);
		Set<AdjectiveWordType> types = Linq.from(word.getTypes()).toSet();
		if(value) {
			types.add(type);
		}
		else {
			types.remove(type);
		}
		
		word.setTypes(types);
		word.save();
	}

	public static void addFixword(long id, String value, String meaning) {
		AdjectiveWord word = getAdjectiveWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getFixwords()).isExist(w->w.getValue().equals(value))) {
			raise("词组已存在：%s", value);
		}
		
		word.addFixword(new WordFixword(value, meaning));
		word.save();
	}

	public static void deleteFixword(long id, String value) {
		AdjectiveWord word = getAdjectiveWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getFixwords()).notExist(w->w.getValue().equals(value))) {
			raise("词组不存在：%s", value);
		}
		word.deleteFixword(value);
		word.save();
	}
	
	private static AdjectiveWord getAdjectiveWordOrRaiseIfNotExist(long id) {
		AdjectiveWord word = AdjectiveWord.findById(id);
		if(word == null) {
			raise("形容词不存在：id=%d", id);
		}
		return word;
	}
	
	private static AdjectiveWordValue getAdjectiveWordValueOrRaiseIfNotExist(long id) {
		AdjectiveWordValue value = AdjectiveWordValue.findById(id);
		if(value == null) {
			raise("形容词不存在：id=%d", id);
		}
		return value;
	}
	
	private static void raiseIfNotValidAdjectiveWordValue(String value) {
		if(
			value == null
			|| value.endsWith("い") == false
			|| LogicValidate.isValidJpBasicWord(value) == false
		) {
			raise("不是合法的形容词：%s", value);
		}
	}

	private static void raiseIfDuplicateAdjectiveWordValue(String value) {
		//检查非读音的单词是否重复
		if(LogicValidate.isValidSyllable(value)==false && hasAdjectiveWordValue(value)) {
			raise("形容词已存在：%s", value);
		}
	}

}
