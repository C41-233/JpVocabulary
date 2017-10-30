package controllers.adjectives;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import base.utility.collection.map.DefaultValueHashMap;
import base.utility.comparator.Comparators;
import base.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.LogicValidate;
import logic.indexes.IndexManager;
import logic.words.AdjectiveWordsLogic;
import po.AdjectiveWordType;
import po.IAdjectiveWord;
import po.IAdjectiveWordValue;
import po.WordFixword;

public class AdjectiveWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Adjective.getFirst());
	}
	
	public static void page(@Required String index) {
		if(IndexManager.Adjective.isValidIndex(index)==false) {
			notFound();
		}
		
		List<String> indexes = Linq.from(IndexManager.Adjective.all()).toList();
		renderArgs.put("index", index);
		renderArgs.put("indexes", indexes);
		
		if(index.length() == 1) {
			processAsCharacter(index.charAt(0));
		}
		else {
			processAsHiragana(index);
		}
		
		render("adjs/adj-index");
	}

	private static void processAsHiragana(String index) {
		List<GroupVO> groupsVO = new ArrayList<>();
		
		for(String token : Linq.from(index).select(s->String.valueOf(s))) {
			GroupVO groupVO = new GroupVO();
			groupVO.value = token;
			
			List<IAdjectiveWordValue> values = AdjectiveWordsLogic.findAdjectiveWordValuesByIndex(token);
			for(IAdjectiveWordValue value : values) {
				IAdjectiveWord word = value.getWord();
				
				WordVO vo = new WordVO();
				vo.value = value.getValue();
				
				//本身是读音
				if(LogicValidate.isValidSyllable(value.getValue())) {
					Linq.from(word.getValues())
						.select(t->t.getValue())
						.where(t->LogicValidate.isValidSyllable(t)==false)
						.foreach(t->vo.alias.add(t));
				}
				else {
					Linq.from(word.getSyllables()).foreach(t->vo.alias.add(t));
				}
				
				vo.href = Route.get(AdjectiveWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
				
				Linq.from(word.getMeanings()).foreach(t->vo.meanings.add(t));
				
				Linq.from(word.getTypes()).select(t->t.toSimple()).foreach(t->vo.types.add(t));
				
				Linq.from(word.getFixwords()).select(t->{
					FixwordVO fixwordVO = new FixwordVO();
					fixwordVO.value = t.getValue();
					fixwordVO.meaning = t.getMeaning();
					return fixwordVO;
				}).foreach(t->vo.fixwords.add(t));
				
				groupVO.words.add(vo);
			}
			
			if(groupVO.words.size() > 0) {
				groupsVO.add(groupVO);
			}
		}
		renderArgs.put("groups", groupsVO);
	}

	private static void processAsCharacter(char ch) {
		String lower = String.valueOf(Character.toLowerCase(ch));
		
		List<IAdjectiveWordValue> values = AdjectiveWordsLogic.findAdjectiveWordValuesByAlpha(ch);
		DefaultValueHashMap<String, List<IAdjectiveWordValue>> groupToWord = new DefaultValueHashMap<>(()->new ArrayList<>());
		
		for(IAdjectiveWordValue value : values) {
			for(String index : value.getIndexes()) {
				if(LogicValidate.isValidPinyin(index) && index.startsWith(lower)) {
					groupToWord.get(index.substring(0, index.length()-1)).add(value);
				}
			}
		}
		
		List<GroupVO> groupsVO = new ArrayList<>();
		for(Entry<String, List<IAdjectiveWordValue>> pair : groupToWord.entrySet()) {
			GroupVO groupVO = new GroupVO();
			groupVO.value = pair.getKey();
			for(IAdjectiveWordValue value : pair.getValue()) {
				IAdjectiveWord word = value.getWord();
				
				WordVO wordVO = new WordVO();
				wordVO.value = value.getValue();
				
				for(String syllable : word.getSyllables()) {
					wordVO.alias.add(syllable);
				}
				
				for(String meaning : word.getMeanings()) {
					wordVO.meanings.add(meaning);
				}
				
				for(AdjectiveWordType type : word.getTypes()) {
					wordVO.types.add(type.toSimple());
				}
				
				wordVO.href = Route.get(AdjectiveWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
				
				for(WordFixword fixword : word.getFixwords()) {
					FixwordVO fixwordVO = new FixwordVO();
					fixwordVO.value = fixword.getValue();
					fixwordVO.meaning = fixword.getMeaning();
					wordVO.fixwords.add(fixwordVO);
				}
				
				groupVO.words.add(wordVO);
			}
			groupsVO.add(groupVO);
		}
		
		groupsVO.sort((g1,g2)->Comparators.compare(g1.value, g2.value));
		
		renderArgs.put("groups", groupsVO);
	}

	private static class GroupVO{
		public String value;
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
