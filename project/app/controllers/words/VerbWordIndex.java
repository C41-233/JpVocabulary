package controllers.words;

import controllers.components.HTMLComponentsControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;

public class VerbWordIndex extends HTMLComponentsControllerBase{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		LeftIndexGroup indexes = LeftIndexGroup.compile(index, IndexManager.Hiragana, IndexManager.Character);
		indexes.url(s->Route.get(VerbWordIndex.class, "page", new RouteArgs().put("index", s)));
		renderArgs.put("indexes", indexes);
		renderArgs.put("index", index);
		
		render("words/word-verb-index");
	}
	
}
