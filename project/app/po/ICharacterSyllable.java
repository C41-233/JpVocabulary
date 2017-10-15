package po;

public interface ICharacterSyllable {

	public String getValue();
	public boolean isMain();
	public Iterable<CharacterWord> getWords();
	
}
