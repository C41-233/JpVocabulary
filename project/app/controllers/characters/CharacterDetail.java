package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import po.ICharacter;

public class CharacterDetail extends HtmlControllerBase{

	public static void index(
		Long id
	) {
		ICharacter character = CharactersLogic.getCharacer(id);
		if(character == null) {
			notFound(Strings.format("%d not found", id));
		}
		
		CharacterVO vo = new CharacterVO();
		vo.id = character.getId();
		vo.jp = character.getJpValue();
		vo.cn = character.getCnValue();
		
		for(String pinyin : character.getPinyins()) {
			vo.pinyins.add(Pinyins.toPinyin(pinyin));
		}
		
		renderArgs.put("character", vo);
		render("characters/detail");
	}
	
	private static class CharacterVO{
		long id;
		String jp;
		String cn;
		List<String> pinyins = new ArrayList<>();
	}
	
}
