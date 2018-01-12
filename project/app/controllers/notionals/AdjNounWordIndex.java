package controllers.notionals;

import c41.core.Core;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;
import logic.words.NotionalWordsQueryLogic;

public final class AdjNounWordIndex extends NotionalWordIndexBase{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		process(
			index, 
			"形容动词",
			s -> NotionalWordsQueryLogic.findAdjNounWordValuesByIndex(s),
			s -> Route.get(Core.cast(request.controllerClass), "page", new RouteArgs().put("index", s))
		);
	}
	
	
}
