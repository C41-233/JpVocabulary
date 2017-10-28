package controllers.words;

import java.util.Arrays;
import java.util.List;

import base.core.Objects;
import base.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.VerbWordsLogic;
import po.IVerbWord;
import po.VerbWordType;

public class VerbWordAPI extends AjaxControllerBase{

	public static void create(
			@Required @Array(duplicate=false) @StringValue(minLength=1) String[] values,
			@Array @StringValue(minLength=1) String[] meanings,
			@Array(duplicate=false, minLength=1) String[] types
	) {
		//词性必须全部合法
		if(Linq.from(types).isExist(s->Objects.asEnum(VerbWordType.class, s)==null)) {
			badRequest("不合法的词性：%s", Linq.from(types).findFirst(s->Objects.asEnum(VerbWordType.class, s)==null));
		}
		List<String> valuesList = Arrays.asList(values);
		List<String> meaingsList = Arrays.asList(meanings);
		List<VerbWordType> typesList = Linq.from(types).select(t->VerbWordType.valueOf(t)).toList();
		
		IVerbWord word = VerbWordsLogic.create(valuesList, meaingsList, typesList);
		jsonResult.put("href", Route.get(VerbWordEdit.class, "index", new RouteArgs().put("id", word.getId())));
	}

	public static void delete(@Id long id) {
		VerbWordsLogic.delete(id);
	}
	
	public static void updateMeanings(@Id long id, @Array @StringValue(minLength=1) String[] meanings) {
		VerbWordsLogic.updateMeanings(id, Arrays.asList(meanings));
	}

	public static void addValue(@Id long id, @Required @StringValue(minLength=1) String value) {
		VerbWordsLogic.addValue(id, value);
	}
	
	public static void deleteValue(@Id long id) {
		VerbWordsLogic.deleteValue(id);
	}

}
