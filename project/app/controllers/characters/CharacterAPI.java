package controllers.characters;

import java.util.Arrays;

import core.controller.AjaxControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.characters.CharactersLogic;
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
		CharactersLogic.deleteCharacater(id);
	}
	
	public static void addSyllable(
		@Id long id, 
		@Required @StringValue(minLength=1) String syllable
	) {
		CharactersLogic.AddSyllable(id, syllable);
	}

	public static void updateJp(
		@Id long id,
		@Required @StringValue(length=1) String value
	) {
		CharactersLogic.UpdateJp(id, value);
	}
	
	public static void UpdateCn(
		@Id long id,
		@Required @StringValue(length=1) String value
	) {
		CharactersLogic.UpdateCn(id, value);
	}

	public static void UpdatePinyins(
		@Id long id,
		@Required @StringValue(minLength=1) String[] values
	) {
		CharactersLogic.UpdatePinyins(id, Arrays.asList(values));
	}
	
	
	
}
