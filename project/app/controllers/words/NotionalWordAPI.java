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
	
	public static void delete(@Id long id) {
		NotionalWordsUpdateLogic.delete(id);
	}
	
	public static void addValue(@Id long id, @Required @StringValue(minLength=1) String value) {
		NotionalWordsUpdateLogic.addValue(id, value);
	}
	
	public static void deleteValue(@Id long id) {
		NotionalWordsUpdateLogic.deleteValue(id);
	}

	public static void updateMeanings(@Id long id, @Array @StringValue(minLength=1) String[] meanings) {
		NotionalWordsUpdateLogic.updateMeanings(id, Arrays.asList(meanings));
	}
	
	public static void updateType(@Id long id, @Required String type, @Required boolean value) {
		NotionalWordsUpdateLogic.updateType(id, type, value);
	}
	
}
