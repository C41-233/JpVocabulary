package core.model.hql;

import java.util.ArrayList;

public class Clause implements IQueryCondition{

	private final String select;
	private final String from;
	private final IQueryCondition condition;
	
	public Clause(String select, String from) {
		this(select, from, null);
	}
	
	public Clause(String select, String from, String condition, Object...args) {
		this(select, from, new Condition(condition, args));
	}

	public Clause(String select, String from, IQueryCondition condition) {
		this.select = select;
		this.from = from;
		this.condition = condition;
	}
	
	@Override
	public void appendSQL(StringBuilder sb) {
		sb.append("select ").append(select);
		sb.append(" from ").append(from);
		if(condition != null) {
			sb.append(" where (");
			condition.appendSQL(sb);
			sb.append(")");
		}
	}

	@Override
	public void appendParams(ArrayList<Object> ar) {
		if(condition != null) {
			condition.appendParams(ar);
		}
	}
	
}
