package controllers.notionals;

import c41.core.Core;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;
import logic.words.NotionalWordsQueryLogic;

public final class FunctionWordIndex extends NotionalWordIndexBase{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		process(
			index, 
			"虚词",
			s -> NotionalWordsQueryLogic.findFunctionWordValuesByIndex(s),
			s -> Route.get(Core.cast(request.controllerClass), "page", new RouteArgs().put("index", s))
		);
	}
	
}
