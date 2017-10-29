package controllers.adjectives;

import java.util.Arrays;
import java.util.List;

import base.core.Objects;
import base.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.Route;
import core.controller.RouteArgs;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.AdjectiveWordsLogic;
import po.AdjectiveWordType;
import po.IAdjectiveWord;

public class AdjectiveWordAPI extends AjaxControllerBase{

	public static void create(
		@Required @Array(duplicate=false) @StringValue(minLength=1) String[] values,
		@Array @StringValue(minLength=1) String[] meanings,
		@Array(duplicate=false) String[] types
	) {
		//词性必须全部合法
		if(Linq.from(types).isExist(s->Objects.asEnum(AdjectiveWordType.class, s)==null)) {
			badRequest("不合法的词性：%s", Linq.from(types).findFirst(s->Objects.asEnum(AdjectiveWordType.class, s)==null));
		}
		List<String> valuesList = Arrays.asList(values);
		List<String> meaningsList = Arrays.asList(meanings);
		List<AdjectiveWordType> typesList = Linq.from(types).select(t->AdjectiveWordType.valueOf(t)).toList();
		
		IAdjectiveWord word = AdjectiveWordsLogic.create(valuesList, meaningsList, typesList);
		jsonResult.put("href", Route.get(AdjectiveWordDetail.class, "index", new RouteArgs().put("id", word.getId())));
	}

}
