package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXingM extends VerbLianYongXing1 {

	@Override
	public String from1(String word) {
		return super.from1(word) + "ます";
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "ます";
	}

	@Override
	public String fromS(String word) {
		return super.fromS(word) + "ます";
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "ます";
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "います");
		return replace.replace(word);
	}

}
