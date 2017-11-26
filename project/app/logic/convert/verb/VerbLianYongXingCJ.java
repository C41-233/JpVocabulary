package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbLianYongXingCJ extends VerbLianYongXing1 {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "いちゃ");
		replace.add("く", "きちゃ");
		replace.add("ぐ", "ぎじゃ");
		replace.add("す", "しちゃ");
		replace.add("つ", "ちちゃ");
		replace.add("ぬ", "にじゃ");
		replace.add("ぶ", "びじゃ");
		replace.add("む", "みちゃ");
		replace.add("る", "りちゃ");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		return super.from2(word) + "じゃ";
	}

	@Override
	public String fromS(String word) {
		return super.fromS(word) + "ちゃ";
	}

	@Override
	public String fromK(String word) {
		return super.fromK(word) + "じゃ";
	}

	@Override
	public String fromR(String word) {
		return null;
	}

}
