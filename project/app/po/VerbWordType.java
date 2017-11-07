package po;

public enum VerbWordType {
	
	一类动词("1", "五段动词"),
	二类动词("2", "一段动词"),
	サ变动词,
	カ变动词,
	ラ变动词,
	
	自动词, 
	他动词,
	
	否定动词,
	补助动词,
	尊敬动词,
	自谦动词("谦", null);
	
	VerbWordType() {
		this.simple = toString().substring(0, 1);
	}
	
	VerbWordType(String simple, String alias){
		this.simple = simple;
		this.alias = alias;
	}
	
	private String simple;
	private String alias;
	
	public String toSimple() {
		return this.simple;
	}
	
	public String toFull() {
		return alias == null ? toString() : toString() + "（" + alias + "）";
	}
	
}
