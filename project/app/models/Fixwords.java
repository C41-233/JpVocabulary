package models;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import base.utility.function.Comparators;
import base.utility.linq.Linq;
import po.CharacterWord;

class Fixwords {

	private final List<CharacterWord> words;
	
	public Fixwords(String json) {
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		this.words = Linq.from(array).select(j->j.getAsJsonObject()).select(word->new CharacterWord(word)).toList();
	}
	
	@Override
	public String toString() {
		JsonArray array = new JsonArray();
		for(CharacterWord word : words) {
			array.add(word.toJsonObject());
		}
		return array.toString();
	}

	public Iterable<CharacterWord> getFixwords() {
		return Linq.from(words)
				.orderBy((w1,w2)->Comparators.compare(w1.getWord(), w2.getWord()))
				.thenBy((w1,w2)->Comparators.compare(w1.getSyllable(), w2.getSyllable()));
	}

	public void add(CharacterWord word) {
		this.words.add(word);
	}

	public void delete(String word, String syllable) {
		int index = Linq.from(words).findIndex(w->w.getWord().equals(word)&&w.getSyllable().equals(syllable));
		if(index >= 0) {
			this.words.remove(index);
		}
	}
	
}
