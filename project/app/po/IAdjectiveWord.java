package po;

import core.model.IModel;

public interface IAdjectiveWord extends IModel{

	public Iterable<String> getMeanings();
	
	public Iterable<AdjectiveWordType> getTypes();
	
	public Iterable<WordFixword> getFixwords();

	public Iterable<? extends IAdjectiveWordValue> getValues();

	public Iterable<String> getSyllables();
	
}
