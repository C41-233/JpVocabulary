package controllers.notionals;

import java.util.Arrays;
import java.util.List;

import c41.core.Core;
import c41.utility.linq.Linq;
import core.controller.AjaxControllerBase;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import logic.words.NotionalWordsUpdateLogic;
import po.NotionalWordType;

public final class NotionalWordAPI extends AjaxControllerBase{

	public static void create(
		@Required @Array(duplicate=false) @StringValue(minLength=1) String[] values,
		@Array @StringValue(minLength=1) String[] meanings,
		@Array(duplicate=false, minLength=1) String[] types
	) {
		
		//词性必须全部合法
		if(Linq.from(types).isExist(s->Core.asEnum(NotionalWordType.class, s)==null)) {
			badRequest("不合法的词性：%s", Linq.from(types).findFirst(s->Core.asEnum(NotionalWordType.class, s)==null));
		}
		List<String> valuesList = Arrays.asList(values);
		List<String> meaingsList = Arrays.asList(meanings);
		List<NotionalWordType> typesList = Linq.from(types).select(t->NotionalWordType.valueOf(t)).toList();
		
		NotionalWordsUpdateLogic.create(valuesList, meaingsList, typesList);
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
		NotionalWordType enumType = Core.asEnum(NotionalWordType.class, type);
		if(enumType == null) {
			badRequest("不合法的词性：%s", type);
		}
		NotionalWordsUpdateLogic.updateType(id, enumType, value);
	}
	
}
