package controllers.katakanas;

import java.util.ArrayList;
import java.util.List;

import c41.utility.Chars;
import c41.utility.linq.Linq;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.indexes.IndexManager;
import logic.words.KatakanaWordsLogic;
import models.KatakanaWord;
import po.KatakanaWordContext;

public final class KatakanaWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Katakana.getFirst());
	}
	
	public static void page(@Required @StringValue(length=1) String index) {
		if(IndexManager.Katakana.isValidIndex(index) == false) {
			notFound();
		}

		renderArgs.put("index", index);
		
		LeftIndexGroup indexes =LeftIndexGroup.compile(index, IndexManager.Katakana);
		indexes.url(s->Route.get(KatakanaWordIndex.class, "page", new RouteArgs().put("index", s)));
		renderArgs.put("indexes", indexes);
		
		List<KatakanaWord> words;
		
		if(Chars.isKatakana(index.codePointAt(0))) {
			words = KatakanaWordsLogic.findKatakanaWordsByKatakana(index);
		}
		else {
			words = KatakanaWordsLogic.findKatakanaWordsByAlpha(index);
		}
		
		List<WordVO> wordsVO = new ArrayList<>(); 
		for(KatakanaWord word : words) {
			WordVO wordVO = new WordVO();
			wordVO.id = word.getId();
			wordVO.href = Route.get(KatakanaWordEdit.class, "index", new RouteArgs().put("id", word.getId()).put("refer", request.url));
			wordVO.value = word.getValue();
			Linq.from(word.getMeanings()).forEach(s->wordVO.meanings.add(s));
			wordVO.alias = word.getAlias();
			Linq.from(word.getTypes()).forEach(s->wordVO.types.add(s.toString()));
			if(word.getContext() != KatakanaWordContext.无) {
				wordVO.context = word.getContext().toString();
			}
			wordsVO.add(wordVO);
		}
		
		renderArgs.put("words", wordsVO);
		
		render("katakanas/katakana-index");
	}
	
	private static class WordVO{
		public long id;
		public String href;
		public String value;
		public List<String> meanings = new ArrayList<>();
		public String alias;
		public List<String> types = new ArrayList<>();
		public String context;
	}
	
}
