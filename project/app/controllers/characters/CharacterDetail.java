package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.StringValue;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import po.WordPair;
import po.ICharacter;
import po.ICharacterSyllable;

public class CharacterDetail extends HtmlControllerBase{

	public static void index(
		@Id long id,
		@StringValue String refer
	) {
		if(refer == null) {
			refer = Route.get(CharacterIndex.class, "index");
		}
		renderArgs.put("refer", refer);
		
		ICharacter character = CharactersLogic.getCharacer(id);
		if(character == null) {
			notFound(Strings.format("汉字不存在: id=%d", id));
		}
		
		CharacterVO vo = new CharacterVO();
		vo.id = character.getId();
		vo.jp = character.getJpValue();
		Linq.from(character.getCnValue()).foreach(c->vo.cns.add(c));
		
		for(String pinyin : character.getPinyins()) {
			vo.pinyins.add(Pinyins.toPinyin(pinyin));
		}

		for(ICharacterSyllable syllable : character.getSyllables()) {
			SyllableVO syllableVO = new SyllableVO();
			vo.syllables.add(syllableVO);
			
			syllableVO.value = syllable.getValue();
			
			if(syllable.isMain()) {
				WordVO wordVO = new WordVO();
				wordVO.word = character.getJpValue();
				wordVO.syllable = syllable.getValue();
				syllableVO.words.add(wordVO);
			}
			
			for(WordPair word : syllable.getWords()) {
				WordVO wordVO = new WordVO();
				syllableVO.words.add(wordVO);
				
				wordVO.word = word.getWord();
				wordVO.syllable = word.getSyllable();
			}
		}
		
		for(WordPair word : character.getFixwords()) {
			WordVO wordVO = new WordVO();
			wordVO.syllable = word.getSyllable();
			wordVO.word = word.getWord();
			vo.fixwords.add(wordVO);
		}
		
		renderArgs.put("character", vo);
		render("characters/character-detail");
	}
	
	private static class CharacterVO{
		long id;
		String jp;
		List<Character> cns = new ArrayList<>();
		List<String> pinyins = new ArrayList<>();
		List<SyllableVO> syllables = new ArrayList<>();
		List<WordVO> fixwords = new ArrayList<>();
	}
	
	private static class SyllableVO{
		String value;
		List<WordVO> words = new ArrayList<>();
	}
	
	private static class WordVO{
		String word;
		String syllable;
	}
	
}
