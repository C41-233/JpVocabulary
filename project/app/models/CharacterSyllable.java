package models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import base.utility.linq.Linq;
import po.CharacterWord;
import po.ICharacterSyllable;

class CharacterSyllable implements ICharacterSyllable{

	private String value;
	private final List<CharacterWord> words;
	private boolean isMain;
	
	public CharacterSyllable(String syllable) {
		this.value = syllable;
		this.words = new ArrayList<>();
		this.isMain = false;
	}
	
	public CharacterSyllable(JsonObject json) {
		this.value = json.get("value").getAsString();
		this.words = Linq.from(json.get("words").getAsJsonArray()).select(j->new CharacterWord(j.getAsJsonObject())).toList();
		this.isMain = json.get("isMain").getAsBoolean();
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public Iterable<CharacterWord> getWords() {
		return this.words;
	}

	public void addWord(CharacterWord word) {
		this.words.add(word);
	}

	public void setMain(boolean b) {
		this.isMain = b;
	}
	
	@Override
	public boolean isMain() {
		return this.isMain;
	}
	
	public JsonObject toJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("value", this.value);
		
		JsonArray jwords = new JsonArray();
		jobj.add("words", jwords);
		
		words.forEach(word->jwords.add(word.toJsonObject()));
		
		jobj.addProperty("isMain", this.isMain);
		
		return jobj;
	}

}
