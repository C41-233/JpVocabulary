package controllers.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import base.utility.linq.Linq;
import core.controller.HtmlControllerBase;
import core.controller.validation.annotation.Id;
import logic.LogicValidate;
import logic.pinyins.Pinyins;
import logic.words.VerbWordsLogic;
import po.IVerbWord;
import po.IVerbWordValue;
import po.VerbFixword;
import po.VerbWordType;

public final class VerbWordEdit extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		IVerbWord word = VerbWordsLogic.getVerbWordAndUpdate(id);
		if(word == null) {
			notFound("动词不存在：id="+id);
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
		for(IVerbWordValue value : word.getValues()) {
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
		Set<VerbWordType> types = Linq.from(word.getTypes()).toSet();
		for(VerbWordType type : VerbWordType.values()){
			TypeVO typeVO = new TypeVO();
			typeVO.name = type.toFull();
			typeVO.value = type.toString();
			if(types.contains(type)) {
				typeVO.has = true;
			}
			typesVO.add(typeVO);
		}
		renderArgs.put("types", typesVO);
		
		List<FixwordVO> fixwordsVO = new ArrayList<>();
		for(VerbFixword fixword : word.getFixwords()) {
			FixwordVO fixwordVO = new FixwordVO();
			fixwordVO.value = fixword.getValue();
			fixwordVO.meaning = fixword.getMeaning();
			fixwordsVO.add(fixwordVO);
		}
		renderArgs.put("fixwords", fixwordsVO);
		
		renderArgs.put("refer", refer);
		jsArgs.put("refer", refer);
		
		render("words/verb-edit");
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
		public String value;
		public boolean has;
	}

	private static class FixwordVO{
		String value;
		String meaning;
	}
	
}
