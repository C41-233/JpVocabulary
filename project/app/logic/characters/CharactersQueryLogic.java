package logic.characters;

import java.util.List;

import core.model.ConcatSplit;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import core.model.hql.Or;
import models.Character;
import models.NotionalWordValue;
import po.ICharacter;
import po.INotionalWordValue;

public final class CharactersQueryLogic {

	public static List<INotionalWordValue> findNotionalWordsByCharacter(String jpValue) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("value", jpValue));

		HQLResult result = hql.end();
		return NotionalWordValue.find(result.select, result.params).fetch();
	}

	public static List<ICharacter> findCharactersByPinyin(String pinyin){
		HQL hql = HQL.begin();
		hql.where(Like.contains("pinyins", ConcatSplit.getToken(pinyin)));
		hql.orderBy("jp");
		
		HQLResult result = hql.end();
		return Character.find(result.select, result.params).fetch();
	}

	public static ICharacter getCharacer(long id) {
		return Character.findById(id);
	}

	public static ICharacter findCharacter(String jp) {
		return Character.find("jp=?1", jp).first();
	}

	public static List<ICharacter> findCharactersByCn(String cn) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("cn", cn));
		
		HQLResult result = hql.end();
		return Character.find(result.select, result.params).fetch();
	}

	public static boolean hasCharacter(String jp) {
		return Character.find("jp=?1", jp).first() != null;
	}

	public static List<ICharacter> findCharacterBySearch(String q) {
		HQL hql = HQL.begin();
		
		Or or = new Or();
		or.or("jp=?", q);
		or.or(Like.contains("cn", q));
		hql.where(or);
		
		HQLResult rst = hql.end();
		return Character.find(rst.select, rst.params).fetch();
	}

}
