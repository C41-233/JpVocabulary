package po;

public enum AdjectiveWordType {

	否定("否"),
	补助("补"),
	尊敬("尊");
	
	private final String simple;
	
	AdjectiveWordType(String simple) {
		this.simple = simple;
	}
	
	public String toSimple() {
		return this.simple;
	}
	
}
