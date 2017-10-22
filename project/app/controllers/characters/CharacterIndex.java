package controllers.characters;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.characters.CharactersLogic;
import logic.indexes.IndexManager;
import logic.pinyins.Pinyins;
import po.CharacterWord;
import po.ICharacter;
import po.ICharacterSyllable;

public final class CharacterIndex extends HtmlControllerBase{

	public static void index(){
		page(IndexManager.Character.getFirst());
	}
	
	public static void page(
		@Required String index
	){
		if(IndexManager.Character.isValidIndex(index) == false) {
			notFound(Strings.format("%s not found.", index));
		}
		
		ArgVO arg = new ArgVO();
		arg.index = index;
		renderArgs.put("arg", arg);
			
		List<CharacterIndexVO> indexesVO = Linq.from(IndexManager.Character.getGroups()).select((group, i)->{
			CharacterIndexVO vo = new CharacterIndexVO();
			vo.name = group.getName();
			vo.seq = i;
			for(String item : group.getItems()) {
				vo.items.add(item);
				if(item.equals(index)) {
					arg.group = vo.name;
				}
			}
			return vo;
		}).toList();
		
		renderArgs.put("indexes", indexesVO);
		
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
					
					for(CharacterWord word : syllable.getWords()) {
						WordPairVO wordVO = new WordPairVO();
						wordVO.word = word.getWord();
						wordVO.syllable = word.getSyllable();
						syllableVO.words.add(wordVO);
					}
				}
				
				//固有词组
				for(CharacterWord word : character.getFixwords()) {
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
