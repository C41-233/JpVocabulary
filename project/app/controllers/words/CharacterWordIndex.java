package controllers.words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.characters.CharacterIndexManager;
import logic.pinyins.Pinyins;
import logic.words.NotionalWordsQueryLogic;
import logic.words.NotionalWordsUpdateLogic;
import po.INotionalWordValue;

public class CharacterWordIndex extends HtmlControllerBase{

	public static void index() {
		page("ai");
	}
	
	public static void page(@Required String index) {
		if(CharacterIndexManager.isValidIndex(index) == false) {
			notFound(Strings.format("%s not found.", index));
		}
		
		ArgVO arg = new ArgVO();
		arg.index = index;
		renderArgs.put("arg", arg);
			
		List<CharacterIndexVO> indexesVO = Linq.from(CharacterIndexManager.getCache()).select((group, i)->{
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
		
		List<CharacterWordSyllableGroupVO> syllableGroupsVO = new ArrayList<>();
		
		
		for(int i=1; i<=4 ;i++) {
			String pinyinGroupName = index + i;
			List<INotionalWordValue> notionalWordValues = NotionalWordsQueryLogic.findCharacterWordValuesByPinyin(pinyinGroupName);
			
			CharacterWordSyllableGroupVO syllableGroupVO = new CharacterWordSyllableGroupVO();
			syllableGroupVO.name = Pinyins.toPinyin(pinyinGroupName);
			
			{
				CharacterWordGroupVO groupVO = new CharacterWordGroupVO();
				syllableGroupVO.characters.add(groupVO);
				
				groupVO.name = "啊";
				{
					WordVO wordVO = new WordVO();
					wordVO.value = "愛野";
					wordVO.syllable = "あいの";
					wordVO.meanings = (Arrays.asList("干","打（哈哈）"));
					wordVO.types = Arrays.asList("名词", "自动词");
					groupVO.words.add(wordVO);
				}
				{
					WordVO wordVO = new WordVO();
					wordVO.value = "愛野";
					wordVO.syllable = "あいの";
					wordVO.meanings = (Arrays.asList("干"));
					wordVO.types = Arrays.asList("名词", "自动词", "副词");
					groupVO.words.add(wordVO);
				}
			}
			
			{
				CharacterWordGroupVO groupVO = new CharacterWordGroupVO();
				syllableGroupVO.characters.add(groupVO);
				
				groupVO.name = "的";
				{
					WordVO wordVO = new WordVO();
					wordVO.value = "愛野";
					wordVO.syllable = "あいの";
					wordVO.meanings = (Arrays.asList("干","打（哈哈）"));
					wordVO.types = Arrays.asList("接头词");
					groupVO.words.add(wordVO);
				}
			}

			syllableGroupsVO.add(syllableGroupVO);
		}
		
		renderArgs.put("syllables", syllableGroupsVO);
		render("words/word-character-index");
	}

	private static class ArgVO{
		String group;
		String index;
	}

	private static class CharacterIndexVO{
		String name;
		List<String> items = new ArrayList<>();
		int seq;
	}
	
	
	private static class CharacterWordSyllableGroupVO{
		String name;
		List<CharacterWordGroupVO> characters = new ArrayList<>();
	}
	
	private static class CharacterWordGroupVO{
		String name;
		List<WordVO> words = new ArrayList<>();
	}
	
	private static class WordVO{
		String value;
		String syllable;
		List<String> meanings = new ArrayList<>();
		List<String> types = new ArrayList<>();
	}
	
}
