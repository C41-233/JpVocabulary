package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Id;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import po.ICharacter;
import po.ICharacterSyllable;

public class CharacterEdit extends HtmlControllerBase{

	public static void index(@Id long id) {
		ICharacter character = CharactersLogic.getCharacer(id);
		if(character == null) {
			notFound(Strings.format("%d not found", id));
		}
		
		jsArgs.put("id", id);
		
		CharacterVO vo = new CharacterVO();
		vo.id = id;
		vo.jp = character.getJpValue();
		vo.cn = character.getCnValue();
		
		StringBuilder pinyinSb = new StringBuilder();
		for(String pinyin : character.getPinyins()) {
			vo.pinyins.add(Pinyins.toPinyin(pinyin));
			pinyinSb.append(pinyin).append("\n");
		}
		vo.pinyinsText = pinyinSb.toString();
		
		for(ICharacterSyllable syllable : character.getSyllables()) {
			SyllableVO syllableVO = new SyllableVO();
			vo.syllables.add(syllableVO);
			
			syllableVO.value = syllable.getValue();
		}
		
		renderArgs.put("character", vo);
		render("characters/edit");
	}

	private static class CharacterVO{
		long id;
		String jp;
		String cn;
		List<String> pinyins = new ArrayList<>();
		String pinyinsText;
		
		List<SyllableVO> syllables = new ArrayList<>();
	}
	
	private static class SyllableVO{
		String value;
	}
	
	private static class WordPairVO{
		
	}
	
}
