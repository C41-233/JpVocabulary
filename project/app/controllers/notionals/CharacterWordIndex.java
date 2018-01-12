package controllers.notionals;

import java.util.ArrayList;
import java.util.List;

import c41.utility.linq.IReferenceGroup;
import c41.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;
import logic.pinyins.Pinyins;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;

public final class CharacterWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Character.getFirst());
	}
	
	public static void page(@Required String index) {
		LeftIndexGroup indexes = LeftIndexGroup.compile(index, IndexManager.Character);
		indexes.url(s->Route.get(CharacterWordIndex.class, "page", new RouteArgs().put("index", s)));
		renderArgs.put("indexes", indexes);
		renderArgs.put("index", index);
		
		List<CharacterWordPinyinGroupVO> pinyinGroupsVO = new ArrayList<>();
		renderArgs.put("pinyins", pinyinGroupsVO);
		
		for(int i=1; i<=4 ;i++) {
			String pinyinGroupName = index + i;
			List<INotionalWordValue> notionalWordValues = NotionalWordsQueryLogic.findCharacterWordValuesByPinyin(pinyinGroupName);
			//按汉字分组
			CharacterWordPinyinGroupVO syllableGroupVO = new CharacterWordPinyinGroupVO();
			syllableGroupVO.name = Pinyins.toPinyin(pinyinGroupName);
			
			for(IReferenceGroup<String, INotionalWordValue> characterGroup : Linq.from(notionalWordValues)
					.groupBy(word->word.getValue().substring(0, 1))
					.orderBy(group->group.getKey())
			) {
				{
					CharacterWordGroupVO groupVO = new CharacterWordGroupVO();
					groupVO.name = characterGroup.getKey();
					
					for(INotionalWordValue wordValue : characterGroup) {
						INotionalWord word = wordValue.getWord();
						
						WordVO wordVO = new WordVO();
						wordVO.id = word.getId();
						wordVO.value = wordValue.getValue();
						wordVO.syllables = Linq.from(word.getSyllables()).toList();
						wordVO.meanings = Linq.from(word.getMeanings()).toList();
						wordVO.types = Linq.from(word.getTypes()).select(t->t.toString()).toList();
						groupVO.words.add(wordVO);
					}
					
					syllableGroupVO.characters.add(groupVO);
				}
				
			}
			
			//仅存在的读音才加入分组
			if(syllableGroupVO.characters.size() > 0) {
				pinyinGroupsVO.add(syllableGroupVO);
			}
		}
		
		render("notionals/character-index");
	}

	private static class CharacterWordPinyinGroupVO{
		String name;
		List<CharacterWordGroupVO> characters = new ArrayList<>();
	}
	
	private static class CharacterWordGroupVO{
		String name;
		List<WordVO> words = new ArrayList<>();
	}
	
	private static class WordVO{
		long id;
		String value;
		List<String> syllables;
		List<String> meanings = new ArrayList<>();
		List<String> types = new ArrayList<>();
	}
	
}
