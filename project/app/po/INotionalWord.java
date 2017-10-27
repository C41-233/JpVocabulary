package po;

import core.model.IModel;

public interface INotionalWord extends IModel{

	public Iterable<String> getMeanings();
	
	public Iterable<NotionalWordType> getTypes();
	
	public Iterable<String> getSyllables();

	public Iterable<? extends INotionalWordValue> getValues();

}
