package po;

import java.util.List;

import core.model.IModel;

public interface ICharacter extends IModel{

	public String getJpValue();
	
	public String getCnValue();
	
	public Iterable<String> getPinyins();
	
	public Iterable<ICharacterSyllable> getSyllables();

	public Iterable<CharacterWord> getFixwords();
}
