package core.model.hql;

import java.util.ArrayList;

public interface IQueryCondition {

	public void appendSQL(StringBuilder sb);
	public void appendParams(ArrayList<Object> ar);
	
}
