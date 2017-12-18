package controllers.verbs;

import java.util.Arrays;
import java.util.List;

import base.core.Core;
import base.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.VerbWordsLogic;
import po.VerbWordType;

public class VerbWordAPI extends AjaxControllerBase{

	public static void create(
			@Required @Array(duplicate=false) @StringValue(minLength=1) String[] values,
			@Array @StringValue(minLength=1) String[] meanings,
			@Array(duplicate=false, minLength=1) String[] types
	) {
		//词性必须全部合法
		if(Linq.from(types).isExist(s->Core.asEnum(VerbWordType.class, s)==null)) {
			badRequest("不合法的词性：%s", Linq.from(types).findFirst(s->Core.asEnum(VerbWordType.class, s)==null));
		}
		List<String> valuesList = Arrays.asList(values);
		List<String> meaningsList = Arrays.asList(meanings);
		List<VerbWordType> typesList = Linq.from(types).select(t->VerbWordType.valueOf(t)).toList();
		
		VerbWordsLogic.create(valuesList, meaningsList, typesList);
	}

	public static void delete(@Id long id) {
		VerbWordsLogic.delete(id);
	}
	
	public static void updateMeanings(@Id long id, @Array @StringValue(minLength=1) String[] meanings) {
		VerbWordsLogic.updateMeanings(id, Arrays.asList(meanings));
	}

	public static void updateType(@Id long id, @Required String type, @Required boolean value) {
		VerbWordType enumType = Core.asEnum(VerbWordType.class, type);
		if(enumType == null) {
			badRequest("不合法的词性：%s", type);
		}
		VerbWordsLogic.updateType(id, enumType, value);
	}
	
	public static void addValue(@Id long id, @Required @StringValue(minLength=1) String value) {
		VerbWordsLogic.addValue(id, value);
	}
	
	public static void deleteValue(@Id long id) {
		VerbWordsLogic.deleteValue(id);
	}

	public static void addFixword(
		@Id long id,
		@Required @StringValue(minLength=1) String value,
		@Required @StringValue(minLength=1) String meaning
	) {
		VerbWordsLogic.addFixword(id, value, meaning);
	}
	
	public static void deleteFixword(@Id long id, @Required @StringValue String value) {
		VerbWordsLogic.deleteFixword(id, value);
	}
	
	public static void updateFixword(
		@Id long id, 
		@Required @StringValue String value, 
		@Required @StringValue(minLength=1) String meaning
	) {
		VerbWordsLogic.updateFixword(id, value, meaning);
	}
	
}
