package logic.words;

import java.util.List;

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

public final class AdjectiveWordsLogic extends LogicBase{

	public static List<IAdjectiveWordValue> findAdjectiveWordValuesByIndex(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", ConcatSplit.getToken(index)));
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
