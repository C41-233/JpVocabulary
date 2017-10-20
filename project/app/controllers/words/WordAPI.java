package controllers.words;

import java.util.Arrays;
import java.util.List;

import core.controller.AjaxControllerBase;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.WordsUpdateLogic;

public final class WordAPI extends AjaxControllerBase{

	public static void create(
		@Required @StringValue(minLength=1) String[] values,
		@Array @StringValue(minLength=1) String[] meanings,
		@Array String[] types
	) {
		List<String> valuesList = Arrays.asList(values);
		List<String> meaingsList = Arrays.asList(meanings);
		List<String> typesList = Arrays.asList(types);
		WordsUpdateLogic.create(valuesList, meaingsList, typesList);
	}
	
}
