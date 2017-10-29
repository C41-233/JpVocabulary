package logic.words;

import java.util.List;

import base.utility.collection.Iterables;
import core.model.ConcatSplit;
import core.model.hql.And;
import core.model.hql.Clause;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.In;
import core.model.hql.Like;
import core.model.hql.Or;
import logic.pinyins.WordQueryIndex;
import models.NotionalWord;
import models.NotionalWordValue;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordType;
import po.NotionalWordValueType;

public final class NotionalWordsQueryLogic {

	public static List<INotionalWordValue> findCharacterWordValuesByPinyin(String pinyin){
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", ConcatSplit.getToken(pinyin)));
		hql.orderBy("value");
		HQLResult result = hql.end();
		
		return NotionalWordValue.find(result.select, result.params).fetch();
	}

	public static boolean hasNotionalWordValue(String value) {
		return NotionalWordValue.find("value=?1", value).first() != null;
	}

	public static INotionalWord getNotionalWord(long id) {
		return NotionalWord.findById(id);
	}

	public static INotionalWord getNotionalWordAndUpdate(long id) {
		NotionalWord word = NotionalWord.findById(id);
		if(word == null) {
			return null;
		}
		
		//更新单词的索引
		for(NotionalWordValue value : word.getValues()) {
			List<String> indexes = WordQueryIndex.getWordQueryIndex(value.getValue());
			if(Iterables.equals(value.getIndexes(), indexes) == false) {
				value.setIndexes(indexes);
				value.save();
			}
		}
		
		return word;
	}

	public static List<INotionalWordValue> findHiraganaWordValuesByIndex(String index) {
		HQL hql = HQL.begin();
		And and = new And();
		and.and("type=? or type=?", NotionalWordValueType.Mixed.value(), NotionalWordValueType.Syllable.value());
		and.and(Like.contains("index", ConcatSplit.getToken(index)));
		
		Clause clause = new Clause("id", "NotionalWord", Like.contains("types", ConcatSplit.getToken(NotionalWordType.名词.toString())));
		and.and(new In("refId", clause));
		
		hql.where(and);
		hql.orderBy("value");
		
		HQLResult rst = hql.end();
		return NotionalWordValue.find(rst.select, rst.params).fetch();
	}
	
	public static List<INotionalWordValue> findFunctionWordValuesByIndex(String index){
		HQL hql = HQL.begin();
		
		And and = new And();
		and.and(Like.contains("index", ConcatSplit.getToken(index)));
		
		Or orTypes = new Or();
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.副词.toString())));
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.代词.toString())));
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.连体词.toString())));
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.接续词.toString())));
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.疑问词.toString())));
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.接头词.toString())));
		orTypes.or(Like.contains("types", ConcatSplit.getToken(NotionalWordType.数量词.toString())));
		
		Clause clause = new Clause("id", "NotionalWord", orTypes);
		and.and(new In("refId", clause));
		
		hql.where(and);
		
		HQLResult rst = hql.end();
		return NotionalWordValue.find(rst.select, rst.params).fetch();
	}

	public static List<INotionalWordValue> findAdjNounWordValuesByIndex(String index) {
		HQL hql = HQL.begin();
		
		And and = new And();
		and.and(Like.contains("index", ConcatSplit.getToken(index)));
		
		Clause clause = new Clause("id", "NotionalWord", Like.contains("types", ConcatSplit.getToken(NotionalWordType.形容动词.toString())));
		and.and(new In("refId", clause));
		
		hql.where(and);
		
		HQLResult rst = hql.end();
		return NotionalWordValue.find(rst.select, rst.params).fetch();
	}
	
}
