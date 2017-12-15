package logic.characters;

import java.util.List;

import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import core.model.hql.Or;
import models.AdjectiveWordValue;
import models.Character;
import models.ConcatSplit;
import models.NotionalWordValue;
import models.VerbWordValue;
import po.IAdjectiveWordValue;
import po.ICharacter;
import po.INotionalWordValue;
import po.IVerbWordValue;

public final class CharactersQueryLogic {

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

	public static List<INotionalWordValue> findNotionalWordsByCharacter(String jpValue) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("value", jpValue));
		hql.orderBy("value");
		
		HQLResult result = hql.end();
		List<INotionalWordValue> list = NotionalWordValue.find(result.select, result.params).fetch();
		return list;
	}

	public static List<IVerbWordValue> findVerbWordsByCharacter(String jpValue) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("value", jpValue));
		hql.orderBy("value");
		
		HQLResult result = hql.end();
		List<IVerbWordValue> list = VerbWordValue.find(result.select, result.params).fetch();
		return list;
	}

	public static List<IAdjectiveWordValue> findAdjWordsByCharacter(String jpValue) {
		HQL hql = HQL.begin();
		hql.where(Like.contains("value", jpValue));
		hql.orderBy("value");
		
		HQLResult result = hql.end();
		List<IAdjectiveWordValue> list = AdjectiveWordValue.find(result.select, result.params).fetch();
		return list;
	}

}
