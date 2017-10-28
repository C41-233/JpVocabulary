package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.StringValue;
import logic.characters.CharactersLogic;
import logic.pinyins.Pinyins;
import po.WordPair;
import po.ICharacter;
import po.ICharacterSyllable;

public class CharacterEdit extends HtmlControllerBase{

	public static void index(
		@Id long id,
		@StringValue String refer
	) {
		renderArgs.put("refer", refer);
		
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
			syllableVO.isMain = syllable.isMain();
			
			StringBuilder sb = new StringBuilder();
			for(WordPair word : syllable.getWords()) {
				WordPairVO wordVO = new WordPairVO();
				wordVO.syllable = word.getSyllable();
				wordVO.word = word.getWord();
				syllableVO.words.add(wordVO);
				
				sb.append(word.getWord()).append(" ").append(word.getSyllable()).append("\n");
			}
			syllableVO.wordsValue = sb.toString();
		}
		
		for(WordPair word : character.getFixwords()) {
			WordPairVO wordVO = new WordPairVO();
			wordVO.word = word.getWord();
			wordVO.syllable = word.getSyllable();
			vo.fixwords.add(wordVO);
		}
		
		boolean canDelete = true;
		
		renderArgs.put("canDelete", canDelete);
		renderArgs.put("character", vo);
		render("characters/character-edit");
	}

	private static class CharacterVO{
		long id;
		String jp;
		String cn;
		List<String> pinyins = new ArrayList<>();
		String pinyinsText;
		
		List<SyllableVO> syllables = new ArrayList<>();
		List<WordPairVO> fixwords = new ArrayList<>();
	}
	
	private static class SyllableVO{
		String value;
		List<WordPairVO> words = new ArrayList<>();
		String wordsValue;
		boolean isMain;
	}
	
	private static class WordPairVO{
		String word;
		String syllable;
	}
	
}
