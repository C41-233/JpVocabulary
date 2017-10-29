package logic.words;

import java.util.List;

import core.model.ConcatSplit;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.AdjectiveWordValue;
import po.IAdjectiveWordValue;

public final class AdjectiveWordsLogic extends LogicBase{

	public static List<IAdjectiveWordValue> findAdjectiveWordValuesByIndex(String index) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", ConcatSplit.getToken(index)));
		hql.orderBy("value");
		HQLResult result = hql.end();
		
		return AdjectiveWordValue.find(result.select, result.params).fetch();
	}

}
