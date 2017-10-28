package po;

import core.model.IModel;

public interface IVerbWordValue extends IModel{

	public IVerbWord getWord();
	
	public String getValue();
	
	public Iterable<String> getIndexes();
	
}
