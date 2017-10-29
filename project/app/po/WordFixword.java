package po;

import com.google.gson.JsonObject;

public class WordFixword {

	private final String value;
	private final String meaning;
	
	public WordFixword(String value, String meaning) {
		this.value = value;
		this.meaning = meaning;
	}
	
	public WordFixword(JsonObject jobj) {
		this.value = jobj.get("value").getAsString();
		this.meaning = jobj.get("meaning").getAsString();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getMeaning() {
		return this.meaning;
	}

	public JsonObject toJsonObject() {
		JsonObject jobj = new JsonObject();
		jobj.addProperty("value", value);
		jobj.addProperty("meaning", meaning);
		return jobj;
	}
	
}
