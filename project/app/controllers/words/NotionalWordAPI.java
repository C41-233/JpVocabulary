package controllers.words;

import java.util.Arrays;
import java.util.List;

import core.controller.AjaxControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.NotionalWordsUpdateLogic;
import po.INotionalWord;

public final class NotionalWordAPI extends AjaxControllerBase{

	public static void create(
		@Required @Array(duplicate=false) @StringValue(minLength=1) String[] values,
		@Array @StringValue(minLength=1) String[] meanings,
		@Array(duplicate=false) String[] types
	) {
		List<String> valuesList = Arrays.asList(values);
		List<String> meaingsList = Arrays.asList(meanings);
		List<String> typesList = Arrays.asList(types);
		INotionalWord word = NotionalWordsUpdateLogic.create(valuesList, meaingsList, typesList);
		jsonResult.put("href", Route.get(NotionalWordEdit.class, "index", new RouteArgs().put("id", word.getId())));
	}
	
	public static void deleteValue(@Id long id) {
		NotionalWordsUpdateLogic.deleteValue(id);
	}
	
}
