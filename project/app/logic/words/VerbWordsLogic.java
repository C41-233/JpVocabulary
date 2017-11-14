package logic.words;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import base.utility.Chars;
import base.utility.collection.Iterables;
import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import logic.LogicValidate;
import logic.pinyins.WordQueryIndex;
import models.VerbWord;
import models.VerbWordValue;
import po.IVerbWord;
import po.IVerbWordValue;
import po.VerbWordType;
import po.WordFixword;

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
	
	public static List<IVerbWordValue> findVerbWordValuesByIndex(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", ConcatSplit.getToken(index)));
		hql.orderBy("value");
		HQLResult result = hql.end();
		
		return VerbWordValue.find(result.select, result.params).fetch();
	}

	public static List<IVerbWordValue> findVerbWordValuesBySearch(String query) {
		return VerbWordValue.find("value=?1", query).fetch();
	}

	public static IVerbWord create(List<String> values, List<String> meanings, List<VerbWordType> types) {
		//必须至少有一个注音
		if(Linq.from(values).notExist(LogicValidate::isValidSyllable)) {
			raise("动词必须至少有一个读音");
		}
		
		for(String value : values) {
			raiseIfNotExistQueryIndex(value);
			raiseIfNotValidVerbWordValue(value);
		}
		
		if(Linq.from(types).notExist(t->t==VerbWordType.一类动词 || t==VerbWordType.二类动词 || t==VerbWordType.サ变动词 ||t==VerbWordType.カ变动词 || t==VerbWordType.ラ变动词)) {
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

	public static void updateType(long id, VerbWordType type, boolean value) {
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		Set<VerbWordType> types = Linq.from(word.getTypes()).toSet();
		if(value) {
			types.add(type);
		}
		else {
			types.remove(type);
		}
		
		if(types.contains(VerbWordType.一类动词) == false
			&& types.contains(VerbWordType.二类动词) == false
			&& types.contains(VerbWordType.サ变动词) == false
			&& types.contains(VerbWordType.カ变动词) == false
			&& types.contains(VerbWordType.ラ变动词) == false
		) {
			raise("至少包含一个基本词性");
		}
		
		word.setTypes(types);
		word.save();
	}

	public static void addFixword(long id, String value, String meaning) {
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getFixwords()).isExist(w->w.getValue().equals(value))) {
			raise("词组已存在：%s", value);
		}
		
		word.addFixword(new WordFixword(value, meaning));
		word.save();
	}

	public static void deleteFixword(long id, String value) {
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getFixwords()).notExist(w->w.getValue().equals(value))) {
			raise("词组不存在：%s", value);
		}
		word.deleteFixword(value);
		word.save();
	}

	public static void updateFixword(long id, String value, String meaning) {
		VerbWord word = getVerbWordOrRaiseIfNotExist(id);
		if(Linq.from(word.getFixwords()).notExist(w->w.getValue().equals(value))) {
			raise("词组不存在：%s", value);
		}
		word.deleteFixword(value);
		word.addFixword(new WordFixword(value, meaning));
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
			|| LogicValidate.isValidJpBasicWord(value) == false
			|| LogicValidate.isCharacterWord(value)
		) {
			raise("不是合法的动词：%s", value);
		}
	}

	public static final Comparator<String> ValueComparator = (t1, t2)->{
		boolean b1 = Chars.isCJKUnifiedIdeograph(t1.charAt(0));
		boolean b2 = Chars.isCJKUnifiedIdeograph(t2.charAt(0));
		if(b1 && !b2) {
			return -1;
		}
		if(b2 && !b1) {
			return 1;
		}
		return Comparators.compare(t1, t2);
	};

}
