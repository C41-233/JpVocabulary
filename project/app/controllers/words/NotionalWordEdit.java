package controllers.words;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import base.utility.linq.Linq;
import controllers.MainIndex;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import logic.LogicValidate;
import logic.pinyins.Pinyins;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;
import po.NotionalWordType;

public final class NotionalWordEdit extends HtmlControllerBase{

	public static void index(@Id long id, String refer) {
		INotionalWord word = NotionalWordsQueryLogic.getNotionalWord(id);
		if(word == null) {
			notFound("基本词不存在：id="+id);
		}
		
		WordVO wordVO = new WordVO();
		
		StringBuilder meaningsSb = new StringBuilder();
		for(String meaning : word.getMeanings()) {
			wordVO.meanings.add(meaning);
			meaningsSb.append(meaning).append("\n");
		}
		wordVO.meaningsText = meaningsSb.toString();
		
		renderArgs.put("word", wordVO);

		List<ValueVO> valuesVO = new ArrayList<>();
		for(INotionalWordValue value : word.getValues()) {
			ValueVO valueVO = new ValueVO();
			valueVO.value = value.getValue();
			valueVO.type = value.getType().getName();
			for(String index : value.getIndexes()) {
				if(LogicValidate.isValidPinyin(index)) {
					index = Pinyins.toPinyin(index);
				}
				valueVO.indexes.add(index);
			}
			valuesVO.add(valueVO);
		}
		renderArgs.put("values", valuesVO);
		
		List<TypeVO> typesVO = new ArrayList<>();
		
		Set<String> types = Linq.from(word.getTypes()).toSet();
		
		for(String type : NotionalWordType.all()) {
			TypeVO typeVO = new TypeVO();
			typeVO.name = type;
			if(types.contains(type)) {
				typeVO.has = true;
			}
			typesVO.add(typeVO);
		}
		renderArgs.put("types", typesVO);
		
		if(refer == null) {
			refer = Route.get(MainIndex.class, "index");
		}
		renderArgs.put("refer", refer);
		
		render("words/notional-edit");
	}
	
	private static class WordVO{

		public List<String> meanings = new ArrayList<>();
		public String meaningsText;
		
	}
	
	private static class ValueVO{
		
		public String value;
		public String type;
		public List<String> indexes = new ArrayList<>();
		
	}
	
	private static class TypeVO{
		public String name;
		public boolean has;
	}
	
}
