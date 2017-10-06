package po;

public class CharacterWord {

	private final String word;
	private final String syllable;
	
	public CharacterWord(String word, String syllable) {
		this.word = word;
		this.syllable = syllable;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public String getSyllable() {
		return this.syllable;
	}
	
}
