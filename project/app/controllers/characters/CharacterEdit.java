package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Id;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import po.ICharacter;

public class CharacterEdit extends HtmlControllerBase{

	public static void index(@Id Long id) {
		ICharacter character = CharactersLogic.getCharacer(id);
		if(character == null) {
			notFound(Strings.format("%d not found", id));
		}
		
		jsArgs.put("id", id);
		
		CharacterVO vo = new CharacterVO();
		vo.jp = character.getJpValue();
		vo.cn = character.getCnValue();
		
		for(String pinyin : character.getPinyins()) {
			vo.pinyins.add(Pinyins.toPinyin(pinyin));
		}
		
		renderArgs.put("character", vo);
		render("characters/edit");
	}

	private static class CharacterVO{
		String jp;
		String cn;
		List<String> pinyins = new ArrayList<>();
	}
	
}
