package controllers.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import base.utility.Strings;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.characters.CharacterIndexManager;
import logic.characters.CharactersLogic;
import logic.characters.ICharacterIndexGroup;
import logic.pinyins.Pinyins;
import po.CharacterWord;
import po.ICharacter;
import po.ICharacterSyllable;

public final class CharacterIndex extends HtmlControllerBase{

	public static void index(){
		page("ai");
	}
	
	public static void page(
		@Required String index
	){
		String argGroup = String.valueOf(index.charAt(0)).toUpperCase();

		{
			//查询参数填充
			ArgVO arg = new ArgVO();
			arg.group = argGroup;
			arg.index = index;
			renderArgs.put("arg", arg);
		}

		{
			//索引
			boolean find = false;
			List<CharacterIndexVO> indexesVO = new ArrayList<>();
			
			{
				int seq = 0;
				for(ICharacterIndexGroup group : CharacterIndexManager.getCache()) {
					CharacterIndexVO vo = new CharacterIndexVO();
					vo.name = group.getName();
					vo.seq = seq;
					for(String item : group.getItems()) {
						vo.items.add(item);
						if(Objects.equals(argGroup, vo.name) && Objects.equals(item, index)) {
							find = true;
						}
					}
					indexesVO.add(vo);
					seq++;
				}
			}
			renderArgs.put("indexes", indexesVO);
			if(find == false) {
				notFound(Strings.format("%s not found.", index));
			}
		}
		
		{
			//汉字组
			List<CharacterGroupVO> characterGroupsVO = new ArrayList<>();
			for(int i=1; i<=4; i++) {
				String groupName = index + i;
				List<ICharacter> characters = CharactersLogic.findCharactersByIndex(groupName);
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
		}
		
		render("characters/index");
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
