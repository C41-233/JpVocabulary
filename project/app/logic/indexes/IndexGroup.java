package logic.indexes;

import java.util.ArrayList;
import java.util.List;

class IndexGroup implements IIndexGroup{
	
	public String name;
	
	public List<String> items = new ArrayList<>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Iterable<String> getItems() {
		return items;
	}
	
}