package controllers.words;

import base.core.Objects;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Required;
import logic.indexes.IndexManager;
import logic.words.NotionalWordsQueryLogic;

public final class FunctionWordIndex extends NotionalWordIndex{

	public static void index() {
		page(IndexManager.Hiragana.getFirst());
	}
	
	public static void page(@Required String index) {
		process(
			index, 
			s -> NotionalWordsQueryLogic.findFunctionWordValuesByIndex(s),
			s -> Route.get(Objects.cast(request.controllerClass), "page", new RouteArgs().put("index", s))
		);
	}
	
}
