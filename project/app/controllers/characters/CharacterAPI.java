package controllers.characters;

import java.util.ArrayList;
import java.util.Arrays;

import core.controller.AjaxControllerBase;
import core.controller.validation.Length;
import core.controller.validation.Required;
import logic.characters.CharactersLogic;

public final class CharacterAPI extends AjaxControllerBase {

	public static void create(
		@Required @Length(1) String jp, 
		@Required @Length(1) String cn, 
		@Required String[] pinyins
	) {
		ArrayList<String> pinyinList = new ArrayList<>();
		for(String token: pinyins) {
			token = token.trim();
			if(token.isEmpty()==false) {
				pinyinList.add(token);
			}
		}
		CharactersLogic.createCharacter(jp, cn, pinyinList);
	}
	
}
