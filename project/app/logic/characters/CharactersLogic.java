package logic.characters;

import java.util.List;

import base.utility.linq.Linq;
import core.model.ConcatSplit;
import core.model.hql.HQL;
import core.model.hql.HQLResult;
import core.model.hql.Like;
import logic.LogicBase;
import models.Character;
import models.NotionalWordValue;
import models.VerbWordValue;
import po.CharacterWord;
import po.ICharacter;
import po.ICharacterSyllable;

public final class CharactersLogic extends LogicBase{

	private CharactersLogic() {}
	
	public static List<ICharacter> findCharactersByPinyin(String pinyin){
		HQL hql = HQL.begin();
		hql.where(Like.contains("pinyins", ConcatSplit.getToken(pinyin)));
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

	public static void deleteCharacter(long id) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		character.delete();
	}

	public static void addSyllable(long id, String syllable) {
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

	public static void updateJp(long id, String jp) {
		raiseIfNotValidJpValue(jp);
		raiseIfCharacterAlreadyExist(jp);

		Character character = getCharacterOrRaiseIfNotFound(id);
		character.setJpValue(jp);
		character.save();
	}

	public static void updateCn(long id, String cn) {
		raiseIfNotValidCnValue(cn);

		Character character = getCharacterOrRaiseIfNotFound(id);
		character.setCnValue(cn);
		character.save();
	}

	public static void updatePinyins(long id, List<String> pinyins) {
		raiseIfNotValidPinyins(pinyins);
		
		Character character = getCharacterOrRaiseIfNotFound(id);
		character.setPinyins(pinyins);
		character.save();
		
		//更新基本词的汉字索引
		List<NotionalWordValue> notionalWordValues = NotionalWordValue.find("value like ?1", character.getJpValue()+"%").fetch();
		for(NotionalWordValue notionalWordValue : notionalWordValues) {
			notionalWordValue.setIndexes(pinyins);
			notionalWordValue.save();
		}
		
		//更新动词的汉字索引
		List<VerbWordValue> verbWordValues = VerbWordValue.find("value like ?1", character.getJpValue()+"%").fetch();
		for(VerbWordValue verbWordValue : verbWordValues) {
			verbWordValue.setIndexes(pinyins);
			verbWordValue.save();
		}
	}

	public static void updateSyllable(long id, String from, String to) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfSyllableNotFound(character, from);
		raiseIfSyllableFound(character, to);
		raiseIfNotValidSyllable(to);
		
		character.updateSyllable(from, to);
		character.save();
	}
	
	public static void deleteSyllable(long id, String syllable) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfSyllableNotFound(character, syllable);
		
		character.deleteSyllable(syllable);
		character.save();
	}

	public static void updateSyllableMain(long id, String syllable, boolean value) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfSyllableNotFound(character, syllable);
		
		character.updateSyllableMain(syllable, value);
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

	public static void addFixword(long id, String word, String syllable) {
		raiseIfNotValidSyllable(syllable);
		
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfFixwordFound(character, word, syllable);
		character.addFixword(new CharacterWord(word, syllable));
		character.save();
	}

	public static void deleteFixword(long id, String word, String syllable) {
		Character character = getCharacterOrRaiseIfNotFound(id);
		raiseIfFixwordNotFound(character, word, syllable);

		character.deleteFixword(word, syllable);
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

	private static void raiseIfSyllableFound(Character character, String syllable) {
		if(Linq.from(character.getSyllables()).isExist(s->s.getValue().equals(syllable))) {
			raise("已存在读音: %s", syllable);
		}
	}

	private static void raiseIfSyllableNotFound(Character character, String syllable) {
		if(Linq.from(character.getSyllables()).notExist(s->s.getValue().equals(syllable))) {
			raise("不存在读音: %s", syllable);
		}
	}

	private static void raiseIfFixwordFound(Character character, String word, String syllable) {
		if(Linq.from(character.getFixwords()).isExist(s->s.getWord().equals(word) && s.getSyllable().equals(syllable))) {
			raise("单词已存在: %s %s", word, syllable);
		}
	}

	private static void raiseIfFixwordNotFound(Character character, String word, String syllable) {
		if(Linq.from(character.getFixwords()).notExist(s->s.getWord().equals(word) && s.getSyllable().equals(syllable))) {
			raise("单词不存在: %s %s", word, syllable);
		}
	}

}
