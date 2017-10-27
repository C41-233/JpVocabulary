package logic.words;

import java.util.List;
import java.util.Set;

import base.utility.linq.Linq;
import logic.LogicBase;
import logic.LogicValidate;
import logic.pinyins.WordQueryIndex;
import models.NotionalWord;
import models.NotionalWordValue;
import po.INotionalWord;
import po.NotionalWordType;
import po.NotionalWordValueType;

public final class NotionalWordsUpdateLogic extends LogicBase{

	public static INotionalWord create(List<String> values, List<String> meanings, List<NotionalWordType> types) {
		//必须至少有一个注音
		if(Linq.from(values).notExist(LogicValidate::isValidSyllable)) {
			raise("基本词必须至少有一个读音");
		}
		
		for(String value : values) {
			//检查每个单词都存在索引
			if(WordQueryIndex.getWordQueryIndex(value).size() == 0) {
				raise("单词无法生成索引，需要先添加汉字：%s", value);
			}
			//检查非读音的单词是否重复
			if(LogicValidate.isValidSyllable(value)==false && NotionalWordsQueryLogic.hasNotionalWordValue(value)) {
				raise("单词已存在：%s", value);
			}
			//必须是合法的基本词
			if(NotionalWordValueType.getWordValueType(value) == null) {
				raise("不是合法的基本词：%s", value);
			}
		}
		
		if(types.size() == 0) {
			raise("至少包含一个词性", types);
		}
		
		NotionalWord word = new NotionalWord();
		word.setMeanings(meanings);
		word.setTypes(types);
		word.save();
		
		for(String value : values) {
			NotionalWordValue notionalWordValue = new NotionalWordValue();
			notionalWordValue.setValue(value);
			notionalWordValue.setNotionalWordId(word.getId());
			notionalWordValue.setType(NotionalWordValueType.getWordValueType(value));
			notionalWordValue.setIndexes(WordQueryIndex.getWordQueryIndex(value));
			
			notionalWordValue.save();
		}
		
		return word;
	}
	
	public static void delete(long id) {
		NotionalWord word = getNotionalWordOrRaiseIfNotFound(id);
		for(NotionalWordValue value : word.getValues()) {
			value.delete();
		}
		word.delete();
	}
	
	public static void addValue(long id, String value) {
		NotionalWord word = getNotionalWordOrRaiseIfNotFound(id);
		if(Linq.from(word.getValues()).isExist(v->v.getValue().equals(value))) {
			raise("重复添加单词：%s", value);
		}
		
		NotionalWordValueType type = NotionalWordValueType.getWordValueType(value);
		if(type == null) {
			raise("不合法的基本词：%s", value);
		}
		
		List<String> indexes = WordQueryIndex.getWordQueryIndex(value);
		if(indexes.isEmpty()) {
			raise("无法创建索引，请先添加汉字：%s", value);
		}
		
		NotionalWordValue notionalWordValue = new NotionalWordValue();
		notionalWordValue.setValue(value);
		notionalWordValue.setType(type);
		notionalWordValue.setNotionalWordId(word.getId());
		notionalWordValue.setIndexes(indexes);
		notionalWordValue.save();
	}

	public static void deleteValue(long id) {
		NotionalWordValue value = getNotionalWordValueOrRaiseIfNotFound(id);
		if(value.getType() == NotionalWordValueType.Syllable && Linq.from(value.getSyllables()).count() == 1) {
			raise("不能删除全部注音");
		}
		value.delete();
	}
	
	public static void updateMeanings(long id, List<String> meanings) {
		NotionalWord word = getNotionalWordOrRaiseIfNotFound(id);
		word.setMeanings(meanings);
		word.save();
	}

	public static void updateType(long id, NotionalWordType type, boolean value) {
		NotionalWord word = getNotionalWordOrRaiseIfNotFound(id);
		Set<NotionalWordType> types = Linq.from(word.getTypes()).toSet();
		if(value) {
			types.add(type);
		}
		else {
			types.remove(type);
		}
		
		if(types.size() == 0) {
			raise("至少包含一个词性");
		}
		
		word.setTypes(types);
		word.save();
	}
	
	private static NotionalWord getNotionalWordOrRaiseIfNotFound(long id) {
		NotionalWord word = NotionalWord.findById(id);
		if(word == null) {
			raise("不存在单词：id=%d", id);
		}
		return word;
	}

	private static NotionalWordValue getNotionalWordValueOrRaiseIfNotFound(long id) {
		NotionalWordValue value = NotionalWordValue.findById(id);
		if(value == null) {
			raise("不存在单词：id=%d", id);
		}
		return value;
	}
	
}
