package controllers.verbs;

import java.util.ArrayList;
import java.util.List;

import c41.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.LogicValidate;
import logic.indexes.IndexManager;
import logic.pinyins.Pinyins;
import logic.words.VerbWordsLogic;
import po.IVerbWord;
import po.IVerbWordValue;
import po.WordFixword;
import po.VerbWordType;

public class VerbWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		LeftIndexGroup indexes = LeftIndexGroup.compile(index, IndexManager.Hiragana, IndexManager.Character);
		indexes.url(s->Route.get(VerbWordIndex.class, "page", new RouteArgs().put("index", s)));
		renderArgs.put("indexes", indexes);
		renderArgs.put("index", index);

		if(IndexManager.Character.isValidIndex(index)) {
			processAsCharacter(index);
		}
		if(IndexManager.Hiragana.isValidIndex(index)) {
			processAsHiragana(index);
		}
		
		render("verbs/verb-index");
	}

	private static void processAsHiragana(String index) {
		List<IVerbWordValue> values = VerbWordsLogic.findVerbWordValuesByIndex(index);
		List<WordVO> wordsVO = new ArrayList<>();
		for(IVerbWordValue value : values) {
			IVerbWord word = value.getWord();
			
			WordVO vo = new WordVO();
			vo.value = value.getValue();
			
			//本身是读音
			if(LogicValidate.isValidSyllable(value.getValue())) {
				Linq.from(word.getValues())
					.select(t->t.getValue())
					.where(t->LogicValidate.isValidSyllable(t)==false)
					.orderBy(VerbWordsLogic.ValueComparator)
					.foreachEx(t->vo.alias.add(t));
			}
			else {
				Linq.from(word.getSyllables()).foreachEx(t->vo.alias.add(t));
			}
			
			vo.href = Route.get(VerbWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
			
			Linq.from(word.getMeanings()).foreachEx(t->vo.meanings.add(t));
			
			Linq.from(word.getTypes()).select(t->t.toSimple()).foreachEx(t->vo.types.add(t));
			
			Linq.from(word.getFixwords()).select(t->{
				FixwordVO fixwordVO = new FixwordVO();
				fixwordVO.value = t.getValue();
				fixwordVO.meaning = t.getMeaning();
				return fixwordVO;
			}).foreachEx(t->vo.fixwords.add(t));
			
			wordsVO.add(vo);
		}
		renderArgs.put("words", wordsVO);
		renderArgs.put("model", "hiragana");
	}

	private static void processAsCharacter(String index) {
		List<PinyinGroupVO> pinyinGroupsVO = new ArrayList<>();
		for(int i=1; i<=4; i++) {
			String pinyin = index+i;
			List<IVerbWordValue> values = VerbWordsLogic.findVerbWordValuesByIndex(pinyin);
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
					
					for(IVerbWordValue value : group) {
						IVerbWord word = value.getWord();
						
						WordVO wordVO = new WordVO();
						wordVO.value = value.getValue();
						
						for(String syllable : word.getSyllables()) {
							wordVO.alias.add(syllable);
						}
						
						for(String meaning : word.getMeanings()) {
							wordVO.meanings.add(meaning);
						}
						
						for(VerbWordType type : word.getTypes()) {
							wordVO.types.add(type.toSimple());
						}
						
						wordVO.href = Route.get(VerbWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
						
						for(WordFixword fixword : word.getFixwords()) {
							FixwordVO fixwordVO = new FixwordVO();
							fixwordVO.value = fixword.getValue();
							fixwordVO.meaning = fixword.getMeaning();
							wordVO.fixwords.add(fixwordVO);
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
		public List<FixwordVO> fixwords = new ArrayList<>();
		public String href;
	}
	
	private static class FixwordVO{
		public String value;
		public String meaning;
	}
	
}
