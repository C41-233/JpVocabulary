package logic.characters;

import java.util.List;

import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.Character;
import po.ICharacter;

public final class CharactersLogic extends LogicBase{

	private CharactersLogic() {}
	
	public static List<ICharacter> findCharactersByIndex(String index){
		HQL hql = HQL.begin();
		hql.where(Like.contains("pinyins", "|"+index+"|"));
		HQLResult result = hql.end();
		return Character.find(result.select, result.params).fetch();
	}

	public static ICharacter getCharacer(long id) {
		return Character.findById(id);
	}
	
	public static ICharacter findCharacter(String jp) {
		return Character.find("jp=?1", jp).first();
	}
	
	public static boolean hasCharacter(String jp) {
		return Character.find("jp=?1", jp).first() != null;
	}
	
	public static ICharacter createCharacter(String jp, String cn, List<String> pinyins) {
		raiseIfNotValidJpValue(jp);
		raiseIfNotValidCnValue(cn);
		raiseIfNotValidPinyins(pinyins);
		raiseIfCharacterAlreadyExist(jp);
		
		Character character = new Character();
		character.setJpValue(jp);
		character.setCnValue(cn);
		character.setPinyins(pinyins);
		character.save();
		return character;
	}

	public static void deleteCharacater(long id) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		character.delete();
	}

	public static void AddSyllable(long id, String syllable) {
		raiseIfNotValidSyllable(syllable);
		
		Character character = getCharacterOrRaiseIfNotFound(id);
		
		//TODO
	}

	public static void UpdateJp(long id, String jp) {
		raiseIfNotValidJpValue(jp);
		raiseIfCharacterAlreadyExist(jp);

		Character character = getCharacterOrRaiseIfNotFound(id);
		character.setJpValue(jp);
		character.save();
	}

	public static void UpdateCn(long id, String cn) {
		raiseIfNotValidCnValue(cn);

		Character character = getCharacterOrRaiseIfNotFound(id);
		character.setCnValue(cn);
		character.save();
	}

	public static void UpdatePinyins(long id, List<String> pinyins) {
		raiseIfNotValidPinyins(pinyins);
		
		Character character = getCharacterOrRaiseIfNotFound(id);
		character.setPinyins(pinyins);
		character.save();
		
		//更新其他表的汉字索引
	}

	private static Character getCharacterOrRaiseIfNotFound(long id) {
		Character character = Character.findById(id);
		if(character == null) {
			raise("汉字不存在: id=%d", id);
		}
		return character;
	}

	private static void raiseIfCharacterAlreadyExist(String ch) {
		if(hasCharacter(ch)) {
			raise("汉字已存在：%s", ch);
		}
	}
	
}
