package logic.words;

import java.util.List;

import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import models.NotionalWordValue;
import po.INotionalWordValue;

public final class NotionalWordsQueryLogic {

	public static List<INotionalWordValue> findCharacterWordValuesByPinyin(String pinyin){
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", "|"+pinyin+"|"));
		HQLResult result = hql.end();
		
		return NotionalWordValue.find(result.select, result.params).fetch();
	}

	public static boolean hasNotionalWordValue(String value) {
		return NotionalWordValue.find("value=?1", value).first() != null;
	}
	
	
}
