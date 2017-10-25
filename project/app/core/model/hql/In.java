package core.model.hql;

import java.util.ArrayList;

import base.utility.Strings;

public class In implements IQueryCondition{

	private final String column;
	private final String condition;
	private final Object[] args;
	
	public In(String column, String condition, Object...args) {
		this.column = column;
		this.condition = condition;
		this.args = args;
	}
	
	@Override
	public void appendSQL(StringBuilder sb) {
		sb.append(Strings.format("%s in (%s)", column, condition));
	}

	@Override
	public void appendParams(ArrayList<Object> ar) {
		for(Object arg : args) {
			ar.add(arg);
		}
	}

}
