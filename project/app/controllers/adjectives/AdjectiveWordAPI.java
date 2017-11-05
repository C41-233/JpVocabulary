package controllers.adjectives;

import java.util.Arrays;
import java.util.List;

import base.core.Objects;
import base.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.AdjectiveWordsLogic;
import po.AdjectiveWordType;

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
		
		AdjectiveWordsLogic.create(valuesList, meaningsList, typesList);
	}

	public static void delete(@Id long id) {
		AdjectiveWordsLogic.delete(id);
	}
	
	public static void addValue(@Id long id, @Required @StringValue(minLength=1) String value) {
		AdjectiveWordsLogic.addValue(id, value);
	}
	
	public static void deleteValue(@Id long id) {
		AdjectiveWordsLogic.deleteValue(id);
	}
	
	public static void updateMeanings(@Id long id, @Array @StringValue(minLength=1) String[] meanings) {
		AdjectiveWordsLogic.updateMeanings(id, Arrays.asList(meanings));
	}
	
	public static void updateType(@Id long id, @Required String type, @Required boolean value) {
		AdjectiveWordType enumType = Objects.asEnum(AdjectiveWordType.class, type);
		if(enumType == null) {
			badRequest("不合法的词性：%s", type);
		}
		AdjectiveWordsLogic.updateType(id, enumType, value);
	}

	public static void addFixword(
		@Id long id,
		@Required @StringValue(minLength=1) String value,
		@Required @StringValue(minLength=1) String meaning
	) {
		AdjectiveWordsLogic.addFixword(id, value, meaning);
	}

	public static void deleteFixword(@Id long id, @Required @StringValue String value) {
		AdjectiveWordsLogic.deleteFixword(id, value);
	}
	
}
