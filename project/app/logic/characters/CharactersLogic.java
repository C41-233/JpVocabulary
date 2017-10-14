package logic.characters;

import java.util.List;

import base.utility.linq.Linq;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.Character;
import po.CharacterWord;
import po.ICharacter;
import po.ICharacterSyllable;

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
		for(ICharacterSyllable mySyllable : character.getSyllables()) {
			if(mySyllable.getValue().equals(syllable)) {
				raise("读音已存在: %s", syllable);
			}
		}
		character.addSyllable(syllable);
		character.save();
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

	public static void deleteSyllable(long id, String syllable) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfSyllableNotFound(character, syllable);
		
		character.deleteSyllable(syllable);
		character.save();
	}

	public static void updateSyllableWords(long id, String syllable, List<CharacterWord> words) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfSyllableNotFound(character, syllable);
		
		for(CharacterWord word : words) {
			raiseIfNotValidSyllable(word.getSyllable());
		}
		
		character.setSyllableWords(syllable, words);
		character.save();
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

	private static void raiseIfSyllableNotFound(Character character, String syllable) {
		if(Linq.from(character.getSyllables()).notExist(s->s.getValue().equals(syllable))) {
			raise("不存在读音: %s", syllable);
		}
	}
	
}
