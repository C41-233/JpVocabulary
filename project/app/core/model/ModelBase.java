package core.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import base.utility.comparator.Comparators;
import core.logger.Logs;
import play.db.jpa.Model;

public abstract class ModelBase extends Model implements IModel{

	private static HashMap<Class, Field[]> typeToFields = new HashMap<>();
	
	private final String name;
	private final Field[] fields;
	
	protected ModelBase() {
		Class type = this.getClass();
		Field[] fields = typeToFields.get(type);
		if(fields == null) {
			fields = type.getDeclaredFields();
			typeToFields.put(type, fields);
		}
		for(Field field : fields) {
			field.setAccessible(true);
		}
		
		Arrays.sort(fields, (f1, f2)->Comparators.compare(f1.getName(), f2.getName()));
		
		this.fields = fields;
		this.name = type.getSimpleName();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelBase save() {
		boolean persistent = this.isPersistent();
		ModelBase entity = super.save();
		if(persistent) {
			log("update");
		}
		else {
			log("insert");
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelBase delete() {
		ModelBase entity = super.delete();
		log("delete");
		return entity;
	}
	
	private void log(String action) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(action)
			.append(" ")
			.append(name)
			.append("[")
			.append(id)
			.append("]");
			for(Field field : fields) {
				Object value = field.get(this);
				sb.append(" ")
				.append(field.getName())
				.append("=【")
				.append(value.toString().replace("\n", "\\n"))
				.append("】");
			}
			Logs.Db.debug(sb.toString());
		}
		catch (ReflectiveOperationException e) {
			Logs.Db.error(e, "reflection error");
		}
	}
	
}
