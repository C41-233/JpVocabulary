package controllers.katakanas;

import java.util.ArrayList;
import java.util.List;

import base.utility.Chars;
import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.indexes.IndexManager;
import logic.words.KatakanaWordsLogic;
import models.KatakanaWord;

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
			words = KatakanaWordsLogic.getKatakanaWordsByKatakana(index);
		}
		else {
			words = new ArrayList<>();
		}
		
		render("katakanas/katakana-index");
	}
	
	private static class WordVO{
		public String value;
	}
	
}
