package po;

import com.google.gson.JsonObject;

public class VerbFixword {

	private final String value;
	private final String meaning;
	
	public VerbFixword(String value, String meaning) {
		this.value = value;
		this.meaning = meaning;
	}
	
	public VerbFixword(JsonObject jobj) {
		this.value = jobj.get("value").getAsString();
		this.meaning = jobj.get("meaning").getAsString();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getMeaning() {
		return this.meaning;
	}
	
}
