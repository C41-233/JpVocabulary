package logic.words;

import java.util.List;

import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.KatakanaWord;

public final class KatakanaWordsLogic extends LogicBase{

	public static List<KatakanaWord> getKatakanaWordsByKatakana(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.startWith("value", index));
		HQLResult rst = hql.end();
		
		return KatakanaWord.find(rst.select, rst.params).fetch();
	}
	
}
