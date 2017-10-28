package po;

import com.google.gson.JsonObject;

public class WordPair {

	private final String word;
	private final String syllable;
	
	public WordPair(String word, String syllable) {
		this.word = word;
		this.syllable = syllable;
	}
	
	public WordPair(JsonObject json) {
		this.word = json.get("word").getAsString();
		this.syllable = json.get("syllable").getAsString();
	}

	public String getWord() {
		return this.word;
	}
	
	public String getSyllable() {
		return this.syllable;
	}

	public JsonObject toJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("word", this.word);
		jobj.addProperty("syllable", this.syllable);
		return jobj;
	}
	
}
