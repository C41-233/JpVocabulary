package controllers.adjectives;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import c41.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Id;
import logic.LogicValidate;
import logic.pinyins.Pinyins;
import logic.words.AdjectiveWordsLogic;
import po.AdjectiveWordType;
import po.IAdjectiveWord;
import po.IAdjectiveWordValue;
import po.WordFixword;

public final class AdjectiveWordEdit extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		IAdjectiveWord word = AdjectiveWordsLogic.getAdjectiveWordAndUpdate(id);
		if(word == null) {
			notFound("形容词不存在：id="+id);
		}
		
		jsArgs.put("id", id);
		renderArgs.put("id", id);
		
		WordVO wordVO = new WordVO();

		StringBuilder meaningsSb = new StringBuilder();
		for(String meaning : word.getMeanings()) {
			wordVO.meanings.add(meaning);
			meaningsSb.append(meaning).append("\n");
		}
		wordVO.meaningsText = meaningsSb.toString();
		
		renderArgs.put("word", wordVO);
		
		boolean syllableDelete = Linq.from(word.getSyllables()).count() > 1;
		
		List<ValueVO> valuesVO = new ArrayList<>();
		for(IAdjectiveWordValue value : word.getValues()) {
			ValueVO valueVO = new ValueVO();
			valueVO.id = value.getId();
			valueVO.value = value.getValue();
			for(String index : value.getIndexes()) {
				if(LogicValidate.isValidPinyin(index)) {
					index = Pinyins.toPinyin(index);
				}
				valueVO.indexes.add(index);
			}

			if(syllableDelete == false && LogicValidate.isValidSyllable(value.getValue())) {
				valueVO.canDelete = false;
			}
			valuesVO.add(valueVO);
		}
		renderArgs.put("values", valuesVO);

		List<TypeVO> typesVO = new ArrayList<>();
		Set<AdjectiveWordType> types = Linq.from(word.getTypes()).toSet();
		for(AdjectiveWordType type : AdjectiveWordType.values()){
			TypeVO typeVO = new TypeVO();
			typeVO.name = type.toString();
			if(types.contains(type)) {
				typeVO.has = true;
			}
			typesVO.add(typeVO);
		}
		renderArgs.put("types", typesVO);
		
		List<FixwordVO> fixwordsVO = new ArrayList<>();
		for(WordFixword fixword : word.getFixwords()) {
			FixwordVO fixwordVO = new FixwordVO();
			fixwordVO.value = fixword.getValue();
			fixwordVO.meaning = fixword.getMeaning();
			fixwordsVO.add(fixwordVO);
		}
		renderArgs.put("fixwords", fixwordsVO);
		
		renderArgs.put("refer", refer);
		jsArgs.put("refer", refer);
		
		render("adjs/adj-edit");
	}
	
	private static class WordVO{
		public List<String> meanings = new ArrayList<>();
		public String meaningsText;
	}

	private static class ValueVO{
		public long id;
		public String value;
		public String type;	
		public List<String> indexes = new ArrayList<>();
		public boolean canDelete = true;
	}
	
	private static class TypeVO{
		public String name;
		public boolean has;
	}

	private static class FixwordVO{
		public String value;
		public String meaning;
	}
	
	
}
