package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXingT extends VerbLianYongXing2 {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("いく", "いって");
		replace.add("行く", "行って");
		replace.add("う", "って");
		replace.add("く", "いて");
		replace.add("ぐ", "いで");
		replace.add("す", "して");
		replace.add("つ", "って");
		replace.add("ぬ", "んで");
		replace.add("ぶ", "んで");
		replace.add("む", "んで");
		replace.add("る", "って");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "て";
	}

	@Override
	public String fromS(String word) {
		return super.fromS(word) + "て";
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "て";
	}

	@Override
	public String fromR(String word) {
		return super.fromR(word) + "て";
	}

}
