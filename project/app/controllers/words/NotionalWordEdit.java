package controllers.words;

import java.util.ArrayList;
import java.util.List;

import controllers.MainIndex;
import core.controller.HtmlControllerBase;
import core.controller.Route;
import core.controller.validation.annotation.Id;
import logic.words.NotionalWordsQueryLogic;
import po.INotionalWord;
import po.INotionalWordValue;

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
		StringBuilder valuesSb = new StringBuilder();
		for(INotionalWordValue value : word.getValues()) {
			ValueVO valueVO = new ValueVO();
			valueVO.value = value.getValue();
			valuesVO.add(valueVO);
			
			valuesSb.append(value.getValue()).append("\n");
		}
		renderArgs.put("values", valuesVO);

		wordVO.valuesText = valuesSb.toString();
		
		if(refer == null) {
			refer = Route.get(MainIndex.class, "index");
		}
		renderArgs.put("refer", refer);
		
		render("words/word-edit");
	}
	
	private static class WordVO{

		public List<String> values = new ArrayList<>();
		public String valuesText;
		
		public List<String> meanings = new ArrayList<>();
		public String meaningsText;
		
	}
	
	private static class ValueVO{
		
		public String value;
		
	}
	
}
