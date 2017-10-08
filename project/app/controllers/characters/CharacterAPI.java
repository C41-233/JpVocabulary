package controllers.characters;

import java.util.ArrayList;

import core.controller.AjaxControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import logic.characters.CharactersLogic;
import po.ICharacter;

public final class CharacterAPI extends AjaxControllerBase {

	public static void create(
		String jp, 
		String cn, 
		String[] pinyins
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
	
	public static void delete(Long id) {
		CharactersLogic.deleteCharacater(id);
	}
	
}
