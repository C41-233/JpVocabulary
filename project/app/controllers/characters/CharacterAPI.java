package controllers.characters;

import java.util.Arrays;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.characters.CharactersLogic;
import po.CharacterWord;
import po.ICharacter;

public final class CharacterAPI extends AjaxControllerBase {

	public static void create(
		@Required @StringValue(length=1) String jp, 
		@Required @StringValue(length=1) String cn, 
		@Required @StringValue(minLength=1) String[] pinyins
	) {
		ICharacter character = CharactersLogic.createCharacter(jp, cn, Arrays.asList(pinyins));
		jsonResult.put("href", Route.get(CharacterDetail.class, "index", new RouteArgs().put("id", character.getId())));
	}
	
	public static void delete(@Id long id) {
		CharactersLogic.deleteCharacter(id);
	}
	
	public static void addSyllable(
		@Id long id, 
		@Required @StringValue(minLength=1) String syllable
	) {
		CharactersLogic.addSyllable(id, syllable);
	}

	public static void updateJp(
		@Id long id,
		@Required @StringValue(length=1) String value
	) {
		CharactersLogic.updateJp(id, value);
	}
	
	public static void updateCn(
		@Id long id,
		@Required @StringValue(length=1) String value
	) {
		CharactersLogic.updateCn(id, value);
	}

	public static void updatePinyins(
		@Id long id,
		@Required @StringValue(minLength=1) String[] values
	) {
		CharactersLogic.updatePinyins(id, Arrays.asList(values));
	}
	
	public static void deleteSyllable(
		@Id long id,
		@Required String syllable
	) {
		CharactersLogic.deleteSyllable(id, syllable);
	}
	
	public static void updateSyllableValue(
		@Id long id,
		@Required @StringValue(minLength=1) String syllable,
		@Required @StringValue(minLength=1) String value
	) {
		CharactersLogic.updateSyllable(id, syllable, value);
	}

	public static void updateSyllableMain(
		@Id long id,
		@Required @StringValue(minLength=1) String syllable,
		@Required boolean value
	) {
		CharactersLogic.updateSyllableMain(id, syllable, value);
	}
	
	public static void updateSyllableWords(
		@Id long id,
		@Required String syllable,
		@Required @StringValue(minLength=1) String[] words
	) {
		List<CharacterWord> wordList = Linq.from(words).select(s->{
			String[] split = Strings.splitTokens(s);
			if(split.length != 2) {
				badRequest("参数格式错误: "+s);
			}
			return new CharacterWord(split[0], split[1]);
		}).toList();
		CharactersLogic.updateSyllableWords(id, syllable, wordList);
	}
	
	public static void addFixword(
		@Id long id,
		@Required @StringValue(minLength=1) String word,
		@Required @StringValue(minLength=1) String syllable
	) {
		CharactersLogic.addFixword(id, word, syllable);
	}

	public static void deleteFixword(
		@Id long id,
		@Required String word,
		@Required String syllable
	) {
		CharactersLogic.deleteFixword(id, word, syllable);
	}
	
}
