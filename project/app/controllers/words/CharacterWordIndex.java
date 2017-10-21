package controllers.words;

import java.util.ArrayList;
import java.util.List;

import base.utility.Strings;
import base.utility.linq.IReferenceGroup;
import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Required;
import logic.characters.CharacterIndexManager;
import logic.pinyins.Pinyins;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
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
		renderArgs.put("syllables", syllableGroupsVO);
		
		for(int i=1; i<=4 ;i++) {
			String pinyinGroupName = index + i;
			List<INotionalWordValue> notionalWordValues = NotionalWordsQueryLogic.findCharacterWordValuesByPinyin(pinyinGroupName);
			//按汉字分组
			CharacterWordSyllableGroupVO syllableGroupVO = new CharacterWordSyllableGroupVO();
			syllableGroupVO.name = Pinyins.toPinyin(pinyinGroupName);
			
			for(IReferenceGroup<String, INotionalWordValue> characterGroup : Linq.from(notionalWordValues)
					.groupBy(word->word.getValue().substring(0, 1))) {
				{
					CharacterWordGroupVO groupVO = new CharacterWordGroupVO();
					groupVO.name = characterGroup.getKey();
					
					for(INotionalWordValue wordValue : characterGroup) {
						INotionalWord word = wordValue.getWord();
						
						WordVO wordVO = new WordVO();
						wordVO.value = wordValue.getValue();
						wordVO.syllables = Linq.from(word.getSyllables()).toList();
						wordVO.meanings = Linq.from(word.getMeanings()).toList();
						wordVO.types = Linq.from(word.getTypes()).toList();
						groupVO.words.add(wordVO);
					}
					
					syllableGroupVO.characters.add(groupVO);
				}
				
			}
			
			//仅存在的读音才加入分组
			if(syllableGroupVO.characters.size() > 0) {
				syllableGroupsVO.add(syllableGroupVO);
			}
		}
		
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
		List<String> syllables;
		List<String> meanings = new ArrayList<>();
		List<String> types = new ArrayList<>();
	}
	
}
