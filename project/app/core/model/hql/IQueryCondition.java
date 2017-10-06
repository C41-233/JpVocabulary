package core.model.hql;

public interface IQueryCondition {

	public String getSql();
	public Object[] getParams();
	
}
