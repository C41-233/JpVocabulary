package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.characters.CharactersLogic;
import logic.indexes.IndexManager;
import logic.pinyins.Pinyins;
import po.WordPair;
import po.ICharacter;
import po.ICharacterSyllable;

public final class CharacterIndex extends HTMLComponentsControllerBase{

	public static void index(){
		page(IndexManager.Character.getFirst());
	}
	
	public static void page(
		@Required String index
	){
		LeftIndexGroup indexes = LeftIndexGroup.compile(index, IndexManager.Character);
		indexes.url(s->Route.get(CharacterIndex.class, "page", new RouteArgs().put("index", s)));
		renderArgs.put("indexes", indexes);
		renderArgs.put("index", index);
		
		//汉字组
		List<CharacterGroupVO> characterGroupsVO = new ArrayList<>();
		for(int i=1; i<=4; i++) {
			String groupName = index + i;
			List<ICharacter> characters = CharactersLogic.findCharactersByPinyin(groupName);
			if(characters.size() == 0) {
				continue;
			}
			
			CharacterGroupVO groupVO = new CharacterGroupVO();
			groupVO.name = Pinyins.toPinyin(groupName);
			characterGroupsVO.add(groupVO);
			
			//汉字
			for(ICharacter character : characters) {
				CharacterVO characterVO = new CharacterVO();
				characterVO.id = character.getId();
				characterVO.jp = character.getJpValue();
				characterVO.cn = character.getCnValue();
				groupVO.characters.add(characterVO);
				
				//发音组
				for(ICharacterSyllable syllable : character.getSyllables()) {
					CharacterSyllableVO syllableVO = new CharacterSyllableVO();
					syllableVO.value = syllable.getValue();
					characterVO.syllables.add(syllableVO);
					
					if(syllable.isMain()) {
						WordPairVO wordVO = new WordPairVO();
						wordVO.word = character.getJpValue();
						wordVO.syllable = syllable.getValue();
						syllableVO.words.add(wordVO);
					}
					
					for(WordPair word : syllable.getWords()) {
						WordPairVO wordVO = new WordPairVO();
						wordVO.word = word.getWord();
						wordVO.syllable = word.getSyllable();
						syllableVO.words.add(wordVO);
					}
				}
				
				//固有词组
				for(WordPair word : character.getFixwords()) {
					WordPairVO wordVO = new WordPairVO();
					wordVO.word = word.getWord();
					wordVO.syllable = word.getSyllable();
					characterVO.fixwords.add(wordVO);
				}
			}
		}
		
		renderArgs.put("characterGroups", characterGroupsVO);
		
		render("characters/character-index");
	}
	
	private static class CharacterIndexVO{
		String name;
		List<String> items = new ArrayList<>();
		int seq;
	}
	
	private static class ArgVO{
		String group;
		String index;
	}
	
	private static class CharacterGroupVO{
		String name;
		List<CharacterVO> characters = new ArrayList<>();
	}
	
	private static class CharacterVO{
		long id;
		String jp;
		String cn;
		List<CharacterSyllableVO> syllables = new ArrayList<>();
		List<WordPairVO> fixwords = new ArrayList<>();
	}

	private static class CharacterSyllableVO{
		String value;
		List<WordPairVO> words = new ArrayList<>();
	}
	
	private static class WordPairVO{
		String word;
		String syllable;
	}
	
}
