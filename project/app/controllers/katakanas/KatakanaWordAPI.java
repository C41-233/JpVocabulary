package controllers.katakanas;

import java.util.Arrays;
import java.util.List;

import base.core.Objects;
import base.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.KatakanaWordsLogic;
import po.IKatakanaWord;
import po.KatakanaWordContext;
import po.KatakanaWordType;

public class KatakanaWordAPI extends AjaxControllerBase{

	public static void create(
		@Required @StringValue(minLength=1) String value,
		@Array @StringValue(minLength=1) String[] meanings,
		@Array(minLength=1) String[] types,
		@Required @StringValue String alias,
		@Required String context
	) {
		if(Linq.from(types).isExist(s->Objects.asEnum(KatakanaWordType.class, s)==null)) {
			badRequest("不合法的词性：%s", Linq.from(types).findFirst(s->Objects.asEnum(KatakanaWordType.class, s)==null));
		}
		
		KatakanaWordContext contextEnum = Objects.asEnum(KatakanaWordContext.class, context);
		if(contextEnum == null) {
			badRequest("不合法的词源：%s", context);
		}
		
		List<String> meaningsList = Arrays.asList(meanings);
		List<KatakanaWordType> typesList = Linq.from(types).select(s->KatakanaWordType.valueOf(s)).toList();
		
		IKatakanaWord word = KatakanaWordsLogic.create(value, meaningsList, typesList, alias, contextEnum);
		
	}
	
}
