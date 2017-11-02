package core.model.hql;

import java.util.ArrayList;

public class Like implements IQueryCondition{

	private final String name;
	private final String arg;
	
	private Like(String name, String arg) {
		this.name = name;
		this.arg = arg;
	}
	
	public static Like like(String name, String arg) {
		return new Like(name, arg);
	}
	
	public static Like contains(String name, String arg) {
		return new Like(name, "%"+arg+"%");
	}

	public static Like startWith(String name, String arg) {
		return new Like(name, arg+"%");
	}

	@Override
	public void appendSQL(StringBuilder sb) {
		sb.append(name).append(" like ?");
	}

	@Override
	public void appendParams(ArrayList<Object> ar) {
		ar.add(arg);
	}

	
}
