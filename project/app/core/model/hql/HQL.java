package core.model.hql;

import play.db.jpa.GenericModel.JPAQuery;

public class HQL {

	private HQL() {
		
	}
	
	public static HQL begin() {
		return new HQL();
	}

	private IQueryCondition where;
	
	public HQL where(String condition) {
		this.where = new Condition(condition);
		return this;
	}
	
	public HQL where(IQueryCondition condition) {
		this.where = condition;
		return this;
	}
	
	public HQLResult end() {
		return new HQLResult(null, null, null);
	}
	
}
