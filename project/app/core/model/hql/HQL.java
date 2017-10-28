package core.model.hql;

import java.util.ArrayList;

public class HQL {

	private HQL() { }
	
	public static HQL begin() {
		return new HQL();
	}

	private IQueryCondition where;
	private String orderBy;
	
	public HQL where(String condition, Object...args) {
		this.where = new Condition(condition, args);
		return this;
	}
	
	public HQL where(IQueryCondition condition) {
		this.where = condition;
		return this;
	}
	
	public HQL orderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	
	public HQLResult end() {
		StringBuilder sb = new StringBuilder();
		if(where != null) {
			where.appendSQL(sb);
		}

		StringBuilder to = new StringBuilder();
		for(int i=0, off=1; i<sb.length(); i++) {
			char ch = sb.charAt(i);
			to.append(ch);
			if(ch == '?') {
				to.append(off);
				off++;
			}
		}
		
		String rawSql = to.toString();
		
		if(orderBy != null) {
			to.append(" order by ").append(orderBy);
		}
		
		String orderSql = to.toString();
		
		ArrayList<Object> params = new ArrayList<>();
		if(where != null) {
			where.appendParams(params);
		}
		
		return new HQLResult(orderSql, rawSql, params.toArray(new Object[params.size()]));
	}
	
}
