package logic.convert.adjective;

public enum AdjConvert {

	基本型(new AdjYuanXing()),
	终止型(new AdjYuanXing()),
	连体型(new AdjYuanXing()),
	未然型1(new AdjWeiRanXing1()),
	未然型2(new AdjWeiRanXing2()),
	;

	private final IAdjConvert convertor;
	
	private AdjConvert(IAdjConvert convertor) {
		this.convertor = convertor;
	}
	
	public String convert(String value, String syllable) {
		return convertor.convert(value, syllable);
	}
	
}
