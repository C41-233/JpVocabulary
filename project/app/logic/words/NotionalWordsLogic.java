package logic.words;

import java.util.List;

import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import models.NotionalWordValue;
import po.INotionalWordValue;

public final class NotionalWordsLogic {

	public static List<INotionalWordValue> findNotionalWordValuesByIndex(String index){
		HQL hql = HQL.begin();
		hql.where(Like.contains("index", "|"+index+"|"));
		HQLResult result = hql.end();
		
		return NotionalWordValue.find(result.select, result.params).fetch();
	}
	
}
