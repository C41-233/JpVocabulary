package po;

import core.model.IModel;

public interface IVerbWord extends IModel{

	public Iterable<String> getMeanings();
	
	public Iterable<VerbWordType> getTypes();
	
	public Iterable<? extends IVerbWordValue> getValues();

	public Iterable<String> getSyllables();
	
	public Iterable<WordFixword> getFixwords();

	public boolean hasType(VerbWordType type);

	public VerbWordType getMainType();
	
}
