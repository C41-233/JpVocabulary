package controllers.words;

import java.util.ArrayList;
import java.util.List;

import base.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.LogicValidate;
import logic.indexes.IndexManager;
import logic.words.VerbWordsLogic;
import po.IVerbWord;
import po.IVerbWordValue;

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
		
		render("words/word-verb-index");
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
					.foreach(t->vo.alias.add(t));
			}
			
			vo.href = Route.get(VerbWordDetail.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
			
			wordsVO.add(vo);
		}
		renderArgs.put("words", wordsVO);
		renderArgs.put("model", "hiragana");
	}

	private static void processAsCharacter(String index) {
		
		
	}
	
	private static class WordVO{
		
		public String value;
		public List<String> alias = new ArrayList<>();
		public List<String> meanings = new ArrayList<>();
		public List<String> types = new ArrayList<>();
		public String href;
		
	}
	
}
