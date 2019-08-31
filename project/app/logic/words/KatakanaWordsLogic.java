package logic.words;

import java.util.List;
import java.util.Set;

import c41.utility.Chars;
import c41.utility.linq.Linq;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.ConcatSplit;
import models.KatakanaWord;
import po.IKatakanaWord;
import po.KatakanaWordContext;
import po.KatakanaWordType;

public final class KatakanaWordsLogic extends LogicBase{

	public static List<KatakanaWord> findKatakanaWordsByKatakana(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.startWith("value", index));
		hql.orderBy("value");
		HQLResult rst = hql.end();
		
		return KatakanaWord.find(rst.select, rst.params).fetch();
	}

	public static List<KatakanaWord> findKatakanaWordsByAlpha(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.startWith("alias", index));
		hql.orderBy("alias");
		HQLResult rst = hql.end();
		
		return KatakanaWord.find(rst.select, rst.params).fetch();
	}

	public static List<IKatakanaWord> findKatakanaWordsBySearch(String query) {
		return KatakanaWord.find("value=?1", query).fetch();
	}

	public static List<IKatakanaWord> findKatakanaWordsByAlias(String query) {
		return KatakanaWord.find("alias=?1 or alias like ?2 or alias like ?3 or alias like ?4", query, "% " + query + " %", "% " + query, query + " %").fetch();
	}

	public static List<IKatakanaWord> findQuantifierKatakanaWords() {
		HQL hql = HQL.begin();
		hql.where(Like.contains("types", ConcatSplit.getToken(KatakanaWordType.量词.toString())));
		hql.orderBy("value");
		HQLResult rst = hql.end();
		return KatakanaWord.find(rst.select, rst.params).fetch();
	}

	public static IKatakanaWord getKatakanawordAndUpdate(long id) {
		return KatakanaWord.findById(id);
	}

	public static IKatakanaWord create(String value, List<String> meanings, List<KatakanaWordType> types, String alias, KatakanaWordContext context) {
		raiseIfNotValidKatakanaWord(value);
		
		KatakanaWord word = new KatakanaWord();
		word.setValue(value);
		word.setMeanings(meanings);
		word.setTypes(types);
		word.setAlias(alias);
		word.setContext(context);
		word.save();
		return word;
	}

	public static void delete(long id) {
		KatakanaWord word = getKatakanaWordOrRaiseIfNotExist(id);
		word.delete();
	}

	public static void updateValue(long id, String value) {
		raiseIfNotValidKatakanaWord(value);
		
		KatakanaWord word = getKatakanaWordOrRaiseIfNotExist(id);
		word.setValue(value);
		word.save();
	}

	public static void updateMeanings(long id, List<String> meanings) {
		KatakanaWord word = getKatakanaWordOrRaiseIfNotExist(id);
		word.setMeanings(meanings);
		word.save();
	}

	public static void updateType(long id, KatakanaWordType type, boolean value) {
		KatakanaWord word = getKatakanaWordOrRaiseIfNotExist(id);
		Set<KatakanaWordType> types = Linq.from(word.getTypes()).toSet();
		if(value) {
			types.add(type);
		}
		else {
			types.remove(type);
		}
		
		if(types.size() == 0) {
			raise("至少存在一个词性");
		}
		
		word.setTypes(types);
		word.save();
	}

	public static void updateAlias(long id, String alias) {
		KatakanaWord word = getKatakanaWordOrRaiseIfNotExist(id);
		word.setAlias(alias);
		word.save();
	}

	public static void updateContext(long id, KatakanaWordContext context) {
		KatakanaWord word = getKatakanaWordOrRaiseIfNotExist(id);
		word.setContext(context);
		word.save();
	}

	private static KatakanaWord getKatakanaWordOrRaiseIfNotExist(long id) {
		KatakanaWord word = KatakanaWord.findById(id);
		if(word == null) {
			raise("片假名词不存在：id=%d", id);
		}
		return word;
	}
	
	private static void raiseIfNotValidKatakanaWord(String value) {
		if(value == null || value.length() == 0 || Chars.isKatakana(value.codePointAt(0))==false) {
			raise("不是合法的片假名词：%s", value);
		}
	}
	
}
