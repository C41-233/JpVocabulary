package controllers.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.utility.linq.IReferenceEnumerable;
import base.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;
import logic.pinyins.Pinyins;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordValueType;

public final class HiraganaWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		renderArgs.put("index", index);
		
		LeftIndexGroup indexes =LeftIndexGroup.compile(index, IndexManager.Hiragana, IndexManager.Character);
		indexes.url(s -> Route.get(HiraganaWordIndex.class, "page", new RouteArgs().put("index", s)));
		renderArgs.put("indexes", indexes);
		
		if(IndexManager.Character.isValidIndex(index)) {
			processAsCharacter(index);
		}
		if(IndexManager.Hiragana.isValidIndex(index)) {
			processAsHiragana(index);
		}
		
		render("words/word-hiragana-index");
	}

	private static void processAsHiragana(String index) {
		List<INotionalWordValue> values = NotionalWordsQueryLogic.findHiraganaWordValuesByIndex(index);
		List<WordVO> wordsVO = new ArrayList<>();
		for(INotionalWordValue value : values) {
			INotionalWord word = value.getWord();
			
			WordVO wordVO = new WordVO();
			wordVO.value = value.getValue();

			Map<NotionalWordValueType, IReferenceEnumerable<INotionalWordValue>> refValues = Linq.from(word.getValues()).<INotionalWordValue>cast().toMap(w->w.getType());
			//如果本身是读音，那么用汉字或混合来表示
			switch (value.getType()) {
			case Syllable:
				IReferenceEnumerable<INotionalWordValue> characters = refValues.get(NotionalWordValueType.Character);
				if(characters != null) {
					for(INotionalWordValue refValue : characters) {
						wordVO.alias.add(refValue.getValue());
					}
				}
				
				if(wordVO.alias.size() == 0) {
					IReferenceEnumerable<INotionalWordValue> mixes = refValues.get(NotionalWordValueType.Mixed);
					if(mixes != null) {
						for(INotionalWordValue refValue : mixes) {
							wordVO.alias.add(refValue.getValue());
						}
					}
				}
				break;
			//如果本身是混合词，那么用注音表示
			case Mixed:
				for(INotionalWordValue refValue : refValues.get(NotionalWordValueType.Syllable)) {
					wordVO.alias.add(refValue.getValue());
				}
				break;
			default:
				break;
			}
			
			for(String meaning : word.getMeanings()) {
				wordVO.meanings.add(meaning);
			}
			
			for(String type : word.getTypes()) {
				wordVO.types.add(type);
			}
			
			wordVO.href = Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
			
			wordsVO.add(wordVO);
		}
		
		renderArgs.put("words", wordsVO);
		renderArgs.put("model", "hiragana");
	}
	
	private static void processAsCharacter(String index) {
		List<PinyinGroupVO> pinyinGroupsVO = new ArrayList<>();
		for(int i=1; i<=4; i++) {
			String pinyin = index+i;
			List<INotionalWordValue> values = NotionalWordsQueryLogic.findHiraganaWordValuesByIndex(pinyin);
			if(values.size() == 0) {
				continue;
			}
			
			PinyinGroupVO groupVO = new PinyinGroupVO();
			groupVO.value = Pinyins.toPinyin(pinyin);
			Linq.from(values)
				.groupBy(value->value.getValue().substring(0, 1))
				.orderBy(group->group.getKey())
				.foreach(group->{
					CharacterGroupVO characterGroupVO = new CharacterGroupVO();
					characterGroupVO.name = group.getKey();
					groupVO.characters.add(characterGroupVO);
					
					for(INotionalWordValue value : group) {
						INotionalWord word = value.getWord();
						
						WordVO wordVO = new WordVO();
						wordVO.value = value.getValue();
						for(String syllable : word.getSyllables()) {
							wordVO.alias.add(syllable);
						}
						
						for(String meaning : word.getMeanings()) {
							wordVO.meanings.add(meaning);
						}
						
						for(String type : word.getTypes()) {
							wordVO.types.add(type);
						}
						
						characterGroupVO.words.add(wordVO);
					}
				});
			pinyinGroupsVO.add(groupVO);
		}
		
		renderArgs.put("pinyins", pinyinGroupsVO);
		renderArgs.put("model", "pinyin");
	}

	private static class PinyinGroupVO{
		
		public String value;
		public List<CharacterGroupVO> characters = new ArrayList<>();
		
	}
	
	private static class CharacterGroupVO{
		
		public String name;
		public List<WordVO> words = new ArrayList<>();
		
	}
	
	private static class WordVO{
		
		public String value;
		public List<String> alias = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
		public List<String> types = new ArrayList<>();
		public String href;
		
	}
	
}
