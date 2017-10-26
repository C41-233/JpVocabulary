package core.model.hql;

import java.util.ArrayList;

public class Or implements IQueryCondition{

	private final ArrayList<IQueryCondition> conditions = new ArrayList<>();
	
	public Or() {
	}

	public Or or(IQueryCondition condition) {
		conditions.add(condition);
		return this;
	}
	
	public Or or(String sql, Object...args) {
		return or(new Condition(sql, args));
	}
	
	@Override
	public void appendSQL(StringBuilder sb) {
		if(conditions.size() == 0) {
			return;
		}
		sb.append("(");
		conditions.get(0).appendSQL(sb);
		sb.append(")");
		for(int i=1; i<conditions.size(); i++) {
			sb.append(" or (");
			conditions.get(i).appendSQL(sb);
			sb.append(")");
		}
	}

	@Override
	public void appendParams(ArrayList<Object> ar) {
		for(IQueryCondition condition: conditions) {
			condition.appendParams(ar);
		}
	}

}
