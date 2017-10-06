package core.model.hql;

public class HQLResult {

	public final String select;
	public final String count;
	public final Object[] params;
	
	public HQLResult(String select, String count, Object[] param) {
		this.select = select;
		this.count = count;
		this.params = param;
	}
	
}
