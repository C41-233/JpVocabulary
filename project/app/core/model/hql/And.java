package core.model.hql;

import java.util.ArrayList;

public class And implements IQueryCondition{

	private final ArrayList<IQueryCondition> conditions = new ArrayList<>();
	
	public And() {
	}

	public And and(IQueryCondition condition) {
		conditions.add(condition);
		return this;
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
			sb.append(" and (");
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
