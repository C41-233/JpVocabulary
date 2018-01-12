package controllers.notionals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import c41.utility.comparator.Comparators;
import c41.utility.linq.IReferenceEnumerable;
import c41.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import logic.indexes.IndexManager;
import logic.pinyins.Pinyins;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordType;
import po.NotionalWordValueType;

public abstract class NotionalWordIndexBase extends HTMLComponentsControllerBase{

	@FunctionalInterface
	protected static interface IWordProvider{
		public List<INotionalWordValue> find(String index);
	}
	
	@FunctionalInterface
	protected static interface IPageProvider{
		public String route(String index);
	}
	
	protected static void process(String index, String wordsName, IWordProvider wordProvider, IPageProvider pageProvider) {
		renderArgs.put("index", index);
		
		LeftIndexGroup indexes =LeftIndexGroup.compile(index, IndexManager.Hiragana, IndexManager.Character);
		indexes.url(s->pageProvider.route(s));
		renderArgs.put("indexes", indexes);
		
		if(IndexManager.Character.isValidIndex(index)) {
			processAsCharacter(index, wordProvider);
		}
		if(IndexManager.Hiragana.isValidIndex(index)) {
			processAsHiragana(index, wordProvider);
		}
		
		renderArgs.put("group", wordsName);
		render("notionals/notional-index");
	}

	private static void processAsHiragana(String index, IWordProvider provider) {
		List<INotionalWordValue> values = provider.find(index);
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
			
			for(NotionalWordType type : word.getTypes()) {
				wordVO.types.add(type.toString());
			}
			
			wordVO.href = Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
			
			wordsVO.sort((w1,w2)->{
				int s = Comparators.compare(w1.value, w2.value);
				if(s != 0) {
					return s;
				}
				int s1 = w1.alias.size();
				int s2 = w2.alias.size();
				if(s1 !=0 && s2 != 0) {
					return Comparators.compare(w1.alias.get(0), w2.alias.get(0));
				}
				if(s1 == 0 && s2 != 0) {
					return -1;
				}
				if(s1 !=0 && s2 == 0) {
					return 1;
				}
				return 0;
			});
			
			wordsVO.add(wordVO);
		}
		
		renderArgs.put("words", wordsVO);
		renderArgs.put("model", "hiragana");
	}
	
	private static void processAsCharacter(String index, IWordProvider provider) {
		List<PinyinGroupVO> pinyinGroupsVO = new ArrayList<>();
		for(int i=1; i<=4; i++) {
			String pinyin = index+i;
			List<INotionalWordValue> values = provider.find(pinyin);
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
						
						for(NotionalWordType type : word.getTypes()) {
							wordVO.types.add(type.toString());
						}
						
						wordVO.href = Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
						
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
