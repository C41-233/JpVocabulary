package logic.words;

import java.util.List;

import base.utility.Chars;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
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

	private static void raiseIfNotValidKatakanaWord(String value) {
		if(value == null || value.length() == 0 || Chars.isKatakana(value.codePointAt(0))==false) {
			raise("不是合法的片假名词：%s", value);
		}
	}

}
