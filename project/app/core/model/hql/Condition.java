package core.model.hql;

public class Condition implements IQueryCondition{

	private final String condition;
	
	public Condition(String condition) {
		this.condition = condition;
	}
	
	@Override
	public String getSql() {
		return condition;
	}

	@Override
	public Object[] getParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
