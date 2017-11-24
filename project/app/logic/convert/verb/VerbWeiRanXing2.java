package logic.convert.verb;

import logic.convert.SuffixReplace;

class VerbWeiRanXing2 implements IVerbConvertor {

	@Override
	public String from1(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("う", "お");
		replace.add("く", "こ");
		replace.add("ぐ", "ご");
		replace.add("す", "そ");
		replace.add("つ", "と");
		replace.add("ぬ", "の");
		replace.add("ぶ", "ぼ");
		replace.add("む", "も");
		replace.add("る", "ろ");
		return replace.replace(word);
	}

	@Override
	public String from2(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "");
		return replace.replace(word);
	}

	@Override
	public String fromS(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("する", "し");
		replace.add("ずる", "じ");
		return replace.replace(word);
	}

	@Override
	public String fromK(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("来る", "来");
		replace.add("くる", "く");
		return replace.replace(word);
	}

	@Override
	public String fromR(String word) {
		SuffixReplace replace = new SuffixReplace();
		replace.add("る", "ろ");
		return replace.replace(word);
	}

}
