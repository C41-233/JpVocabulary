package po;

import core.model.IModel;

public interface IAdjectiveWordValue extends IModel{

	public IAdjectiveWord getWord();
	
	public String getValue();
	
	public Iterable<String> getIndexes();
	
}
