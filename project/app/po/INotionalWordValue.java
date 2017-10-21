package po;

import core.model.IModel;

public interface INotionalWordValue extends IModel{

	public String getValue();
	
	public INotionalWord getWord();
	
	public Iterable<String> getSyllables();

	public NotionalWordValueType getType();

	public Iterable<String> getIndexes();
	
}
