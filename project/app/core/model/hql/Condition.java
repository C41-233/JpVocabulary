package core.model.hql;

import java.util.ArrayList;

public class Condition implements IQueryCondition{

	private final String condition;
	private final Object[] args;
	
	public Condition(String sql, Object...args) {
		this.condition = sql;
		this.args = args;
	}

	@Override
	public void appendSQL(StringBuilder sb) {
		sb.append(condition);
	}

	@Override
	public void appendParams(ArrayList<Object> ar) {
		for(Object arg: args) {
			ar.add(arg);
		}
	}
	
	
}
