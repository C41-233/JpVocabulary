package controllers.characters;

import java.util.ArrayList;

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
		@Required @StringValue() String[] pinyins
	) {
		ArrayList<String> pinyinList = new ArrayList<>();
		for(String token: pinyins) {
			token = token.trim();
			if(token.isEmpty()==false) {
				pinyinList.add(token);
			}
		}
		ICharacter character = CharactersLogic.createCharacter(jp, cn, pinyinList);
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
	
}
