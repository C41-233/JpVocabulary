package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbWeiRanXingZ extends VerbWeiRanXing1 {

	@Override
	public String from1(String word) {
		return super.from1(word) + "ず";
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "ず";
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "せず");
		replace.add("ずる", "ぜず");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "ず";
	}

	@Override
	public String fromR(String word) {
		return super.fromR(word) + "ず";
	}

}
