package models;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import base.utility.function.Comparators;
import base.utility.linq.Linq;
import po.CharacterWord;

class Syllables{
	
	private final List<CharacterSyllable> syllables;
	
	public Syllables(String string) {
		JsonArray jsyllables = new JsonParser().parse(string).getAsJsonArray();
		this.syllables = Linq.from(jsyllables).select(jsyllable->new CharacterSyllable(jsyllable.getAsJsonObject())).toList();
	}
	
	@Override
	public String toString() {
		JsonArray jarray = new JsonArray();
		for(CharacterSyllable syllable : syllables) {
			jarray.add(syllable.toJsonObject());
		}
		return jarray.toString();
	}
	
	public Iterable<CharacterSyllable> getSyllables(){
		return Linq.from(syllables).orderBy((c1,c2)->Comparators.compare(c1.getValue(), c2.getValue()));
	}

	public void addSyllable(String syllable) {
		this.syllables.add(new CharacterSyllable(syllable));
	}

	public void removeSyllable(String syllable) {
		int index = Linq.from(syllables).findIndex(s->s.getValue().equals(syllable));
		if(index >= 0) {
			syllables.remove(index);
		}
	}

	public void setSyllable(String syllable, List<CharacterWord> words) {
		removeSyllable(syllable);
		CharacterSyllable obj = new CharacterSyllable(syllable);
		for(CharacterWord word : words) {
			obj.addWord(word);
		}
		this.syllables.add(obj);
	}

	public void updateSyllable(String from, String to) {
		CharacterSyllable syllable = findSyllable(from);
		if(syllable != null) {
			syllable.setValue(to);
		}
	}

	public void updateMain(String value, boolean isMain) {
		CharacterSyllable syllable = findSyllable(value);
		if(syllable != null) {
			syllable.setMain(isMain);
		}
	}
	
	public CharacterSyllable findSyllable(String syllable) {
		return Linq.from(syllables).find(s->s.getValue().equals(syllable));
	}
	
}