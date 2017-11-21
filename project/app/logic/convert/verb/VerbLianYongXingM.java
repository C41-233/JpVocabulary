package logic.convert.verb;

class VerbLianYongXingM extends VerbLianYongXing1 {

	@Override
	public String fromR(String word) {
		if(word.endsWith("る")) {
			return word.substring(0, word.length()-1)+"い";
		}
		return null;
	}

}
