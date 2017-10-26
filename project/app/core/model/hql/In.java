package core.model.hql;

import java.util.ArrayList;

public class In implements IQueryCondition{

	private final String column;
	private final IQueryCondition condition;
	
	public In(String column, String condition, Object...args) {
		this(column, new Condition(condition, args));
	}
	
	public In(String column, IQueryCondition condition) {
		this.column = column;
		this.condition = condition;
	}
	
	@Override
	public void appendSQL(StringBuilder sb) {
		sb.append(column).append(" in ");
		sb.append("(");
		condition.appendSQL(sb);
		sb.append(")");
	}

	@Override
	public void appendParams(ArrayList<Object> ar) {
		condition.appendParams(ar);
	}

}
